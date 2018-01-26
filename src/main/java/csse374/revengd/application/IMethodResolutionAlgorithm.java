package csse374.revengd.application;

import java.util.Set;

import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public interface IMethodResolutionAlgorithm {
	public Set<SootMethod> resolve(Scene scene, Unit stmt, SootMethod method);
}
