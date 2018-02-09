package csse374.revengd.application;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CodeAnalyzer {
	private List <Analyzable> analyzables;
	
	public CodeAnalyzer() {
		this.analyzables = new ArrayList<>(); // default analyzable
	}
	public void analyze(AnalyzableData data, OutputStream out){
		for(int i = 0; i < this.analyzables.size(); i++) {
			this.analyzables.get(i).analyze(data, out);
		}
	}
	public void addAnalyzable(Analyzable analyze) {
		this.analyzables.add(analyze);
	}
}
