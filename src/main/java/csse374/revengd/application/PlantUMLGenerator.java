package csse374.revengd.application;

import java.io.OutputStream;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.core.DiagramDescription;

public class PlantUMLGenerator extends Analyzable {

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		SourceStringReader reader = new SourceStringReader(data.getUmlText());
		try {
			FileFormatOption option = new FileFormatOption(FileFormat.SVG, false);
			DiagramDescription description = reader.outputImage(out, option);
			System.out.println("UML diagram generated");
			System.out.println(description);
		} catch (Exception e) {
			System.err.println("Cannot create file to store the UML diagram.");
		}
		
	}

}
