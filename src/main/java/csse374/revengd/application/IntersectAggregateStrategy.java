package csse374.revengd.application;

import java.util.List;
import java.util.Set;

import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public class IntersectAggregateStrategy implements AggregateStrategy {
	@Override
	public Set<SootMethod> resolve(Scene scene, Unit stmt, SootMethod method, List<IMethodResolutionAlgorithm> algs) {
		Set<SootMethod> toReturn = null;
		Set<SootMethod> toAdd = null;
		for (IMethodResolutionAlgorithm m : algs) {
			toAdd = m.resolve(method, scene, stmt);
			if (toReturn == null) {
				toReturn = toAdd;
			} else {
				toReturn.retainAll(toAdd);
			}
		}
		return toReturn;
	}

}
