package csse374.revengd.application;

import java.util.List;

public abstract class AggregateMRA implements IMethodResolutionAlgorithm {
	private List<IMethodResolutionAlgorithm> algs;
	
	public void add(IMethodResolutionAlgorithm mra) {
		this.algs.add(mra);

	}
}
