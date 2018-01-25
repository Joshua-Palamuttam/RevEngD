package csse374.revengd.application;

import soot.SootClass;

public interface UMLModifier {
	public String getHeaderMod();

	public String getClassMod(SootClass clazz);

	public String getExtendsMod(SootClass startClazz, SootClass endClazz);

	public String getImplementsMod(SootClass startClazz, SootClass endClazz);

	public String getHasMod(SootClass startClazz, SootClass endClazz);
	
	public String getUsesMod(SootClass startClazz, SootClass endClazz);
	
	public void setAnalyzableData(AnalyzableData data);

}
