package csse374.revengd.application;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public abstract class Analyzable {
	protected Collection<IFilter> activeFilters = new ArrayList<>();
	protected Map<String, IFilter> availableFilterMap = new HashMap<>();
	
	public abstract void analyze(AnalyzableData data, OutputStream out);
	
	public void addFilter(IFilter filter) {
		this.activeFilters.add(filter);
	}
	
	public void removeFilter(IFilter filter) {
		this.activeFilters.remove(filter);
	}
	
	protected boolean useFiltersOn(SootClass clazz) {
		if (clazz == null) {
			return false;
		}
		boolean keep = true;
		if(!this.activeFilters.isEmpty()) {
			for(IFilter fil : this.activeFilters) {
				keep &= fil.filterClass(clazz);
			}
		}
		return keep;
	}
	
	protected boolean useFiltersOn(SootField field) {
		if (field == null) {
			return false;
		}
		boolean keep = true;
		if(!this.activeFilters.isEmpty()) {
			for(IFilter fil : this.activeFilters) {
				keep &= fil.filterField(field);
			}
		}
		return keep;
	}
	
	protected boolean useFiltersOn(SootMethod method) {
		if (method == null) {
			return false;
		}
		boolean keep = true;
		if(!this.activeFilters.isEmpty()) {
			for(IFilter fil : this.activeFilters) {
				keep &= fil.filterMethod(method);
			}
		}
		return keep;
	}
	
	public void setAvailableFilterMap(Map<String, IFilter> availableFilters){
		this.availableFilterMap = availableFilters;
	}
	
	public void addActiveFilter(IFilter filter) {
		this.activeFilters.add(filter);
	}
}
