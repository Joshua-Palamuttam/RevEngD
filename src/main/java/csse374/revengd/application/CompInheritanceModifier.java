package csse374.revengd.application;

import java.util.Set;

import soot.SootClass;

public class CompInheritanceModifier extends AbstractUMLModifier {
	private final static String COLOR = " #Orange";

	@Override
	public String getClassMod(SootClass clazz) {
		Set<IPattern> compInheritancePatterns = this.data.getPatternsByName(CompInheritanceDetector.PATTERN);
		if (compInheritancePatterns == null) {
			return "";
		}
		for (IPattern p : compInheritancePatterns) {
			if(p.getAllComponents().contains(clazz)) {
				return COLOR;
			}
		}
		return "";
	}

	@Override
	public String getExtendsMod(SootClass startClazz, SootClass endClazz) {
		Set<IPattern> compInheritancePatterns = this.data.getPatternsByName(CompInheritanceDetector.PATTERN);
		if (compInheritancePatterns == null) {
			return "";
		}
		for (IPattern p : compInheritancePatterns) {
			if(p.getComponents(CompInheritanceDetector.SUBCLASS).contains(startClazz)
				&& p.getComponents(CompInheritanceDetector.SUPERCLASS).contains(endClazz) ) {
				return COLOR;
			}
		}
		return "";
	}

}
