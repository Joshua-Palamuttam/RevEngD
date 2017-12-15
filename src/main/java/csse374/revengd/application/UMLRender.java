package csse374.revengd.application;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Set;

import soot.SootClass;

public class UMLRender extends Analyzable {

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		String filterName = data.getConfigMap().get("--accesslevel");
	
		if(this.availableFilterMap != null){
			
			if(this.availableFilterMap.containsKey(filterName)){
				this.activeFilters.add(this.availableFilterMap.get(filterName));
			}
		}
		
		StringBuilder str = new StringBuilder();
		Collection<Relationship> relationships = data.getRelationships();
		str.append("@startuml\n");
		if(relationships != null) {
			
			relationships.forEach(r ->{
				boolean keep = true;
				if(!activeFilters.isEmpty()) {
					for(IFilter f : activeFilters) {
						keep &= f.filterClass(r.getThisClass());
					}
				}
				
				if(keep) {
					str.append(
							getClassString(r)
							+" { \n"
							+ getFieldsString(r)
							+ getMethodsString(r)
							+"} \n"
							+ getExtendsString(r)
							+ getImplementsString(r)
							+ getHasAString(r)
							+ getUsesString(r));
				}
			});	
		}
		
		str.append("@enduml");
		System.out.println(str);
		data.setUmlText(str.toString());
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
			str.append(r.getThisClass().getName());
		return str.toString();
	}
	
	public String getFieldsString(Relationship r) {
		StringBuilder str = new StringBuilder();
		
		r.getThisClass().getFields().forEach(f ->{
			
			boolean keep = true;
			if(!activeFilters.isEmpty()) {
				for(IFilter fil : activeFilters) {
					keep &= fil.filterField(f);
				}
			}
			
			if(keep) {
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
				
				str.append(modifiers + splitField[1] + "\n");
			}
			
		});
		return str.toString();
	}
	
	public String getMethodsString(Relationship r) {
		StringBuilder str = new StringBuilder();
		r.getThisClass().getMethods().forEach(m ->{
			
			boolean keep = true;
			if(!activeFilters.isEmpty()) {
				for(IFilter f : activeFilters) {
					keep &= f.filterMethod(m);
				}
			}
			
			if(keep) {
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
				
				str.append(modifiers + splitField[1] + "\n");
			}
			
		});
		return str.toString();
	}
	
	public String getExtendsString(Relationship r) {
		if(r.getExtendz() != null){
			
			boolean keep = true;
			if(!activeFilters.isEmpty()) {
				for(IFilter f : activeFilters) {
					keep &= f.filterClass(r.getExtendz());
				}
			}
			
			if(keep) {
			
				
				if( r.getExtendz().getName().contains("$")){
					return r.getThisClass().getName()+ " --|> "+r.getExtendz().getName()+"\n";
					
				}
				else{
					return getClassString(r)+ " extends "+r.getExtendz().getName()+"\n";
				}
			}
		}
		return "";
	}
	
	public String getImplementsString(Relationship r) {
		StringBuilder str = new StringBuilder();
		Set<SootClass> implementz = r.getImplementz();
		if(implementz != null){
			String className = getClassString(r);
			implementz.forEach(i ->{
				boolean keep = true;
				if(!activeFilters.isEmpty()) {
					for(IFilter f : activeFilters) {
						keep &= f.filterClass(i);
					}
				}
				
				if(keep) {
					
					if(i.getName().contains("$")){
						
						str.append(r.getThisClass().getName()+ " ..|> ");

					}
					else {
						str.append(className + " implements ");

					}
					str.append(i.getName()+"\n");
				}
				
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
				
				boolean keep = true;
				if(!activeFilters.isEmpty()) {
					for(IFilter f : activeFilters) {
						keep &= f.filterClass(u);
					}
				}
				
				if(keep) {
					str.append(className + " ..> " + u.getName() + "\n");
				}
				
	
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
				boolean keep = true;
				if(!activeFilters.isEmpty()) {
					for(IFilter f : activeFilters) {
						keep &= f.filterClass(h);
					}
				}
				
				if(keep) {
					str.append(className + " --> " + h.getName() + "\n");
				}
				
				
			});
		}
		return str.toString();
	}

}
