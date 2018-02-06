package csse374.revengd.application;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public class Pattern implements IPattern {

	private SetMultimap<String, Relationship> componentMap;
	private SetMultimap<String, SootMethod> methodMap;
	private SetMultimap<String, SootField> fieldMap;
	private String patternName;
	
	public Pattern(String name){
		this.patternName = name;
		this.componentMap = HashMultimap.create();
		this.methodMap = HashMultimap.create();
		this.fieldMap = HashMultimap.create();
		
	}
	
	@Override
	public Set<SootClass> getComponents(String name) {
		return this.componentMap.get(name).stream().map(r->{
			return r.getThisClass();
		}).collect(Collectors.toSet());
	}
	
	@Override
	public Set<SootMethod> getMethods(String name) {
		return this.methodMap.get(name);
	}
	
	@Override
	public Set<SootField> getFields(String name) {
		return this.fieldMap.get(name);
	}

	@Override
	public void putComponent(String name, Relationship r) {
		componentMap.put(name, r);
		componentMap.put("all", r);
	}
	
	@Override
	public void putMethod(String name, SootMethod m) {
		methodMap.put(name, m);
		methodMap.put("all", m);
		
	}

	@Override
	public void putField(String name, SootField f) {
		fieldMap.put(name, f);
		fieldMap.put("all", f);
		
	}
	
	
	public Set<SootClass> getAllComponents(){
		return this.getComponents("all");
	}
	
	@Override
	public Set<SootMethod> getAllMethods() {
		return this.getMethods("all");
	}

	@Override
	public Set<SootField> getAllFields() {
		return this.getFields("all");
	}
	
	public String getPatternName(){
		
		return this.patternName;
		
	}
	

}
