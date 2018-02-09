package csse374.revengd.application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class RuntimeLoader {

	@SuppressWarnings("unchecked")
	public static void loadPatterns(Map<String, String> argMap, UMLRender umlRender, CodeAnalyzer ca, Map<String, IFilter> filterMap) {
		if (!argMap.containsKey("pattern_class") || !argMap.containsKey("modifier_class")) {
			return;
		}
		String patterns[] = argMap.get("pattern_class").split(" ");
		String modifiers[] = argMap.get("modifier_class").split(" ");

		Analyzable a;
		Class<? extends Analyzable> pattern;
		Class<? extends UMLModifier> modifier;
		for (int i = 0; i < patterns.length; i++) {
			try {
				pattern = (Class<? extends Analyzable>) Class.forName(patterns[i]);
				modifier = (Class<? extends UMLModifier>) Class.forName(modifiers[i]);
				a = pattern.newInstance();
				a.setAvailableFilterMap(filterMap);
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
	
	@SuppressWarnings("unchecked")
	public static void loadMRAs(Map<String, String> argMap, Map<String, IMethodResolutionAlgorithm> mraMap, Map<String, AggregateStrategy> agMap) {
		if (argMap.containsKey("mra")) {
			String mras[] = argMap.get("mra").split(" ");
			Class<? extends IMethodResolutionAlgorithm> mra;
			for (String s : mras) {
				if (!mraMap.containsKey(s)) {
					try {
						mra = (Class<? extends IMethodResolutionAlgorithm>) Class.forName(s);
						mraMap.put(s, mra.newInstance());
					} catch (InstantiationException e) {
						e.printStackTrace();
						throw new RuntimeException();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						throw new RuntimeException();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						throw new RuntimeException();
					}
				}
			}
		}
		
		if (argMap.containsKey("aggregate")) {
			String ags[] = argMap.get("aggregate").split(" ");
			Class<? extends AggregateStrategy> ag;
			for (String s : ags) {
				if (!agMap.containsKey(s)) {
					try {
						ag = (Class<? extends AggregateStrategy>) Class.forName(s);
						agMap.put(s, ag.newInstance());
					} catch (InstantiationException e) {
						e.printStackTrace();
						throw new RuntimeException();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						throw new RuntimeException();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						throw new RuntimeException();
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ICodeAnalyzerFactory loadCodeAnalyzerFactory(Map<String, String> argMap) {
		ICodeAnalyzerFactory caf = null;
		if (argMap.containsKey("caf")) {
			Class<? extends ICodeAnalyzerFactory> cafClass;
			
			try {
				cafClass = (Class<? extends ICodeAnalyzerFactory>) Class.forName(argMap.get("caf"));
				caf = cafClass.newInstance();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Class <" + argMap.get("caf") +"> not found");
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new RuntimeException();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			
		} else {
			caf = new CodeAnalyzerFactory();
		}
		return caf;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, IFilter> loadFilters(Map<String, String> argMap) {
		Map<String, IFilter> filterMap = new HashMap<>();
		if (argMap.containsKey("filter_class")) {
			String filters[] = argMap.get("filter_class").split(" ");
			Class<? extends IFilter> filterClass;
			for (String s : filters) {
				try {
					filterClass = (Class<? extends IFilter>) Class.forName(s);
					try {
						Constructor<? extends IFilter> cons = filterClass.getConstructor(Map.class);
						filterMap.put(s, cons.newInstance(argMap));
					} catch (NoSuchMethodException e) {
						filterMap.put(s, filterClass.newInstance());
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						throw new RuntimeException();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
						throw new RuntimeException();
					}
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					throw new RuntimeException();
				} catch (InstantiationException e) {
					e.printStackTrace();
					throw new RuntimeException();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
			}
		}
		return filterMap;
	}
}
