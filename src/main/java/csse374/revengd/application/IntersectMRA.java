package csse374.revengd.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public class IntersectMRA extends AggregateMRA {
	@Override
	public Set<SootMethod> resolve(Scene scene, Unit stmt, SootMethod method) {
		Set<SootMethod> toReturn = null;
		Set<SootMethod> toAdd = null;
		for (IMethodResolutionAlgorithm m : this.algs) {
			toAdd = m.resolve(scene, stmt, method);
			if (toReturn == null) {
				toReturn = toAdd;
			} else {
				toReturn.retainAll(toAdd);
			}
		}
		return toReturn;
	}

}
