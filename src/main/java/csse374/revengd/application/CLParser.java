package csse374.revengd.application;

import java.util.List;

public class CLParser {
	
	List<Argable> argables;

	public CLParser(List<Argable> argables2){
		this.argables = argables2;
	}
	
	public void parseAll(String[] args){
		this.argables.forEach(argable -> {
			argable.parseArgs(args);});
	}
	
}
