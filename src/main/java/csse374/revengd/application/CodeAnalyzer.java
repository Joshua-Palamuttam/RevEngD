package csse374.revengd.application;

import java.util.ArrayList;
import java.util.List;

import soot.SootClass;

public class CodeAnalyzer {
	private List<SootClass> sootClasses;
	private Analyzable analyzable;
	private String path;
	private List<String> classNames;
	
	public CodeAnalyzer() {
		this.classNames = new ArrayList<>();
		this.analyzable = new RegularAnalyzable(); // default analyzable
	}
	
	public void addClassName(String className) {
		this.classNames.add(className);
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setAnalyzable(Analyzable analyzable) {
		this.analyzable = analyzable;
	}
}
