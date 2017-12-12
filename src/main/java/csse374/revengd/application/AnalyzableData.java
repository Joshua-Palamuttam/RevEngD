package csse374.revengd.application;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import soot.Scene;
import soot.SootClass;


public class AnalyzableData {
	private Map <String, String> configMap;
	private Scene scene;
	private Set <SootClass> sootClasses;
	private Collection <Relationship> relationships;
	public Map<String, String> getConfigMap() {
		return configMap;
	}
	public void setConfigMap(Map<String, String> configMap) {
		this.configMap = configMap;
	}
	public Scene getScene() {
		return scene;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public Set<SootClass> getSootClasses() {
		return sootClasses;
	}
	public void setSootClasses(Set<SootClass> sootClasses) {
		this.sootClasses = sootClasses;
	}
	public Collection<Relationship> getRelationships() {
		return relationships;
	}
	public void setRelationships(Collection<Relationship> relationships) {
		this.relationships = relationships;
	}
	
}
