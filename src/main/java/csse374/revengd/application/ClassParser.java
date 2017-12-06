package csse374.revengd.application;

public class ClassParser implements Argable {

	private CodeAnalyzer ca;
	
	public ClassParser(CodeAnalyzer ca) {
		this.ca = ca;
	}

	@Override
	public void parseArgs(String[] args) {
		boolean readingClassNames = false;
		for (int i = 0; i<args.length; i++) {
			if (readingClassNames) {
				if (args[i].startsWith("-")) {
					readingClassNames = false;
				} else {
					this.ca.addClassName(args[i]);
				}
			}
			
			if (args[i].equals("--class")) {
				readingClassNames = true;
			}
		}
	}

}
