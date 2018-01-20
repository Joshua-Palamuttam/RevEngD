package csse374.revengd.application;

import java.io.OutputStream;
import java.util.Collection;

public class CompInheritanceDetector extends Analyzable {

	
	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		
		Collection<Relationship> relationships = data.getRelationships();
		relationships.forEach(r -> {
			if(this.useFiltersOn(r.getThisClass()) && isInheritance(r)){
				System.out.println(r.getThisClass()+" "+r.getExtendz());
				IPattern pattern = new Pattern("inheritance");
				pattern.putComponent("subclass", r);
				pattern.putComponent("superclass", data.getRelationship(r.getExtendz()));
				data.putPattern("inheritance", pattern);
				System.out.println("INHERITANCE!!!!!!!! " +r.getThisClass());
			}
			
		});
		
	}

	private boolean isInheritance(Relationship r) {
		 return  !(r.getExtendz() == null) 
				 && (this.useFiltersOn(r.getExtendz()) 
						 && !r.getExtendz().isAbstract());
	
	}
		
	
	
	

}
