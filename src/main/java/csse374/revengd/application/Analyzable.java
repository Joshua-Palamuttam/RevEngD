package csse374.revengd.application;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import soot.SootClass;

public abstract class Analyzable {
	protected Collection<Ifilter> filters = new ArrayList<>();
	public abstract void analyze(AnalyzableData data, OutputStream out);
	
	public void addFilter(Ifilter filter) {
		this.filters.add(filter);
	}
	
	public void removeFilter(Ifilter filter) {
		this.filters.remove(filter);
	}
}
