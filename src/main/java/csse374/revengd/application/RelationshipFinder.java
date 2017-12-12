package csse374.revengd.application;

import java.io.OutputStream;
import java.util.*;
import soot.SootClass;
import soot.util.Chain;

public class RelationshipFinder extends Analyzable {

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		Set<SootClass> sootClasses = data.getSootClasses();
		Collection<Relationship> relationships = new ArrayList<>();
		sootClasses.forEach(clazz -> {
			Relationship r = new Relationship(clazz);
			hasAFinder(r);
			extendsAFinder(r);
			implementsAFinder(r);
			usesAFinder(r);
			relationships.add(r);
			r.filterIn(sootClasses);
			//put IFilters in, probably in each method
		});
		data.setRelationships(relationships);
	}
	
	private void hasAFinder(Relationship r) {
		SootClass clazz = r.getThisClass();
		if(clazz.getName().equals("java.lang.Object")){
			return;
		}
	}
	
	private void extendsAFinder(Relationship r) {
		SootClass clazz = r.getThisClass();
		if(clazz.getName().equals("java.lang.Object")){
			return;
		}
		SootClass sClazz = clazz.getSuperclass();
		r.setExtendz(sClazz);
	}
	
	public void implementsAFinder(Relationship r) {
		SootClass clazz = r.getThisClass();
		if(clazz.getName().equals("java.lang.Object")){
			return;
		}
		Chain<SootClass> iClazz = clazz.getInterfaces();
		Set<SootClass> iClazzSet = new HashSet<>();
		iClazzSet.addAll(iClazz);
		r.setImplementz(iClazzSet);		
	}
	
	public void usesAFinder(Relationship r) {
		SootClass clazz = r.getThisClass();
		if(clazz.getName().equals("java.lang.Object")){
			return;
		}
	}
	
	
}
