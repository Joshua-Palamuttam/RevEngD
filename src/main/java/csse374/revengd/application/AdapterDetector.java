package csse374.revengd.application;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

public class AdapterDetector extends Analyzable {
	public static final String PATTERN = "adapter";
	public static final String ADAPTER = "adapter";
	public static final String TARGET = "target";
	public static final String ADAPTEE = "adaptee";
	
	private Scene scene;
	private AnalyzableData data;
	
	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		this.data = data;
		this.scene = this.data.getScene();
		
		Map<String, String> configMap = this.data.getConfigMap();
		String overrideRatioString = configMap.getOrDefault("adapter_override_ratio", "0.5");
		double overrideRatio = Double.parseDouble(overrideRatioString);
		
		Collection<Relationship> relationships = data.getRelationships();		
		relationships.forEach(r -> {
			SootClass candidate = r.getThisClass();
			if(this.useFiltersOn(candidate) && candidate.isConcrete()) {
				SootClass target = getTarget(candidate);
				if (target == null 
						|| !this.useFiltersOn(target)
						|| !implementsAllPublicMethods(candidate, target)) {
					return;
				}
				
				SootClass adaptee = getAdaptee(r, target);
				
				if (adaptee ==null
						|| !usesAdapteeAtLeast(candidate, target, adaptee, overrideRatio)) {
					return;
				}

				IPattern pattern = new Pattern(PATTERN);
				pattern.putComponent(ADAPTER, this.data.getRelationship(candidate));
				pattern.putComponent(TARGET, this.data.getRelationship(target));
				pattern.putComponent(ADAPTEE, this.data.getRelationship(adaptee));
				data.putPattern(pattern.getPatternName(), pattern);
			}
		});

	}
	

	private boolean usesAdapteeAtLeast(SootClass candidate, SootClass target, SootClass adaptee, double overrideRatio) {
		Set<String> publicTargetMethods = target.getMethods().stream()
				.filter(m -> m.isPublic())
				.filter(m -> !m.isConstructor())
				.map(m -> m.getSubSignature())
				.collect(Collectors.toSet());
		int targetMethodCount = publicTargetMethods.size();
		
		int candidateCount = (int) candidate.getMethods().stream()
		.filter(m -> publicTargetMethods.contains(m.getSubSignature()))
		.filter(m -> {
			return TypeResolver.methodBodyUsesField(m, adaptee, scene);
		})
		.count();
		
		return (double) candidateCount / (double) targetMethodCount >= overrideRatio;
	}


	private static SootClass getTarget(SootClass candidate) {
		if (candidate.getName().equals("java.lang.Object")) return null;
		if (candidate.getSuperclass().getName().equals("java.lang.Object")) {
			if (candidate.getInterfaceCount() == 1) {
				return candidate.getInterfaces().getFirst();
			}
			return null;
		}
		return candidate.getSuperclass();
	}
	
	private SootClass getAdaptee(Relationship rCandidate, SootClass target) {
		return rCandidate.getThisClass().getMethods().stream()
			.filter(m -> m.isConstructor())
			.filter(m -> m.isPublic())
			.flatMap(m -> {
				return TypeResolver.resolveMethodParameters(m, scene).keySet().stream();
			})
			.filter(clazz -> {
				return this.useFiltersOn(clazz) && rCandidate.has(clazz) && !clazz.equals(target);
			})
			.findFirst()
			.orElse(null);
	}

	private static boolean implementsAllPublicMethods(SootClass candidate, SootClass target) {
		Set<String> candidateSubSigs = candidate.getMethods().stream()
				.filter(m -> m.isPublic())
				.filter(m -> !m.isConstructor())
				.map(m -> m.getSubSignature())
				.collect(Collectors.toSet());
		
		return target.getMethods().stream()
				.filter(m -> m.isPublic())
				.filter(m -> !m.isConstructor())
				.allMatch(m -> {
					return candidateSubSigs.contains(m.getSubSignature());
				});
	}
}
