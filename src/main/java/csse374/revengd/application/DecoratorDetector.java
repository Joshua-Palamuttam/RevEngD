package csse374.revengd.application;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import soot.Scene;
import soot.SootClass;
import soot.SootMethod;

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
		this.scene = data.getScene();
		this.data = data;
		Collection<Relationship> relationships = data.getRelationships();		
		relationships.forEach(r -> {
			if(this.useFiltersOn(r.getThisClass())) {
				SootClass component = this.getComponentClass(r);
				SootClass candidate = r.getThisClass();
				
				System.out.println(candidate + "    " + component);
				
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
	
	private SootClass getComponentClass(Relationship r) {
		if (!this.useFiltersOn(r.getThisClass())
				|| r.getThisClass().getName().equals("java.lang.Object")
				|| r.getHas().isEmpty()) {
			return null;
		}
		
		Set<SootClass> has = r.getHas().keySet();
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
		
		return this.getComponentClass(this.data.getRelationship(superClass));
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
			Set<SootClass> params = TypeResolver.resolveMethodParameters(m, scene).keySet();
			return params.contains(component);
		});
		if (hasSetter) {
			return true;
		}
		SootClass superClazz = candidate.getSuperclass();
		return hasComponentAsParam(superClazz, component);
	}
	
	private boolean usesComponent(SootClass candidate, SootClass component) {
		return true;
	}
	
	private boolean goodConstructor(SootClass candidate, SootClass component, IPattern pattern) {
		return false;
	}
	
	private boolean overridesMethods(SootClass candidate, SootClass component, IPattern pattern) {
		return false;
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
