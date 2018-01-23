package csse374.revengd.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.plantuml.SourceStringReader;
import soot.SootClass;

public class RevEngDApp {

	public static void main(String[] args) throws FileNotFoundException {
		Analyzable a; 
		CLParser parser = new CLParser();
		SettingsFileLoader settings = new SettingsFileLoader();
		Map<String, String> argMap = parser.parseAll(args);
		settings.loadSettings(argMap);
		
		System.out.println(argMap);
		System.out.println(argMap.get("path"));
		
		OutputStream out = new FileOutputStream("./output/UML.svg");
		CodeAnalyzer ca = new CodeAnalyzer();
		ca.addAnalyzable(new SootLoader());
		
		if(argMap.containsKey("sequence")) {
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
			if (argMap.containsKey("r")) {
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
		
			
			ca.addAnalyzable(umlRender);
		}
		
		ca.addAnalyzable(new PlantUMLGenerator());
		AnalyzableData data = new AnalyzableData(argMap);
		ca.analyze(data, out);
	}
}