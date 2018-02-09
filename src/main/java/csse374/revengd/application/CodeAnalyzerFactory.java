package csse374.revengd.application;

import java.util.HashMap;
import java.util.Map;

public class CodeAnalyzerFactory implements ICodeAnalyzerFactory {

	@Override
	public CodeAnalyzer getCodeAnalyzer(Map<String, String> argMap) {
		Analyzable a;
		CodeAnalyzer ca = new CodeAnalyzer();
		ca.addAnalyzable(new SootLoader());
		
		Map<String, IFilter> filterMap = RuntimeLoader.loadFilters(argMap);
		if(argMap.containsKey("method")) {
			Map<String, IMethodResolutionAlgorithm> mraMap = new HashMap<>();
			Map<String, AggregateStrategy> agMap = new HashMap<>();
			mraMap.put("hierarchy", new HierarchyMRA());
			mraMap.put("callgraph", new CallGraphMRA());
			
			agMap.put("chain", new ChainAggregateStrategy());
			agMap.put("union", new UnionAggregateStrategy());
			agMap.put("intersection", new IntersectAggregateStrategy());
			RuntimeLoader.loadMRAs(argMap, mraMap, agMap);
			Analyzable sequenceDiagram = new SequenceDiagramRender(mraMap, agMap);
			if (argMap.containsKey("exclude")) {
				sequenceDiagram.setAvailableFilterMap(filterMap);
				sequenceDiagram.addActiveFilter(new PrefixFilter(argMap));
			}
			ca.addAnalyzable(sequenceDiagram);
		} else {
			UMLRender umlRender = new UMLRender();
			umlRender.setAvailableFilterMap(filterMap);
			String accessLevel = argMap.get("accessLevel");
			if (null != accessLevel) {
				if (accessLevel.equals("public")) {
					umlRender.addActiveFilter(new PublicFilter());
				} else if (accessLevel.equals("private")) {
					umlRender.addActiveFilter(new PrivateFilter());
				} else if (accessLevel.equals("protected")) {
					umlRender.addActiveFilter(new ProtectedFilter());
				}
			}
			if (argMap.containsKey("exclude")) {
				umlRender.addActiveFilter(new PrefixFilter(argMap));
			}
			if (argMap.containsKey("synthetic") && argMap.get("synthetic").equals("false")) {
				umlRender.addActiveFilter(new SyntheticFilter());
			}
			if (argMap.containsKey("r") && !argMap.get("r").equals("false")) {
				ca.addAnalyzable(new RecursiveLoader());
			}
			ca.addAnalyzable(new RelationshipFinder());
			if (argMap.containsKey("pattern")){
				if (argMap.get("pattern").contains(SingletonDetector.PATTERN)){
					a = new SingletonDetector();
					a.setAvailableFilterMap(filterMap);
					a.addActiveFilter(new PrefixFilter(argMap));
					ca.addAnalyzable(a);
					umlRender.addModifier(new SingletonModifier());
				}
				if (argMap.get("pattern").contains(CompInheritanceDetector.PATTERN)){
					a = new CompInheritanceDetector();
					a.setAvailableFilterMap(filterMap);
					a.addActiveFilter(new PrefixFilter(argMap));
					ca.addAnalyzable(a);
					umlRender.addModifier(new CompInheritanceModifier());
				}
				if (argMap.get("pattern").contains(DIPViolationDetector.PATTERN)){
					a = new DIPViolationDetector();
					a.setAvailableFilterMap(filterMap);
					a.addActiveFilter(new PrefixFilter(argMap));
					ca.addAnalyzable(a);
					umlRender.addModifier(new DIPViolationModifier());
				}
				if (argMap.get("pattern").contains(DecoratorDetector.PATTERN)){
					a = new DecoratorDetector();
					a.setAvailableFilterMap(filterMap);
					a.addActiveFilter(new PrefixFilter(argMap));
					ca.addAnalyzable(a);
					umlRender.addModifier(new DecoratorModifier());
				}
				if (argMap.get("pattern").contains(AdapterDetector.PATTERN)){
					a = new AdapterDetector();
					a.setAvailableFilterMap(filterMap);
					a.addActiveFilter(new PrefixFilter(argMap));
					ca.addAnalyzable(a);
					umlRender.addModifier(new AdapterModifier());
				}
			}
			
			RuntimeLoader.loadPatterns(argMap, umlRender, ca, filterMap);
			
			ca.addAnalyzable(umlRender);
		}
		ca.addAnalyzable(new PlantUMLGenerator());
		
		return ca;
	}

}
