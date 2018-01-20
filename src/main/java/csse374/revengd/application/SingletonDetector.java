package csse374.revengd.application;

import java.io.OutputStream;
import java.util.Collection;

public class SingletonDetector extends Analyzable {

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		Collection<Relationship> relationships = data.getRelationships();
		relationships.forEach(r -> {
			if(isSingleton(r)){
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
					 return m.getReturnType().equals(r.getThisClass());
				 });
	
	}
	

}
