package csse374.revengd.application;

import java.util.Set;

import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public interface IMethodResolutionAlgorithm {
	public Set<SootMethod> resolve(SootMethod method, Scene scene, Unit stmt);
}
