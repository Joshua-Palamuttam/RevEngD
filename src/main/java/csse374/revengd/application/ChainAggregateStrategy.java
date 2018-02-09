package csse374.revengd.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public class ChainAggregateStrategy implements AggregateStrategy {

	@Override
	public Set<SootMethod> resolve(Scene scene, Unit stmt, SootMethod method, List<IMethodResolutionAlgorithm> algs) {
		Set<SootMethod> toReturn = new HashSet<>();
		for (IMethodResolutionAlgorithm m : algs) {
			toReturn = m.resolve(method, scene, stmt);
			if(!toReturn.isEmpty()) {
				break;
			}
		}
		System.out.println(method+"  "+toReturn.size());
		return toReturn;
	}

}
