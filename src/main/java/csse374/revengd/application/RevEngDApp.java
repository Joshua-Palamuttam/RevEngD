package csse374.revengd.application;

import java.util.ArrayList;
import java.util.List;

public class RevEngDApp {

	public static void main(String[] args) {
		CodeAnalyzer ca = new CodeAnalyzer();
		UMLGenerator umlg = new UMLGenerator();
		ClassFilter cf = new ClassFilter();
		List<Argable> argables = new ArrayList<>();
		argables.add(new ClassParser(ca)); // add all argables here
		argables.add(new PathParser(ca));
		argables.add(new DiscoveredParser(ca));
		argables.add(new AccessLevelParser(umlg));

		CLParser parser = new CLParser(argables);
		parser.parseAll(args);// TODO Auto-generated method stub

	}

}
