package csse374.revengd.application;

import java.util.Set;

import soot.SootClass;

public class CompInheritanceModifier extends AbstractUMLModifier {
	private final static String COLOR = " #Orange";

	@Override
	public String getClassMod(String original, SootClass clazz) {
		Set<IPattern> compInheritancePatterns = this.data.getPatternsByName(CompInheritanceDetector.PATTERN);
		if (compInheritancePatterns == null) {
			return original;
		}
		for (IPattern p : compInheritancePatterns) {
			if(p.getAllComponents().contains(clazz)) {
				return original + COLOR;
			}
		}
		return original;
	}

	@Override
	public String getExtendsMod(String original, SootClass startClazz, SootClass endClazz) {
		Set<IPattern> compInheritancePatterns = this.data.getPatternsByName(CompInheritanceDetector.PATTERN);
		if (compInheritancePatterns == null) {
			return original;
		}
		for (IPattern p : compInheritancePatterns) {
			if(p.getComponents(CompInheritanceDetector.SUBCLASS).contains(startClazz)
				&& p.getComponents(CompInheritanceDetector.SUPERCLASS).contains(endClazz) ) {
				return original + COLOR;
			}
		}
		return original;
	}

}
