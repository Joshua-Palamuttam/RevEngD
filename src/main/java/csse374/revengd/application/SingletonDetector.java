package csse374.revengd.application;

import java.io.OutputStream;
import java.util.Collection;

import soot.Scene;

public class SingletonDetector extends Analyzable {
	
	private Scene scene;

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		this.scene = data.getScene();
		Collection<Relationship> relationships = data.getRelationships();
		relationships.forEach(r -> {
			if(this.useFiltersOn(r.getThisClass()) && isSingleton(r)){
				IPattern pattern = new Pattern("singleton");
				pattern.putComponent("singleton", r);
				data.putPattern("singleton", pattern);
				System.out.println("SINGLETON!!!!!!!! " +r.getThisClass());
			}
			
		});
		
	}

	private boolean isSingleton(Relationship r) {
		 return  r.has(r.getThisClass())
				 && !r.hasMany(r.getThisClass()) 
				 && r.getThisClass().getMethods().stream().anyMatch(m -> {
					 return this.scene.getSootClass(m.getReturnType().toString()).equals(r.getThisClass());
				 });
	
	}
	

}
