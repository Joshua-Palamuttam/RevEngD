package csse374.revengd.application;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class RevEngDApp {

	public static void main(String[] args) throws IOException {
		CLParser parser = new CLParser();
		SettingsFileLoader settings = new SettingsFileLoader();
		Map<String, String> argMap = parser.parseAll(args);
		settings.loadSettings(argMap);

		ICodeAnalyzerFactory caFactory = RuntimeLoader.loadCodeAnalyzerFactory(argMap);

		CodeAnalyzer ca = caFactory.getCodeAnalyzer(argMap);
		String output = argMap.getOrDefault("output", "UML.svg");
		OutputStream out = new FileOutputStream(output);
		AnalyzableData data = new AnalyzableData(argMap);
		ca.analyze(data, out);
		out.close();
	}
}