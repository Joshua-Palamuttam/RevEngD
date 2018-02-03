package csse374.revengd.application;

import java.util.Map;

public interface ICodeAnalyzerFactory {

	public CodeAnalyzer getCodeAnalyzer(Map<String, String> argMap);
	
}
