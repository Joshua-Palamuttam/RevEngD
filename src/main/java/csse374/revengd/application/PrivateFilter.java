package csse374.revengd.application;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public class PrivateFilter implements IFilter {

	@Override
	public boolean filterClass(SootClass sootClass) {
		return true;
	}

	@Override
	public boolean filterField(SootField sootField) {
		return true;
	}

	@Override
	public boolean filterMethod(SootMethod sootMethod) {
		return true;
	}

}
