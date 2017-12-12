package csse374.revengd.application;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Set;

import soot.SootClass;

public class UMLRender extends Analyzable {

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		StringBuilder str = new StringBuilder();
		Collection<Relationship> relationships = data.getRelationships();
		str.append("@startuml\n");
		if(relationships != null) {
			relationships.forEach(r ->{
				str.append(getClassString(r));
			});
			relationships.forEach(r ->{
				str.append(
						getFieldsString(r)
						+ getMethodsString(r)
						+ getExtendsString(r)
						+ getImplementsString(r)
						+ getHasAString(r)
						+ getUsesString(r));
			});
		}
		
		str.append("@enduml");
		System.out.println(str);
//		out.write(str.toString());
	}
	public String getClassString(Relationship r){
		StringBuilder str = new StringBuilder();
		
			if(r.getThisClass().isInterface()){
				str.append("interface ");
			}else if(r.getThisClass().isAbstract()){
				str.append("abstract ");
			}else{
				str.append("class ");
			}
			str.append(r.getThisClass().getName()+"\n");
		return str.toString();
	}
	
	public String getFieldsString(Relationship r) {
		StringBuilder str = new StringBuilder();
		
		r.getThisClass().getFields().forEach(f ->{
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
		return str.toString();
	}
	
	public String getMethodsString(Relationship r) {
		StringBuilder str = new StringBuilder();
		r.getThisClass().getMethods().forEach(m ->{
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
	
	public String getExtendsString(Relationship r) {
		if(r.getExtendz() != null){
			return getClassString(r).trim() + " extends " + r.getExtendz().getName() + "\n";
		}
		return "";
	}
	
	public String getImplementsString(Relationship r) {
		StringBuilder str = new StringBuilder();
		Set<SootClass> implementz = r.getImplementz();
		if(implementz != null){
			String className = getClassString(r).trim();
			implementz.forEach(i ->{
				str.append(className + " implements " + i.getName() + "\n");
			});
				
		}
		return str.toString();
	}
	
	public String getUsesString(Relationship r) {
		StringBuilder str = new StringBuilder();
		Set<SootClass> usez = r.getUses();
		if(usez != null){
			String className = r.getThisClass().getName();
			usez.forEach(u ->{
				str.append(className + " ..> " + u.getName() + "\n");
			});
				
		}
		return str.toString();
	}
	
	public String getHasAString(Relationship r) {
		StringBuilder str = new StringBuilder();
		Set<SootClass> haz = r.getHas();
		if(haz != null){
			String className = r.getThisClass().getName();
			haz.forEach(h ->{
				str.append(className + " --> " + h.getName() + "\n");
			});
		}
		return str.toString();
	}

}
