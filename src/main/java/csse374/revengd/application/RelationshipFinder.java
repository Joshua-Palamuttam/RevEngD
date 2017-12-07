package csse374.revengd.application;

import java.util.*;
import soot.SootClass;

public class RelationshipFinder {
	List<SootClass> sootClasses;
	List<Relatable> relatables;
	Map<SootClass, Relationship> sootClassToRelationships;
	
	public RelationshipFinder(List<SootClass> inputClasses){
		this.sootClasses = inputClasses;
		
	}

	private void generateRelationships(){
		//TODO
	}
	
	private Map<SootClass, Relationship> getRelationshipMap(){
		return this.sootClassToRelationships;
	}
	
	private void setRelatables(List<Relatable> relatables){
		this.relatables = relatables;	
	}
	
	
}
