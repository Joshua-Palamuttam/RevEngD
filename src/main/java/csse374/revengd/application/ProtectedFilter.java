package csse374.revengd.application;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public class ProtectedFilter implements IFilter {

	@Override
	public boolean filterClass(SootClass sootClass) {
		
		return !sootClass.isPrivate();
	}

	@Override
	public boolean filterField(SootField sootField) {
	
		return !sootField.isPrivate();
	}

	@Override
	public boolean filterMethod(SootMethod sootMethod) {
		return !sootMethod.isPrivate();
	}

}
