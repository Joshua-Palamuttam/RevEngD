package csse374.revengd.application;

import soot.SootClass;

public class ExtendsRelatable implements Relatable {

	@Override
	public void findRelationships(Relationship r) {
		SootClass clazz = r.getThisClass();
		if(clazz.getName().equals(Relatable.OBJECT)){
			return;
		}
		SootClass sClazz = clazz.getSuperclass();
		r.setExtendz(sClazz);
	}

}
