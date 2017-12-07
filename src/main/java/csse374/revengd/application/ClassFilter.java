package csse374.revengd.application;

import java.util.ArrayList;
import java.util.List;

import soot.SootClass;

public class ClassFilter {
	List<Filterable> filterables;
	
	public ClassFilter(){
		this.filterables = new ArrayList<>();
	}
	
	public void filter(List<SootClass> sootClasses){
		
	}
	
	public void addFilterable(Filterable filterable){
		this.filterables.add(filterable);
	}
	

}
