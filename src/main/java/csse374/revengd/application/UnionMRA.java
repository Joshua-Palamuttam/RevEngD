package csse374.revengd.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public class UnionMRA extends AggregateMRA {
	private List<IMethodResolutionAlgorithm> algs;

	public UnionMRA() {
		this.algs = new ArrayList<>();
	}

	@Override
	public Set<SootMethod> resolve(Scene scene, Unit stmt, SootMethod method) {
		Set<SootMethod> toReturn = new HashSet<>();
		Set<SootMethod> toAdd = null;
		for (IMethodResolutionAlgorithm m : this.algs) {
			toAdd = m.resolve(scene, stmt, method);
			toReturn.addAll(toAdd);
		}
		return toReturn;
	}

}
