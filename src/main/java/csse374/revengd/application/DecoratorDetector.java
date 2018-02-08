package csse374.revengd.application;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.internal.JInstanceFieldRef;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class DecoratorDetector extends Analyzable {
	public static final String PATTERN = "decorator";
	public static final String PATTERN_BAD = "baddecorator";
	public static final String GOOD_DECORATOR = "gooddecorator";
	public static final String BAD_DECORATOR = "baddecorator";
	public static final String COMPONENT = "component";
	public static final String CONSTRUCTOR = "constructor";
	public static final String UNDECORATED_METHOD = "undecoratedmethod";
	
	private Scene scene;
	private AnalyzableData data;
	
	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		this.data = data;
		this.scene = this.data.getScene();
		Collection<Relationship> relationships = data.getRelationships();		
		relationships.forEach(r -> {
			SootClass candidate = r.getThisClass();
			if(this.useFiltersOn(candidate)) {
				SootClass component = this.getComponentClass(r, true);
				if (null == component
						|| !this.hasComponentAsParam(candidate, component)
						|| !this.usesComponent(candidate, component)) {
					return;
				}				
				// now everything is at least a bad decorator
				IPattern pattern = new Pattern(PATTERN_BAD);
				pattern.putComponent(COMPONENT, data.getRelationship(component));
				boolean goodConstructor = this.goodConstructor(candidate, component, pattern);
				boolean overridesMethods = this.overridesMethods(candidate, component, pattern);
				
				if (goodConstructor && overridesMethods) {
					pattern.setPatternName(PATTERN);
					pattern.putComponent(GOOD_DECORATOR, r);
				} else {
					pattern.putComponent(BAD_DECORATOR, r);
				}
				
				data.putPattern(pattern.getPatternName(), pattern);
				
			}
		});
	}
	
	private SootClass getComponentClass(Relationship r, boolean isCandidate) {
		if (!this.useFiltersOn(r.getThisClass())
				|| r.getThisClass().getName().equals("java.lang.Object")) {
			return null;
		}
		
		Set<SootClass> has = r.getThisClass().getFields()
				.stream()
				.filter(f -> {return isCandidate || !f.isPrivate();})
				.flatMap(f -> {return TypeResolver.resolve(f, this.scene).keySet().stream();})
				.collect(Collectors.toSet());
		
		Set<SootClass> superTypes = new HashSet<>();
		this.computeAllSuperTypes(r.getThisClass(), superTypes);
		SootClass component = null;
		for (SootClass clazz : has) {
			if (superTypes.contains(clazz)) {
				component = clazz;
				break;
			}
		}
		if (null != component) {
			return component;
		}
		SootClass superClass = r.getThisClass().getSuperclass();
		
		return this.getComponentClass(this.data.getRelationship(superClass), false);
	}
	
	private boolean hasComponentAsParam(SootClass candidate, SootClass component) {
		if (candidate.getName().equals("java.lang.Object") || candidate.equals(component)) {
			return false;
		}
		List<SootMethod> methods = candidate.getMethods();
		List<SootMethod> notDecorated = methods.stream().filter(m -> {
			SootMethod componentMethod = component.getMethodUnsafe(m.getSubSignature());
			if (componentMethod == null) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		
		boolean hasSetter = notDecorated.stream().anyMatch(m -> {
			Set<SootClass> params = TypeResolver.resolveMethodParameters(m, this.scene).keySet();
			return params.contains(component);
		});
		if (hasSetter) {
			return true;
		}
		SootClass superClazz = candidate.getSuperclass();
		return hasComponentAsParam(superClazz, component);
	}
	
	private boolean usesComponent(SootClass candidate, SootClass component) {
		Set<String> componentSubSigs = component.getMethods().stream()
				.filter(m -> m.isPublic())
				.filter(m -> !m.isConstructor())
				.map(m -> m.getSubSignature())
				.collect(Collectors.toSet());
		
		return candidate.getMethods().stream()
			.filter(m -> m.isConcrete())
			.filter(m -> !m.isConstructor())
			.filter(m -> componentSubSigs.contains(m.getSubSignature()))
			.anyMatch(m -> {
				Body body = m.retrieveActiveBody();
				UnitGraph cfg = new ExceptionalUnitGraph(body);
				for (Unit stmt : cfg) {
					Value op = null;
					if (stmt instanceof AssignStmt) {
						op = ((AssignStmt) stmt).getRightOp();
						if (op instanceof JInstanceFieldRef) {
							Map<SootClass, Boolean> fieldTypes = TypeResolver.resolve(((JInstanceFieldRef) op).getField(), scene);
							if (fieldTypes.containsKey(component)) {
								return true;
							}
						}
					} else if (stmt instanceof JInstanceFieldRef) {
						Map<SootClass, Boolean> fieldTypes = TypeResolver.resolve(((JInstanceFieldRef) stmt).getField(), scene);
						if (fieldTypes.containsKey(component)) {
							return true;
						}
					}
				}
				return false;
			});
	}
	
	@SuppressWarnings("boxing")
	private boolean goodConstructor(SootClass candidate, SootClass component, IPattern pattern) {
		return candidate.getMethods().stream()
			.filter(m -> m.isConstructor())
			.map(m -> {
				boolean toReturn = TypeResolver.resolveMethodParameters(m, scene).keySet().stream()
						.anyMatch(clazz -> {
							return clazz.equals(component);
						});
				if (!toReturn) {
					pattern.putMethod(CONSTRUCTOR, m);
				}
				return toReturn;
			})
			.reduce((b1, b2) -> {return b1 && b2;})
			.orElse(false);
	}
	
	private boolean overridesMethods(SootClass candidate, SootClass component, IPattern pattern) {
		Set<String> componentSubSigs = component.getMethods().stream()
				.filter(m -> m.isPublic())
				.filter(m -> !m.isConstructor())
				.map(m -> m.getSubSignature())
				.collect(Collectors.toSet());
		
		SootClass superClass = candidate;
		Set<String> candidateSubSigs = new HashSet<>();
		while(this.useFiltersOn(superClass) && !superClass.equals(component) && !superClass.getName().equals("java.lang.Object")) {
			candidateSubSigs.addAll(superClass.getMethods().stream()
			.filter(m -> !m.isConstructor())
			.filter(m -> componentSubSigs.contains(m.getSubSignature()))
			.map(m -> m.getSubSignature())
			.collect(Collectors.toSet()));
			superClass = superClass.getSuperclass();
		}
		
		boolean toReturn = true;
		for (String ss : componentSubSigs) {
			if (!candidateSubSigs.contains(ss)) {
				toReturn = false;
				pattern.putMethod(UNDECORATED_METHOD, component.getMethod(ss));
			}
		}
		return toReturn;
	}
	
	private void computeAllSuperTypes(final SootClass clazz, final Collection<SootClass> allSuperTypes) {
		if (clazz.getName().equals("java.lang.Object"))
			return;

		Collection<SootClass> directSuperTypes = new ArrayList<>();

		SootClass superClazz = clazz.getSuperclass();
		if (superClazz != null && this.useFiltersOn(superClazz))
			directSuperTypes.add(superClazz);

		if (clazz.getInterfaceCount() > 0)
			directSuperTypes.addAll(clazz.getInterfaces());

		directSuperTypes.forEach(aType -> {
			if (!allSuperTypes.contains(aType) && this.useFiltersOn(aType)) {
				allSuperTypes.add(aType);
				this.computeAllSuperTypes(aType, allSuperTypes);
			}
		});
	}
	
}
