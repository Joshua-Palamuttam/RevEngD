package csse374.revengd.application;

public class PathParser implements Argable {

	private CodeAnalyzer ca;
	
	public PathParser(CodeAnalyzer ca) {
		this.ca = ca;
	}

	@Override
	public void parseArgs(String[] args) {
		boolean pathFound = false;
		for (int i = 0; i<args.length; i++){
			if (pathFound) {
				this.ca.setPath(args[i]);
				break;
			}
			if (args[i].equals("--path")) {
				pathFound = true;
			}
		}
	}

}
