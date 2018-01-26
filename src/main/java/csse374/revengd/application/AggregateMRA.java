package csse374.revengd.application;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateMRA implements IMethodResolutionAlgorithm {
	protected List<IMethodResolutionAlgorithm> algs = new ArrayList<>();
	
	public void add(IMethodResolutionAlgorithm mra) {
		this.algs.add(mra);

	}
}
