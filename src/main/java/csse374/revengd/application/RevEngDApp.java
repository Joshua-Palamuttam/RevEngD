package csse374.revengd.application;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class RevEngDApp {

	public static void main(String[] args) throws IOException {
		Analyzable a; 
		CLParser parser = new CLParser();
		SettingsFileLoader settings = new SettingsFileLoader();
		Map<String, String> argMap = parser.parseAll(args);
		System.out.println(argMap);
		settings.loadSettings(argMap);
		
		
		OutputStream out = new FileOutputStream("./output/UML.svg");
		CodeAnalyzer ca = new CodeAnalyzer();
		ca.addAnalyzable(new SootLoader());
		
		if(argMap.containsKey("method")) {
			Analyzable sequenceDiagram = new SequenceDiagramRender();
			if (argMap.containsKey("exclude")) {
				sequenceDiagram.addActiveFilter(new PrefixFilter(argMap));
			}
			ca.addAnalyzable(sequenceDiagram);
		} else {
			UMLRender umlRender = new UMLRender();
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
					a.addActiveFilter(new PrefixFilter(argMap));
					ca.addAnalyzable(a);
					umlRender.addModifier(new SingletonModifier());
				}
				if (argMap.get("pattern").contains(CompInheritanceDetector.PATTERN)){
					a = new CompInheritanceDetector();
					a.addActiveFilter(new PrefixFilter(argMap));
					ca.addAnalyzable(a);
					umlRender.addModifier(new CompInheritanceModifier());
				}
			}
			
			RuntimeLoader.loadPatterns(argMap, umlRender, ca);
			
			ca.addAnalyzable(umlRender);
		}
		
		ca.addAnalyzable(new PlantUMLGenerator());
		AnalyzableData data = new AnalyzableData(argMap);
		ca.analyze(data, out);
		out.close();
	}
}