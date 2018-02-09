package csse374.revengd.application;

import java.util.Set;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public class DIPViolationModifier extends AbstractUMLModifier {
	private final static String COLOR = " #Peru";
	private final static String HTML_HEAD = "<font color=red>";
	private final static String HTML_FOOT = "</font>";

	@Override
	public String getClassMod(String original, SootClass clazz) {
		Set<IPattern> dipPatterns = this.data.getPatternsByName(DIPViolationDetector.PATTERN);
		if (dipPatterns == null) {
			return original;
		}
		for (IPattern p : dipPatterns) {
			if(p.getComponents(DIPViolationDetector.VIOLATOR).contains(clazz)) {
				return original + COLOR;
			}
		}
		return original;
	}

	@Override
	public String getExtendsMod(String original, SootClass startClazz, SootClass endClazz) {
		Set<IPattern> dipPatterns = this.data.getPatternsByName(DIPViolationDetector.PATTERN);
		if (dipPatterns == null) {
			return original;
		}
		for (IPattern p : dipPatterns) {
			if(p.getComponents(DIPViolationDetector.VIOLATOR).contains(startClazz)
				&& p.getComponents(DIPViolationDetector.DEPENDENCY).contains(endClazz) ) {
				return original + COLOR;
			}
		}
		return original;
	}
	
	@Override
	public String getUsesMod(String original, SootClass startClazz, SootClass endClazz) {
		return this.getExtendsMod(original, startClazz, endClazz);
	}
	
	@Override
	public String getHasMod(String original, SootClass startClazz, SootClass endClazz) {
		return this.getExtendsMod(original, startClazz, endClazz);
	}

	@Override
	public String getFieldMod(String original, SootField field) {
		Set<IPattern> dipPatterns = this.data.getPatternsByName(DIPViolationDetector.PATTERN);
		if (dipPatterns == null) {
			return original;
		}
		for (IPattern p : dipPatterns) {
			if(p.getAllFields().contains(field)) {
				return original.substring(0, 2) + HTML_HEAD + original.substring(2) + HTML_FOOT;
			}
		}
		return original;
	}

	@Override
	public String getMethodMod(String original, SootMethod method) {
		Set<IPattern> dipPatterns = this.data.getPatternsByName(DIPViolationDetector.PATTERN);
		if (dipPatterns == null) {
			return original;
		}
		for (IPattern p : dipPatterns) {
			if(p.getAllMethods().contains(method)) {
				return original.substring(0, 2) + HTML_HEAD + original.substring(2) + HTML_FOOT;
			}
		}
		return original;
	}
	
	
}
