package csse374.revengd.application;

public class DiscoveredParser implements Argable {
	private CodeAnalyzer ca;
	
	public DiscoveredParser(CodeAnalyzer ca) {
		this.ca = ca;
	}

	@Override
	public void parseArgs(String[] args) {
		for (String arg: args) {
			if (arg.equals("--discovered")) {
				this.ca.setAnalyzable(new DiscoveredAnalyzable());
				break;
			}
		}
	}

}
