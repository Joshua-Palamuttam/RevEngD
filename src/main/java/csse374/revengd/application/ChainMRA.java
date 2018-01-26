package csse374.revengd.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public class ChainMRA extends AggregateMRA {

	@Override
	public Set<SootMethod> resolve(Scene scene, Unit stmt, SootMethod method) {
		Set<SootMethod> toReturn = new HashSet<>();
		for (IMethodResolutionAlgorithm m : this.algs) {
			toReturn = m.resolve(scene, stmt, method);
			if(!toReturn.isEmpty()) {
				break;
			}
		}
		System.out.println(method+"  "+toReturn.size());
		return toReturn;
	}

}
