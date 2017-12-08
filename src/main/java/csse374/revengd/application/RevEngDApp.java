package csse374.revengd.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soot.SootClass;

public class RevEngDApp {

	public static void main(String[] args) {
		CodeAnalyzer ca = new CodeAnalyzer();
		UMLGenerator umlg = new UMLGenerator();
		ClassFilter cf = new ClassFilter();
		List<Argable> argables = new ArrayList<>();
		argables.add(new ClassParser(ca)); // add all argables here
		argables.add(new PathParser(ca));
		argables.add(new DiscoveredParser(ca));
		Map<String, Filterable> argToFilterable = new HashMap <String, Filterable>();
		argToFilterable.put("private", new PrivateFilterable());
		argToFilterable.put("public", new PublicFilterable());
		argToFilterable.put("protected", new ProtectedFilterable());
		argables.add(new AccessLevelParser(cf,argToFilterable));

		CLParser parser = new CLParser(argables);
		parser.parseAll(args);
		
		ca.analyze();
		List<SootClass> sootClasses = ca.getSootClasses();
		cf.filter(sootClasses);
		
		RelationshipFinder rf = new RelationshipFinder(sootClasses);
		rf.addRelatable(new ExtendsRelatable());
		rf.addRelatable(new ImplementsRelatable());
		
		rf.generateRelationships();
		
		umlg.generate(rf.getRelationships());
		System.out.println(umlg.getPlantUMLText());
	}

}
