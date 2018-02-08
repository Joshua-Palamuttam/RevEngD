package csse374.revengd.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import soot.SootClass;

public class DecoratorModifier extends AbstractUMLModifier {
	private final static String DECORATOR_STEREOTYPE = "<<Decorator>>";
	private final static String DECORATOR_STEREOTYPE_BAD = "<<Bad Decorator>>";
	private final static String COMPONENT_STEREOTYPE = "<<Component>>";
	private final static String COLOR = "Chartreuse";
	@Override
	public String getHeaderMod() {
		StringBuilder header = new StringBuilder();
		header.append("skinparam class {\n");
		String[] stereotypes = {DECORATOR_STEREOTYPE, DECORATOR_STEREOTYPE_BAD, COMPONENT_STEREOTYPE};
		for  (String s : stereotypes) {
			header.append("BackgroundColor"+s+" "+ COLOR + "\n");
		}
		header.append("}\n");
		return header.toString();
	}

	@Override
	public String getClassMod(String original, SootClass clazz) {
		Set<IPattern> decoratorPatterns = new HashSet<>();
		decoratorPatterns.addAll(this.data.getPatternsByName(DecoratorDetector.PATTERN));
		decoratorPatterns.addAll(this.data.getPatternsByName(DecoratorDetector.PATTERN_BAD));
		if(decoratorPatterns.isEmpty()) {
			return original;
		}
		
		StringBuilder mod = new StringBuilder();
		mod.append(original);
		boolean alreadyComponent = false;
		for(IPattern p : decoratorPatterns) {
			if(p.getComponents(DecoratorDetector.GOOD_DECORATOR).contains(clazz)) {
				mod.append(" " + DECORATOR_STEREOTYPE);
			} else if (p.getComponents(DecoratorDetector.BAD_DECORATOR).contains(clazz)) {
				mod.append(" " + DECORATOR_STEREOTYPE_BAD);
			} else if (p.getComponents(DecoratorDetector.COMPONENT).contains(clazz)) {
				if (!alreadyComponent) {
					alreadyComponent = true;
					mod.append(" " + COMPONENT_STEREOTYPE);
				}
			}
		}
		return mod.toString();
	}
}
