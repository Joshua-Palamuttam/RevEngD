package csse374.revengd.application;

import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import soot.SootClass;

public class DIPViolationDetector extends Analyzable {
	
	public static final String PATTERN = "dipviolation";
	public static final String VIOLATOR = "violator";
	public static final String DEPENDENCY = "dependency";
	private AnalyzableData data;
	

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		this.data = data;
		Collection<Relationship> relationships = data.getRelationships();
		relationships.forEach(r -> {
			if(this.useFiltersOn(r.getThisClass())){
				IPattern pattern = new Pattern(PATTERN);
				boolean concreteReference = hasConcreteReference(r, pattern);
				boolean concreteInheritance = hasConcreteInheritance(r, pattern);
				boolean methodOverride = methodOverride(r, pattern);
				if(concreteReference || concreteInheritance || methodOverride){
				
				pattern.putComponent(VIOLATOR, r);
				data.putPattern(PATTERN, pattern);
				System.out.println("skinny DIP !!!!!!!!!!! "+r.getThisClass().getName());
				}
			}
		});
		this.data = null;

	}


	private boolean hasConcreteReference(Relationship r, IPattern pattern) { 
		Set<SootClass> references = new HashSet<>();
		references.addAll(r.getHas().keySet());
		references.addAll(r.getUses().keySet());
		boolean notReturn = references.stream().allMatch(ref -> {
			if(this.useFiltersOn(ref) && ref.isConcrete()){
				pattern.putComponent(DEPENDENCY, data.getRelationship(ref));
				return false;
			}
			return true;
		});
		
		return !notReturn ;
	}
	
	private boolean methodOverride(Relationship r, IPattern pattern) {
		SootClass extendz = r.getExtendz();
		boolean notReturn = extendz.getMethods().stream().allMatch(m -> {
			if(this.useFiltersOn(m) && m.isConcrete()){
				if(r.getThisClass().getMethodByName(m.getName()) != null){
					pattern.putComponent(DEPENDENCY, data.getRelationship(extendz));
					return false;
				}
				
			}
			return true;
				
		});
		return !notReturn;
	}
	
	private boolean hasConcreteInheritance(Relationship r, IPattern pattern) {
		SootClass extendz = r.getExtendz();
		if(this.useFiltersOn(extendz) && !extendz.getName().equals("java.lang.Object") && extendz.isConcrete()){
			pattern.putComponent(DEPENDENCY, data.getRelationship(extendz));
			return true;
		}
		return false;
	}
}
