package csse374.revengd.application;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import soot.MethodOrMethodContext;
import soot.Scene;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.toolkits.callgraph.CallGraph;

public class CallGraphMRA implements IMethodResolutionAlgorithm {

	@Override
	public Set<SootMethod> resolve(SootMethod method, Scene scene, Unit stmt) {
		CallGraph callGraph = scene.getCallGraph();

		ArrayList<SootMethod> targetMethods = new ArrayList<>();
		callGraph.edgesOutOf(stmt).forEachRemaining(edge -> {
			MethodOrMethodContext methodOrCntxt = edge.getTgt();
			SootMethod targetMethod = methodOrCntxt.method();
			if (targetMethod != null) {
				targetMethods.add(targetMethod);
			}
		});
		return targetMethods.stream().collect(Collectors.toSet());
	}

}
