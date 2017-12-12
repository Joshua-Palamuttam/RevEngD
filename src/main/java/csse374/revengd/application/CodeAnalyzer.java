package csse374.revengd.application;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import csse374.revengd.soot.SceneBuilder;
import soot.Scene;
import soot.SootClass;

public class CodeAnalyzer {
	private List <Analyzable> analyzables;
	
	public CodeAnalyzer() {
		this.analyzables = new ArrayList<>(); // default analyzable
	}
	public void analyze(AnalyzableData data, OutputStream out){
		for(int i = 0; i < analyzables.size(); i++) {
			analyzables.get(i).analyze(data, out);
		}
	}
	public void addAnalyzable(Analyzable analyze) {
		analyzables.add(analyze);
	}
}
