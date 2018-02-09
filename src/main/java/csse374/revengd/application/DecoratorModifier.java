package csse374.revengd.application;

import java.util.HashSet;
import java.util.Set;

import soot.SootClass;
import soot.SootMethod;

public class DecoratorModifier extends AbstractUMLModifier {
	private final static String DECORATOR_STEREOTYPE = "<<Decorator>>";
	private final static String DECORATOR_STEREOTYPE_BAD = "<<Bad Decorator>>";
	private final static String COMPONENT_STEREOTYPE = "<<Component>>";
	private final static String ASSOCIATION_STEREOTYPE = "<<decorates>>";
	private final static String HTML_HEAD = "<font color=Red>";
	private final static String HTML_FOOT = "</font>";
	private final static String COLOR = "Chartreuse";
	
	@Override
	public String getHeaderMod() {
		StringBuilder header = new StringBuilder();
		header.append("skinparam class {\n");
		String[] stereotypes = {DECORATOR_STEREOTYPE, COMPONENT_STEREOTYPE};
		for  (String s : stereotypes) {
			header.append("BackgroundColor"+s+" "+ COLOR + "\n");
		}
		header.append("}\n");
		return header.toString();
	}

	@Override
	public String getClassMod(String original, SootClass clazz) {
		Set<IPattern> decoratorPatterns = new HashSet<>();
		decoratorPatterns.addAll(this.data.getPatternsByName(DecoratorDetector.PATTERN_BAD));
		decoratorPatterns.addAll(this.data.getPatternsByName(DecoratorDetector.PATTERN));
		if(decoratorPatterns.isEmpty()) {
			return original;
		}
		
		StringBuilder mod = new StringBuilder();
		mod.append(original);
		boolean alreadyComponent = false;
		boolean alreadyDecorator = false;
		for(IPattern p : decoratorPatterns) {
			if(p.getComponents(DecoratorDetector.GOOD_DECORATOR).contains(clazz)) {
				if (!alreadyDecorator) {
					alreadyDecorator = true;
					mod.append(" " + DECORATOR_STEREOTYPE);
				}
			} else if (p.getComponents(DecoratorDetector.BAD_DECORATOR).contains(clazz)) {
				if (!alreadyDecorator) {
					alreadyDecorator = true;
					mod.append(" " + DECORATOR_STEREOTYPE_BAD);
				}
			} else if (p.getComponents(DecoratorDetector.COMPONENT).contains(clazz)) {
				if (!alreadyComponent) {
					alreadyComponent = true;
					mod.append(" " + COMPONENT_STEREOTYPE);
				}
			}
		}
		return mod.toString();
	}

	@Override
	public String getHasMod(String original, SootClass startClazz, SootClass endClazz) {
		Set<IPattern> decoratorPatterns = new HashSet<>();
		decoratorPatterns.addAll(this.data.getPatternsByName(DecoratorDetector.PATTERN_BAD));
		decoratorPatterns.addAll(this.data.getPatternsByName(DecoratorDetector.PATTERN));
		
		for (IPattern pattern : decoratorPatterns) {
			if ((pattern.getComponents(DecoratorDetector.GOOD_DECORATOR).contains(startClazz)
					|| pattern.getComponents(DecoratorDetector.BAD_DECORATOR).contains(startClazz))
					&& pattern.getComponents(DecoratorDetector.COMPONENT).contains(endClazz)) {
				return original + ":" + ASSOCIATION_STEREOTYPE;
			}
		}
		return original;
	}

	@Override
	public String getExtraMethods(Relationship r) {
		StringBuilder extra = new StringBuilder();
		for (IPattern pattern : this.data.getPatternsByName(DecoratorDetector.PATTERN_BAD)) {
			if (pattern.getComponents(DecoratorDetector.BAD_DECORATOR).contains(r.getThisClass())) {
				for (SootMethod m : pattern.getMethods(DecoratorDetector.UNDECORATED_METHOD)) {
					String original = UMLRender.getSingularMethodString(m);
					extra.append(original.substring(0, 2) + HTML_HEAD + original.substring(2) + HTML_FOOT);
					extra.append("\n");
				}
			}
		}
		return extra.toString();
	}

	@Override
	public String getMethodMod(String original, SootMethod method) {
		for (IPattern pattern : this.data.getPatternsByName(DecoratorDetector.PATTERN_BAD)) {
			if (pattern.getMethods(DecoratorDetector.CONSTRUCTOR).contains(method)) {
				return original.substring(0, 2) + HTML_HEAD + original.substring(2) + HTML_FOOT;
			}
		}
		return original;
	}

	@Override
	public String getUsesMod(String original, SootClass startClazz, SootClass endClazz) {
		return this.getHasMod(original, startClazz, endClazz);
	}
}
