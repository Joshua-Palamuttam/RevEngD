package csse374.revengd.application;

import java.util.List;
import java.util.Map;
import java.util.Set;

import soot.SootClass;

public class UMLGenerator {
	private String plantUMLText;
	
	public void generate(Set<Relationship> relationships) {
		StringBuilder str = new StringBuilder();
		str.append("@startuml\n");
		relationships.forEach(r ->{
			str.append(r.getClassString());
		});
		
		relationships.forEach(r ->{
			str.append(r.getRelateString());
		});
		str.append("@enduml");
		plantUMLText = str.toString();
	}
	
	public String getPlantUMLText() {
		return this.plantUMLText;
	}
}
