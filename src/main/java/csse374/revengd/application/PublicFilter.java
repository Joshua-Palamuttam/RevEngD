package csse374.revengd.application;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public class PublicFilter implements IFilter {

	@Override
	public boolean filterClass(SootClass sootClass) {
		
		return sootClass.isPublic();
	}

	@Override
	public boolean filterField(SootField sootField) {
	
		return sootField.isPublic();
	}

	@Override
	public boolean filterMethod(SootMethod sootMethod) {

		return sootMethod.isPublic();
	}

}
