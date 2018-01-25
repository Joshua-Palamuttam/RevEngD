package csse374.revengd.application;

import java.util.Map;

public class RuntimeLoader {

	@SuppressWarnings("unchecked")
	public static void loadPatterns(Map<String, String> argMap, UMLRender umlRender, CodeAnalyzer ca) {
		if (!argMap.containsKey("pattern_class") || !argMap.containsKey("modifier_class")) {
			return;
		}
		String patterns[] = argMap.get("pattern_class").split(" ");
		String modifiers[] = argMap.get("modifier_class").split(" ");
		if (patterns.length != modifiers.length) {
			System.err.println("Number of classes listed for pattern_class must be the same as the number of classes listed for modifier_class");
			return;
		}
		Analyzable a;
		Class<? extends Analyzable> pattern;
		Class<? extends UMLModifier> modifier;
		for (int i = 0; i < patterns.length; i++) {
			try {
				pattern = (Class<? extends Analyzable>) Class.forName(patterns[i]);
				modifier = (Class<? extends UMLModifier>) Class.forName(modifiers[i]);
				a = pattern.newInstance();
				a.addActiveFilter(new PrefixFilter(argMap));
				ca.addAnalyzable(a);
				System.out.println("added analyzable");
				umlRender.addModifier(modifier.newInstance());
			} catch (InstantiationException e) {
				throw new RuntimeException();
			} catch (IllegalAccessException e) {
				throw new RuntimeException();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException();
			}
		}
	}
}
