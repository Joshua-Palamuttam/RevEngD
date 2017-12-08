package csse374.revengd.application;

import java.util.HashSet;
import java.util.Set;

import soot.SootClass;
import soot.util.Chain;

public class ImplementsRelatable implements Relatable{

	@Override
	public void findRelationships(Relationship r) {
		SootClass clazz = r.getThisClass();
		if(clazz.getName().equals(Relatable.OBJECT)){
			return;
		}
		Chain<SootClass> iClazz = clazz.getInterfaces();
		Set<SootClass> iClazzSet = new HashSet<>();
		iClazzSet.addAll(iClazz);
		r.setImplementz(iClazzSet);		
	}

}
