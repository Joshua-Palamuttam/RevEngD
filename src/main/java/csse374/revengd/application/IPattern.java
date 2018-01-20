package csse374.revengd.application;

import java.util.Set;

import soot.SootClass;

public interface IPattern {

	public Set<SootClass> getComponents(String name);
	
	public void putComponent(String name, Relationship r);
	public String getPatternName();
	
}
