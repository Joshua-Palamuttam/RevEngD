package csse374.revengd.application;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.core.DiagramDescription;

public class PlantUMLGenerator extends Analyzable {

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		SourceStringReader reader = new SourceStringReader(data.getUmlText());
		try {
			Path filePath = Paths.get(System.getProperty("user.dir"), "build", "plantuml", "diagram.png");
			Files.createDirectories(filePath.getParent());
				
			FileFormatOption option = new FileFormatOption(FileFormat.PNG, false);
			DiagramDescription description = reader.outputImage(out, option);
			System.out.println("UML diagram generated at: " + filePath.toString());
			System.out.println(description);
		} catch (Exception e) {
			System.err.println("Cannot create file to store the UML diagram.");
		}
		
	}

}
