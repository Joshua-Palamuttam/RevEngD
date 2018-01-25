package csse374.revengd.application;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public class SyntheticFilter implements IFilter {

	@Override
	public boolean filterClass(SootClass sootClass) {
		return !sootClass.getName().contains("$");
	}

	@Override
	public boolean filterField(SootField sootField) {
		return !sootField.getName().contains("$");
	}

	@Override
	public boolean filterMethod(SootMethod sootMethod) {
		return !sootMethod.getName().contains("$");
	}

}
