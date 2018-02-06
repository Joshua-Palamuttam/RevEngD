package csse374.revengd.application;

import java.util.Set;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public interface IPattern {

	public Set<SootClass> getComponents(String name);
	public void putComponent(String name, Relationship r);
	public String getPatternName();
	public Set<SootClass> getAllComponents();
	public Set<SootMethod> getMethods(String name);
	public Set<SootField> getFields(String name);
	public void putMethod (String name, SootMethod m);
	public void putField (String name, SootField f);
	public Set<SootMethod> getAllMethods();
	public Set<SootField> getAllFields();
	
}
