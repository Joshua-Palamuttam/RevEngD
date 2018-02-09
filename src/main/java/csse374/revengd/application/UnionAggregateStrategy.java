package csse374.revengd.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public class UnionAggregateStrategy implements AggregateStrategy {

	@Override
	public Set<SootMethod> resolve(Scene scene, Unit stmt, SootMethod method, List<IMethodResolutionAlgorithm> algs) {
		Set<SootMethod> toReturn = new HashSet<>();
		Set<SootMethod> toAdd = null;
		for (IMethodResolutionAlgorithm m : algs) {
			toAdd = m.resolve(method, scene, stmt);
			toReturn.addAll(toAdd);
		}
		return toReturn;
	}

}
