package csse374.revengd.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public class ChainMRA extends AggregateMRA {
	private List<IMethodResolutionAlgorithm> algs;

	public ChainMRA() {
		this.algs = new ArrayList<>();
	}

	@Override
	public Set<SootMethod> resolve(Scene scene, Unit stmt, SootMethod method) {
		Set<SootMethod> toReturn = null;
		for (IMethodResolutionAlgorithm m : this.algs) {
			toReturn = m.resolve(scene, stmt, method);
			if(!toReturn.isEmpty()) {
				break;
			}
		}
		return toReturn;
	}

}
