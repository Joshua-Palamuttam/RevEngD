package csse374.revengd.application;

import java.util.Set;

import soot.SootClass;

public abstract class AbstractUMLModifier implements UMLModifier {
	protected AnalyzableData data;
	@Override
	public String getHeaderMod() {
		return "";
	}

	@Override
	public String getClassMod(SootClass clazz) {
		return "";
	}

	@Override
	public String getExtendsMod(SootClass startClazz, SootClass endClazz) {
		return "";
	}

	@Override
	public String getImplementsMod(SootClass startClazz, SootClass endClazz) {
		return "";
	}

	@Override
	public String getHasMod(SootClass startClazz, SootClass endClazz) {
		return "";
	}

	@Override
	public String getUsesMod(SootClass startClazz, SootClass endClazz) {
		return "";
	}
	
	@Override
	public void setAnalyzableData(AnalyzableData data) {
		this.data = data;
	}

}
