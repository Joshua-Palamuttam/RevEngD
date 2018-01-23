package csse374.revengd.application;

import java.util.Set;

import soot.SootClass;

public abstract class AbstractUMLModifier implements UMLModifier {
	protected AnalyzableData data;
	public String getHeaderMod() {
		return "";
	}

	public String getClassMod(SootClass clazz) {
		return "";
	}

	public String getExtendsMod(SootClass startClazz, SootClass endClazz) {
		return "";
	}

	public String getImplementsMod(SootClass startClazz, SootClass endClazz) {
		return "";
	}

	public String getHasMod(SootClass startClazz, SootClass endClazz) {
		return "";
	}

	public String getUsesMod(SootClass startClazz, SootClass endClazz) {
		return "";
	}
	
	public void setAnalyzableData(AnalyzableData data) {
		this.data = data;
	}

}
