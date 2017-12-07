package csse374.revengd.application;

import java.util.Map;

import soot.SootClass;

public class UMLGenerator {
	private String plantUMLText;
	
	public void generate(Map<SootClass, Relationship> sootClassToRelationship) {
		
	}
	
	public String getPlantUMLText() {
		return this.plantUMLText;
	}
}
