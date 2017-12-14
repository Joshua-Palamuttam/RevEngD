package csse374.revengd.application;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import soot.SootClass;

public abstract class Analyzable {
	protected Collection<IFilter> activeFilters = new ArrayList<>();
	protected Map<String, IFilter> availableFilterMap;
	
	public abstract void analyze(AnalyzableData data, OutputStream out);
	
	public void addFilter(IFilter filter) {
		this.activeFilters.add(filter);
	}
	
	public void removeFilter(IFilter filter) {
		this.activeFilters.remove(filter);
	}
	
	public void setAvailableFilterMap(Map<String, IFilter> availableFilters){
		this.availableFilterMap = availableFilters;
	}
}
