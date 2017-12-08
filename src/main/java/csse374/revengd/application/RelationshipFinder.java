package csse374.revengd.application;

import java.util.*;
import soot.SootClass;

public class RelationshipFinder {
	List<SootClass> sootClasses;
	List<Relatable> relatables;
	List<Relationship> relationships;
	
	public RelationshipFinder(List<SootClass> inputClasses){
		this.sootClasses = inputClasses;
		this.relatables = new ArrayList<>();
		this.relationships = new ArrayList<>();
	}

	public void generateRelationships(){
		this.sootClasses.forEach(clazz -> {
			Relationship r = new Relationship(clazz);
			this.relatables.forEach(relatable -> {
				relatable.findRelationships(r);
			});
			this.relationships.add(r);
			
			r.filterIn(this.sootClasses);
//			System.out.println("---------------------");
//			System.out.println(clazz.getName());
//			System.out.println("Extends:");
//			if (r.getExtendz() != null){
//				System.out.println(r.getExtendz().getName());
//			}
//			System.out.println("Implements:");
//			r.getImplementz().forEach(cl -> {
//				System.out.println(cl.getName());
//			});
//			System.out.println("---------------------");
		});
	}
	
	public List<Relationship> getRelationships(){
		return this.relationships;
	}
	
	public void addRelatable(Relatable relatable){
		this.relatables.add(relatable);	
	}
	
	
}
