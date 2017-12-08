package csse374.revengd.application;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import soot.SootClass;
import soot.SootField;

public class Relationship {
	private SootClass thisClass;
	private Set<SootClass> has;
	private Set<SootClass> uses;
	private Set<SootClass> implementz;
	private SootClass extendz;
	
	public Relationship(SootClass clazz) {
		this.thisClass = clazz;
	}
	
	//This needs to change to remove concurrent modification exceptions
	public void filterIn(Set<SootClass> keep) {
		
		Predicate<SootClass> notInKeep = new Predicate<SootClass>() {
			@Override
			public boolean test(SootClass clazz) {
				return (!keep.contains(clazz));
			}
		};
		
		if (this.has != null) {
			this.has.removeIf(notInKeep);
		}
		
		if (this.uses != null) {
			this.uses.remove(notInKeep);
		}
		
		if (this.implementz != null){
			this.implementz.removeIf(notInKeep);
		}
		
		if (this.extendz != null && !keep.contains(this.extendz)) {
			this.extendz = null;
		}
	}
	
	public String getClassString(){
		StringBuilder str = new StringBuilder();
		
			if(thisClass.isInterface()){
				str.append("interface ");
			}
			else if(thisClass.isAbstract()){
				str.append("abstract ");
			}
			else{
				str.append("class ");
			}
			
			str.append(thisClass.getName()+"\n");
		
		thisClass.getFields().forEach(f ->{
			String modifiers = "";
			if(f.isPrivate()){
				modifiers += "- ";
			}
			else if(f.isProtected()){
				modifiers += "# ";
			}
			else{
				modifiers += "+ ";
			}
			
			if(f.isStatic()){
				modifiers += "{static} ";
			}
			if(f.isFinal()){
				modifiers += "final ";
			}
			String sig = f.getSignature().replace("<", "").replace(">", "");
			String [] splitField = sig.split(":");
			
			str.append(splitField[0] + " : " + modifiers + splitField[1] + "\n");
		});
		
		thisClass.getMethods().forEach(m ->{
			String modifiers = "";
			if(m.isPrivate()){
				modifiers += "- ";
			}
			else if(m.isProtected()){
				modifiers += "# ";
			}
			else{
				modifiers += "+ ";
			}
			
			if(m.isAbstract()){
				modifiers += "{abstract} ";
			}
			if(m.isStatic()){
				modifiers += "{static} ";
			}
			if(m.isFinal()){
				modifiers += "final ";
			}
			String sig = m.getSignature().replace("<", "").replace(">", "");
			String [] splitField = sig.split(":");
			
			str.append(splitField[0] + " : " + modifiers + splitField[1] + "\n");
		});
		return str.toString();
	}
	
	public String getRelateString(){
		StringBuilder str = new StringBuilder();
		if(this.has != null){
			this.has.forEach(clazz ->{
				str.append(this.thisClass.getName() + " --> " + clazz.getName() + "\n");
			});
		}
		
		if(this.uses != null){
			this.uses.forEach(clazz ->{
				str.append(this.thisClass.getName() + " ..> " + clazz.getName() + "\n");
			});
		}
		
		if(this.implementz != null){
			this.implementz.forEach(clazz ->{
				str.append(this.thisClass.getName() + " ..|> " + clazz.getName() + "\n");
			});
		}
		
		if(this.extendz != null){
			str.append(this.thisClass.getName() + " --|> " + this.extendz.getName() + "\n");
		}
		return str.toString();
	}

	public SootClass getThisClass() {
		return thisClass;
	}

	public void setThisClass(SootClass thisClass) {
		this.thisClass = thisClass;
	}

	public Set<SootClass> getHas() {
		return has;
	}

	public void setHas(Set<SootClass> has) {
		this.has = has;
	}

	public Set<SootClass> getUses() {
		return uses;
	}

	public void setUses(Set<SootClass> uses) {
		this.uses = uses;
	}

	public Set<SootClass> getImplementz() {
		return implementz;
	}

	public void setImplementz(Set<SootClass> implementz) {
		this.implementz = implementz;
	}

	public SootClass getExtendz() {
		return extendz;
	}

	public void setExtendz(SootClass extendz) {
		this.extendz = extendz;
	}
	

}
