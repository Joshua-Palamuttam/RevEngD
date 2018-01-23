package csse374.revengd.application;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import soot.SootClass;

public class Pattern implements IPattern {

	private SetMultimap<String, Relationship> componentMap;
	private String patternName;
	
	public Pattern(String name){
		this.patternName = name;
		this.componentMap = HashMultimap.create();
	}
	
	@Override
	public Set<SootClass> getComponents(String name) {
		return this.componentMap.get(name).stream().map(r->{
			return r.getThisClass();
		}).collect(Collectors.toSet());
	}

	@Override
	public void putComponent(String name, Relationship r) {
		componentMap.put(name, r);
		componentMap.put("all", r);
	}
	
	public String getPatternName(){
	
		return this.patternName;
		
	}
	
	public Set<SootClass> getAllComponents(){
		return this.getComponents("all");
	}
	

}
