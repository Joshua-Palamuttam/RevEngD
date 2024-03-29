package csse374.revengd.application;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import soot.Scene;
import soot.SootClass;

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
	
	@SuppressWarnings("hiding")
	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		this.addAllAvailableFilters();
		this.data = data;
		this.scene = this.data.getScene();
		Collection<Relationship> relationships = data.getRelationships();		
		relationships.forEach(r -> {
			SootClass candidate = r.getThisClass();
			if(this.useFiltersOn(candidate)) {
				SootClass delegator = this.getDelegator(r, true);
				SootClass component = this.getComponentClass(candidate, delegator);
				if (null == component
						|| !this.usesComponent(candidate, delegator)) {
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
	
	private SootClass getComponentClass(SootClass candidate, SootClass delegator) {
		if (delegator == null) return null;
		Set<SootClass> superTypes = new HashSet<>();
		superTypes.add(delegator);
		this.computeAllSuperTypes(delegator, superTypes);

		Set<SootClass> candidateSuperTypes = new HashSet<>();
		if (candidate.hasSuperclass()) {
			candidateSuperTypes.add(candidate.getSuperclass());
		}
		if (candidate.getInterfaceCount() > 0) {
			candidateSuperTypes.addAll(candidate.getInterfaces());
		}
		
		SootClass component = null;
		for (SootClass dClazz : superTypes) {
			if (candidateSuperTypes.contains(dClazz)) {
				component = dClazz;
				break;
			}
		}		
		if (null != component) {
			return component;
		}
		
		for (SootClass cSuper : candidateSuperTypes) {
			component = this.getComponentClass(cSuper, delegator);
			if (component != null) {
				break;
			}
		}
		return component;		
	}
	
	private SootClass getDelegator(Relationship r, boolean isCandidate) {
		if (!r.getThisClass().hasSuperclass()
				|| !this.useFiltersOn(r.getThisClass())) {
			return null;
		}
		
		Set<SootClass> has = r.getThisClass().getFields()
				.stream()
				.filter(f -> {return isCandidate || !f.isPrivate();})
				.flatMap(f -> {return TypeResolver.resolve(f, this.scene).keySet().stream();})
				.collect(Collectors.toSet());
		
		Set<SootClass> superTypes = new HashSet<>();
		superTypes.add(r.getThisClass());
		this.computeAllSuperTypes(r.getThisClass(), superTypes);
		SootClass delegator = null;
		for (SootClass clazz : has) {
			Set<SootClass> fieldSuperTypes = new HashSet<>();
			fieldSuperTypes.add(clazz);
			this.computeAllSuperTypes(clazz, fieldSuperTypes);
			for (SootClass fieldSuper : fieldSuperTypes) {
				if (superTypes.contains(fieldSuper)) {
					delegator = clazz;
					break;
				}
			}
		}		
		if (null != delegator) {
			return delegator;
		}
		SootClass superClass = r.getThisClass().getSuperclass();
		return this.getDelegator(this.data.getRelationship(superClass), false);
	}
	
	private boolean usesComponent(SootClass candidate, SootClass component) {
		Set<String> componentSubSigs = component.getMethods().stream()
				.filter(m -> m.isPublic())
				.filter(m -> !m.isConstructor())
				.map(m -> m.getSubSignature())
				.collect(Collectors.toSet());
			
		if (candidate.getMethods().stream().filter(m -> !m.isConcrete()).noneMatch(m -> m.isConcrete())) {
			return true;
		}
			
		return candidate.getMethods().stream()
				.filter(m -> m.isConcrete())
				.filter(m -> !m.isConstructor())
				.filter(m -> componentSubSigs.contains(m.getSubSignature()))
				.anyMatch(m -> {
					return TypeResolver.methodBodyUsesField(m, component, this.scene);
				});
	}
	
	@SuppressWarnings("boxing")
	private boolean goodConstructor(SootClass candidate, SootClass component, IPattern pattern) {
		return candidate.getMethods().stream()
			.filter(m -> m.isConstructor())
			.map(m -> {
				boolean toReturn = TypeResolver.resolveMethodParameters(m, this.scene).keySet().stream()
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
			if (!superClass.hasSuperclass()) {
				break;
			}
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
		if (!clazz.hasSuperclass())
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
