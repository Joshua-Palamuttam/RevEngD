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
		String adapteeRatioString = configMap.getOrDefault("adapter_adaptee_ratio", "0.5");
		double overrideRatio = Double.parseDouble(overrideRatioString);
		double adapteeRatio = Double.parseDouble(adapteeRatioString);
		
		Collection<Relationship> relationships = data.getRelationships();		
		relationships.forEach(r -> {
			SootClass candidate = r.getThisClass();
			if(this.useFiltersOn(candidate) && candidate.isConcrete()) {
				SootClass target = getTarget(candidate);
				if (target == null 
						|| !this.useFiltersOn(target)
						|| !implementsAllPublicMethods(candidate, target, overrideRatio)) {
					return;
				}
				
				SootClass adaptee = getAdaptee(r, target);

				if (adaptee ==null
						|| !usesAdapteeAtLeast(candidate, target, adaptee, adapteeRatio)) {
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
	

	private boolean usesAdapteeAtLeast(SootClass candidate, SootClass target, SootClass adaptee, double ratio) {
		Set<String> publicTargetMethods = target.getMethods().stream()
				.filter(m -> m.isPublic())
				.filter(m -> !m.isConstructor())
				.map(m -> m.getSubSignature())
				.collect(Collectors.toSet());
		
		int overriddenCount = (int) candidate.getMethods().stream()
				.filter(m -> publicTargetMethods.contains(m.getSubSignature()))
				.count();
		
		int usesCount = (int) candidate.getMethods().stream()
				.filter(m -> publicTargetMethods.contains(m.getSubSignature()))
				.filter(m -> {
					return TypeResolver.methodBodyUsesField(m, adaptee, this.scene);
				})
				.count();
		
		return (double) usesCount / (double) overriddenCount >= ratio;
	}


	private static SootClass getTarget(SootClass candidate) {
		if (candidate.getName().equals("java.lang.Object")) return null;
		if (candidate.hasSuperclass() && candidate.getSuperclass().getName().equals("java.lang.Object")) {
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
				return TypeResolver.resolveMethodParameters(m, this.scene).keySet().stream();
			})
			.filter(clazz -> {
				return this.useFiltersOn(clazz) && rCandidate.has(clazz) && !clazz.equals(target);
			})
			.findFirst()
			.orElse(null);
	}

	private static boolean implementsAllPublicMethods(SootClass candidate, SootClass target, double overrideRatio) {
		int targetCount, candidateCount;
		
		Set<String> candidateSubSigs = candidate.getMethods().stream()
				.filter(m -> m.isPublic())
				.filter(m -> !m.isConstructor())
				.map(m -> m.getSubSignature())
				.collect(Collectors.toSet());
		
		Stream<SootMethod> targetMethods = target.getMethods().stream()
				.filter(m -> m.isPublic())
				.filter(m -> !m.isConstructor());
		targetCount = (int) targetMethods.count();
		
		candidateCount = (int) target.getMethods().stream()
				.filter(m -> m.isPublic())
				.filter(m -> !m.isConstructor())
				.filter(m -> {
					return candidateSubSigs.contains(m.getSubSignature());
				}).count();
		
		return (double) candidateCount / (double) targetCount >= overrideRatio;
	}
}
