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
	public String getClassMod(String original, SootClass clazz) {
		Set<IPattern> singletonPatterns = this.data.getPatternsByName(SingletonDetector.PATTERN);
		if(singletonPatterns == null) {
			return original;
		}
		for(IPattern p : singletonPatterns) {
			if(p.getComponents(SingletonDetector.SINGLETON).contains(clazz)) {
				return original + " " + STEREOTYPE;
			}			
		}
		return original;
	}
	
	public void setAnalyzableData(AnalyzableData data) {
		this.data = data;
	}
	
}
