package csse374.revengd.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import soot.SootClass;

public class ClassFilter {
	private Set<Filterable> filterables;
	
	public ClassFilter(){
		this.filterables = new HashSet<>();
	}
	
	public void filter(Set<SootClass> sootClasses){
		if (this.filterables.isEmpty()) {
			return;
		}
	}
	
	public void addFilterable(Filterable filterable){
		this.filterables.add(filterable);
	}
	

}
