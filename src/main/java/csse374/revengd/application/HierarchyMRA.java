package csse374.revengd.application;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import soot.Hierarchy;
import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public class HierarchyMRA implements IMethodResolutionAlgorithm {

	@Override
	public Set<SootMethod> resolve(SootMethod method, Scene scene, Unit stmt) {
		Hierarchy hierarchy = scene.getActiveHierarchy();
		List<SootMethod> possibleMethods = hierarchy.resolveAbstractDispatch(method.getDeclaringClass(), method);
		return possibleMethods.stream().collect(Collectors.toSet());
	}

}
