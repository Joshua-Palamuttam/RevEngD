package csse374.revengd.application;

import java.io.OutputStream;
import java.util.Collection;

import soot.Scene;
import soot.SootClass;

public class CompInheritanceDetector extends Analyzable {
	public final static String PATTERN = "inheritance";
	public final static String SUBCLASS = "subclass";
	public final static String SUPERCLASS = "superclass";
	
	private Scene scene;
	
	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		this.scene = data.getScene();
		Collection<Relationship> relationships = data.getRelationships();
		relationships.forEach(r -> {
			if(this.useFiltersOn(r.getThisClass()) && isInheritance(r)){
				System.out.println(r.getThisClass()+" "+r.getExtendz());
				IPattern pattern = new Pattern(PATTERN);
				pattern.putComponent(SUBCLASS, r);
				pattern.putComponent(SUPERCLASS, data.getRelationship(r.getExtendz()));
				data.putPattern(PATTERN, pattern);
//				System.out.println("INHERITANCE!!!!!!!! " +r.getThisClass());
			}
			
		});
		
	}

	private boolean isInheritance(Relationship r) {
		if (r.getExtendz() == null
				|| !this.useFiltersOn(r.getThisClass())
				|| !r.getThisClass().isConcrete()) {
			return false;
		}
		
		SootClass superClass = r.getExtendz();
		
		return !r.getThisClass().getMethods().stream()
			.filter(m -> !m.isConstructor())
			.filter(m -> superClass.declaresMethod(m.getSubSignature()))
			.allMatch(m -> TypeResolver.methodBodyUsesField(m, superClass, this.scene));
	}
		
	
	
	

}
