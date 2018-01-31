package csse374.revengd.application;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public interface UMLModifier {
	public String getHeaderMod();

	public String getClassMod(String original, SootClass clazz);

	public String getExtendsMod(String original, SootClass startClazz, SootClass endClazz);

	public String getImplementsMod(String original, SootClass startClazz, SootClass endClazz);

	public String getHasMod(String original, SootClass startClazz, SootClass endClazz);
	
	public String getUsesMod(String original, SootClass startClazz, SootClass endClazz);
	
	public String getFieldMod(String original, SootField field);
	
	public String getExtraFields(Relationship r);
	
	public String getMethodMod(String original, SootMethod method);
	
	public String getExtraMethods(Relationship r);
	
	public String getNotes(Relationship r);
	
	public void setAnalyzableData(AnalyzableData data);

}
