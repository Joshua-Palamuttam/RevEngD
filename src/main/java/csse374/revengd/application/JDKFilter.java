package csse374.revengd.application;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public class JDKFilter implements IFilter {

	@Override
	public boolean filterClass(SootClass sootClass) {
		return !sootClass.toString().startsWith("java.");
	}

	@Override
	public boolean filterField(SootField sootField) {
		return !sootField.getDeclaringClass().toString().startsWith("java.");
	}

	@Override
	public boolean filterMethod(SootMethod sootMethod) {
		return !sootMethod.getDeclaringClass().toString().startsWith("java.");
	}

}
