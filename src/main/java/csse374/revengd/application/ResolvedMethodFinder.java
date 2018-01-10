package csse374.revengd.application;

import java.util.ArrayList;
import java.util.List;

import soot.Hierarchy;
import soot.MethodOrMethodContext;
import soot.Scene;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.toolkits.callgraph.CallGraph;

public class ResolvedMethodFinder {

	public static SootMethod resolveMethod(Scene scene, Unit stmt, SootMethod method) {

		SootMethod resolvedMethod = performContextSensitivePointerAnalysis(scene, stmt, method);
		if (resolvedMethod == null) {
			resolvedMethod = performCHAPointerAnalysis(scene, method);
		}
		if (resolvedMethod == null) {
			return method;
		}
		return resolvedMethod;
	}

	private static SootMethod performContextSensitivePointerAnalysis(Scene scene, Unit stmt, SootMethod method) {
		CallGraph callGraph = scene.getCallGraph();

		ArrayList<SootMethod> targetMethods = new ArrayList<>();
		callGraph.edgesOutOf(stmt).forEachRemaining(edge -> {
			MethodOrMethodContext methodOrCntxt = edge.getTgt();
			SootMethod targetMethod = methodOrCntxt.method();
			if (targetMethod != null) {
				targetMethods.add(targetMethod);
			}
		});
		if (targetMethods.isEmpty()) {
			return null;
		}
		return targetMethods.get(0);
	}

	private static SootMethod performCHAPointerAnalysis(Scene scene, SootMethod method) {
		Hierarchy hierarchy = scene.getActiveHierarchy();
		List<SootMethod> possibleMethods = hierarchy.resolveAbstractDispatch(method.getDeclaringClass(), method);
		if (possibleMethods.isEmpty()) {
			return null;
		}
		return possibleMethods.get(0);
	}

}
