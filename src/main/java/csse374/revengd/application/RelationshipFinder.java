package csse374.revengd.application;

import java.util.*;
import soot.SootClass;

public class RelationshipFinder {
	Set<SootClass> sootClasses;
	List<Relatable> relatables;
	Set<Relationship> relationships;
	
	public RelationshipFinder(Set<SootClass> inputClasses){
		this.sootClasses = inputClasses;
		this.relatables = new ArrayList<>();
		this.relationships = new HashSet<>();
	}

	public void generateRelationships(){
		
		this.sootClasses.forEach(clazz -> {
			Relationship r = new Relationship(clazz);
			this.relatables.forEach(relatable -> {
				relatable.findRelationships(r);
			});
			this.relationships.add(r);
			
			r.filterIn(this.sootClasses);

		});
	}
	
	public Set<Relationship> getRelationships(){
		return this.relationships;
	}
	
	public void addRelatable(Relatable relatable){
		this.relatables.add(relatable);	
	}
	
	
}
