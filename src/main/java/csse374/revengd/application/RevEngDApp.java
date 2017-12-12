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

	public static void main(String[] args) {
		CLParser parser = new CLParser();
		Map<String, String> argMap = parser.parseAll(args);
		 OutputStream out = null;

		CodeAnalyzer ca = new CodeAnalyzer();

		ca.addAnalyzable(new SootLoader());
		ca.addAnalyzable(new RecursiveLoader());
		ca.addAnalyzable(new RelationshipFinder());
		ca.addAnalyzable(new UMLRender());

		AnalyzableData data = new AnalyzableData(argMap);

		ca.analyze(data, out);
	}
}