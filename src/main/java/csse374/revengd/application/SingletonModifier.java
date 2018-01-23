package csse374.revengd.application;

import java.util.Set;

import soot.SootClass;

public class SingletonModifier extends AbstractUMLModifier{
	private final static String STEREOTYPE = "<<Singleton>>";
	private final static String COLOR = "Blue";
	@Override
	public String getHeaderMod() {
		return "skinparam class {\nBorderColor"+STEREOTYPE+" "+ COLOR+"\n}\n";
	}

	@Override
	public String getClassMod(SootClass clazz) {
		Set<IPattern> singletonPatterns = this.data.getPatternsByName(SingletonDetector.PATTERN);
		if(singletonPatterns == null) {
			return "";
		}
		for(IPattern p : singletonPatterns) {
			if(p.getComponents(SingletonDetector.SINGLETON).contains(clazz)) {
				return STEREOTYPE;
			}			
		}
		return "";
	}
	
	public void setAnalyzableData(AnalyzableData data) {
		this.data = data;
	}
	
}
