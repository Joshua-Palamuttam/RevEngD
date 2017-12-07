package csse374.revengd.application;

import java.util.*;
import soot.SootClass;

public class RelationshipFinder {
	List<SootClass> sootClasses;
	List<Relatable> relatables;
	Map<SootClass, Relationship> sootClassToRelationships;
	
	public RelationshipFinder(List<SootClass> inputClasses){
		this.sootClasses = inputClasses;
		this.relatables = new ArrayList<>();
		this.sootClassToRelationships = new HashMap<>();
	}

	public void generateRelationships(){
		this.sootClasses.forEach(clazz -> {
			Relationship r = new Relationship(clazz);
			this.relatables.forEach(relatable -> {
				relatable.findRelationships(r);
			});
			this.sootClassToRelationships.put(clazz, r);
			
			//r.filterIn(this.sootClasses);
			System.out.println("---------------------");
			System.out.println(clazz.getName());
			System.out.println("Extends:");
			if (r.getExtendz() != null){
				System.out.println(r.getExtendz().getName());
			}
			System.out.println("Implements:");
			r.getImplementz().forEach(cl -> {
				System.out.println(cl.getName());
			});
			System.out.println("---------------------");
		});
	}
	
	public Map<SootClass, Relationship> getRelationshipMap(){
		return this.sootClassToRelationships;
	}
	
	public void addRelatable(Relatable relatable){
		this.relatables.add(relatable);	
	}
	
	
}
