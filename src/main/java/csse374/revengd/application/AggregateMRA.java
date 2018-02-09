package csse374.revengd.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import soot.Scene;
import soot.SootMethod;
import soot.Unit;

public class AggregateMRA implements IMethodResolutionAlgorithm {
	protected List<IMethodResolutionAlgorithm> algs = new ArrayList<>();
	protected AggregateStrategy as;
	
	public AggregateMRA() {
		this.as = null;
	}
	
	public AggregateMRA(AggregateStrategy as) {
		this.as = as;
	}
	
	public void add(IMethodResolutionAlgorithm mra) {
		this.algs.add(mra);
	}
	
	public void setAggregateStrategy(AggregateStrategy as) {
		this.as = as;
	}

	@Override
	public Set<SootMethod> resolve(SootMethod method, Scene scene, Unit stmt) {
		return this.as.resolve(scene, stmt, method, this.algs);
	}
}
