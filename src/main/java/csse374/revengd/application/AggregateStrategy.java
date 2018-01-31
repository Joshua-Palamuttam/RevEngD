package csse374.revengd.application;

import java.util.List;
import java.util.Set;

import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public interface AggregateStrategy {

	Set<SootMethod> resolve(Scene scene, Unit stmt, SootMethod method, List<IMethodResolutionAlgorithm> algs);

}
