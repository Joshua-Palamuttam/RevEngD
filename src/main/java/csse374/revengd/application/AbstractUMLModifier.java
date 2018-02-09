package csse374.revengd.application;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public abstract class AbstractUMLModifier implements UMLModifier {
	protected AnalyzableData data;
	@Override
	public String getHeaderMod() {
		return "";
	}

	@Override
	public String getClassMod(String original, SootClass clazz) {
		return original;
	}

	@Override
	public String getExtendsMod(String original, SootClass startClazz, SootClass endClazz) {
		return original;
	}

	@Override
	public String getImplementsMod(String original, SootClass startClazz, SootClass endClazz) {
		return original;
	}

	@Override
	public String getHasMod(String original, SootClass startClazz, SootClass endClazz) {
		return original;
	}

	@Override
	public String getUsesMod(String original, SootClass startClazz, SootClass endClazz) {
		return original;
	}
	
	@Override
	public String getExtraFields(Relationship r) {
		return "";
	}

	@Override
	public String getExtraMethods(Relationship r) {
		return "";
	}

	@Override
	public String getFieldMod(String original, SootField field) {
		return original;
	}
	
	@Override
	public String getMethodMod(String original, SootMethod method) {
		return original;
	}
	
	@Override
	public String getNotes(Relationship r) {
		return "";
	}

	@Override
	public void setAnalyzableData(AnalyzableData data) {
		this.data = data;
	}
}
