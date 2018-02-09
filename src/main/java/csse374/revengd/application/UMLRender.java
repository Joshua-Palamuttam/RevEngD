package csse374.revengd.application;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.rosehulman.jvm.sigevaluator.FieldEvaluator;
import edu.rosehulman.jvm.sigevaluator.GenericType;
import edu.rosehulman.jvm.sigevaluator.MethodEvaluator;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Type;
import soot.tagkit.Tag;

public class UMLRender extends Analyzable {
	private Collection<UMLModifier> modifiers;

	public UMLRender(Collection<UMLModifier> modifiers) {
		this.modifiers = modifiers;
	}

	public UMLRender() {
		this.modifiers = new ArrayList<>();
	}

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		StringBuilder str = new StringBuilder();
		Collection<Relationship> relationships = data.getRelationships();
		str.append("@startuml\n");
		for (UMLModifier m : this.modifiers) {
			m.setAnalyzableData(data);
			str.append(m.getHeaderMod());
		}
		if (!data.getConfigMap().containsKey("curved") || data.getConfigMap().get("curved").equals("false")) {
			str.append("skinparam linetype ortho\n");
		}
		if (relationships != null) {

			relationships.forEach(r -> {
				if (this.useFiltersOn(r.getThisClass())) {
					str.append(getClassString(r))
						.append(" { \n") 
						.append(getFieldsString(r)) 
						.append(getMethodsString(r))
						.append("} \n")
						.append(getExtendsString(r))
						.append(getImplementsString(r))
						.append(getHasAString(r))
						.append(getUsesString(r))
						.append(getFooterString(r));
				}
			});
		}

		str.append("@enduml");
		System.out.println(str);
		data.setUmlText(str.toString());
	}

	public String getClassString(Relationship r) {
		StringBuilder str = new StringBuilder();

		if (r.getThisClass().isInterface()) {
			str.append("interface ");
		} else if (r.getThisClass().isAbstract()) {
			str.append("abstract ");
		} else {
			str.append("class ");
		}
		str.append(r.getThisClass().getName());
		for (UMLModifier m : this.modifiers) {
			String modified = m.getClassMod(str.toString(), r.getThisClass());
			if (!modified.equals(str.toString())) {
				return modified;
			}
		}
		return str.toString();
	}

	public String getFieldsString(Relationship r) {
		StringBuilder str = new StringBuilder();

		r.getThisClass().getFields().forEach(f -> {
			if (this.useFiltersOn(f)) {
				String toAppend = getSingularFieldString(f);
				for (UMLModifier mod : this.modifiers) {
					String modifier = mod.getFieldMod(toAppend, f);
					if (!modifier.equals(toAppend)) {
						toAppend = modifier;
						break;
					}
				}
				str.append(toAppend);
				str.append("\n");
			}
		});
		for (UMLModifier mod : this.modifiers) {
			String modifier = mod.getExtraFields(r);
			if (!modifier.equals("")) {
				str.append(modifier);
				break;
			}
		}
		return str.toString();
	}

	public String getMethodsString(Relationship r) {
		StringBuilder str = new StringBuilder();
		r.getThisClass().getMethods().forEach(m -> {
			if (this.useFiltersOn(m)) {
				String toAppend = getSingularMethodString(m);
				for (UMLModifier mod : this.modifiers) {
					String modifier = mod.getMethodMod(toAppend, m);
					if (!modifier.equals(toAppend)) {
						toAppend = modifier;
						break;
					}
				}
				str.append(toAppend);
				str.append("\n");
			}
		});
		for (UMLModifier mod : this.modifiers) {
			String modifier = mod.getExtraMethods(r);
			if (!modifier.equals("")) {
				str.append(modifier);
				break;
			}
		}
		return str.toString();
	}

	public String getExtendsString(Relationship r) {
		String toReturn = "";
		if (r.getExtendz() != null && this.useFiltersOn(r.getExtendz())) {
			toReturn = r.getThisClass().getName() + " -up--|> " + r.getExtendz().getName();
			
			for (UMLModifier m : this.modifiers) {
				String modifier = m.getExtendsMod(toReturn, r.getThisClass(), r.getExtendz());
				if (!modifier.equals(toReturn)) {
					toReturn = modifier;
					break;
				}
			}
			return toReturn + "\n";
		}
		return toReturn;

	}

	public String getImplementsString(Relationship r) {
		StringBuilder str = new StringBuilder();
		Set<SootClass> implementz = r.getImplementz();
		if (implementz != null) {
			implementz.forEach(i -> {
				if (this.useFiltersOn(i)) {
					String toAppend = getSingularImplementsString(r.getThisClass(), i);
					for (UMLModifier m : this.modifiers) {
						String modifier = m.getImplementsMod(toAppend, r.getThisClass(), i);
						if (!modifier.equals(toAppend)) {
							toAppend = modifier;
							break;
						}
					}
					str.append(toAppend);
					str.append("\n");
				}
			});
		}
		return str.toString();
	}

	public String getUsesString(Relationship r) {
		StringBuilder str = new StringBuilder();
		Map<SootClass, Boolean> usesMap = r.getUses();
		if (usesMap != null && !usesMap.isEmpty()) {
			Set<SootClass> usez = usesMap.keySet();
			SootClass startClazz = r.getThisClass();
			usez.forEach(u -> {
				if (r.getExtendz() != null && (r.getExtendz().equals(u) || r.getImplementz().contains(u) || r.getHas().containsKey(u) || startClazz.equals(u))) {
					return;
				}
				if (this.useFiltersOn(u)) {
					String toAppend = getSingularUsesString(startClazz, u, r.usesMany(u));
					for (UMLModifier m : this.modifiers) {
						String modifier = m.getUsesMod(toAppend, startClazz, u);
						if (!modifier.equals(toAppend)) {
							toAppend = modifier;
							break;
						}
					}
					str.append(toAppend);
					str.append("\n");	
				}
			});
		}
		return str.toString();
	}

	public String getHasAString(Relationship r) {
		StringBuilder str = new StringBuilder();
		Map<SootClass, Boolean> hasMap = r.getHas();

		if (hasMap != null && !hasMap.isEmpty()) {
			Set<SootClass> haz = hasMap.keySet();
			SootClass startClass = r.getThisClass();
			haz.forEach(h -> {
				if (this.useFiltersOn(h)) {
					String toAppend = getSingularHasString(startClass, h, r.usesMany(h));
					for (UMLModifier m : this.modifiers) {
						String modifier = m.getHasMod(toAppend, startClass, h);
						if (!modifier.equals(toAppend)) {
							toAppend = modifier;
							break;
						}
					}
					str.append(toAppend);
					str.append("\n");	
				}
			});
		}
		return str.toString();
	}
	
	private String getFooterString(Relationship r) {
		String footer = "";
		for (UMLModifier m : this.modifiers) {
			footer = m.getNotes(r);
			if (!footer.equals("")) {
				break;
			}
		}
		return footer;
	}
	
	public static String getSingularMethodString(SootMethod m) {
		StringBuilder str = new StringBuilder();

		String prefix = "";
		if (m.isPrivate()) {
			prefix += "- ";
		} else if (m.isProtected()) {
			prefix += "# ";
		} else if (m.isPublic()) {
			prefix += "+ ";
		} else {
			prefix += "~ ";
		}

		if (m.isAbstract()) {
			prefix += "{abstract} ";
		}
		if (m.isStatic()) {
			prefix += "{static} ";
		}
		if (m.isFinal()) {
			prefix += "final ";
		}

		String rType;
		StringBuilder params = new StringBuilder();
		Tag signatureTag = m.getTag("SignatureTag");
		
		if (signatureTag != null) {
			String signature = signatureTag.toString();
			MethodEvaluator methodEvaluator = new MethodEvaluator(signature);
			
			try {
				GenericType returnType = methodEvaluator.getReturnType();
				rType = returnType.toString();
			} catch (RuntimeException e) {
				rType = m.getReturnType().toString();
			}
			try {
				List<GenericType> paramTypes = methodEvaluator.getParameterTypes();
				for (GenericType param : paramTypes) {
					params.append(param.toString());
					params.append(", ");
				}
			} catch (RuntimeException e) {
				for (Type type : m.getParameterTypes()) {
					params.append(type.toString());
					params.append(", ");
				}
			}
		} else {
			rType = m.getReturnType().toString() + " ";
			List<Type> paramTypes = m.getParameterTypes();

			for (Type param : paramTypes) {
				params.append(param.toString());
				params.append(", ");
			}
		}

		if (params.length() > 0) {
			params.delete(params.length() - 2, params.length());
		}

		str.append(prefix + rType + " " + m.getName() + "(" + params.toString() + ")");

		return str.toString();
	}
	
	public static String getSingularFieldString(SootField f) {
		StringBuilder str = new StringBuilder();
		
		String access = "";
		if (f.isPrivate()) {
			access += "- ";
		} else if (f.isProtected()) {
			access += "# ";
		} else if (f.isPrivate()) {
			access += "+ ";
		} else {
			access += "~ ";
		}

		if (f.isStatic()) {
			access += "{static} ";
		}
		if (f.isFinal()) {
			access += "final ";
		}

		String type;
		Tag signatureTag = f.getTag("SignatureTag");
		if (signatureTag != null) {
			// Use SignatureEvaluator API for parsing the field signature
			String signature = signatureTag.toString();
			FieldEvaluator fieldEvaluator = new FieldEvaluator(signature);
			try {
				GenericType fieldType = fieldEvaluator.getType();
				type = fieldType.toString();
			} catch (IllegalStateException e) {
				type = f.getType().toString();
			}
		} else {
			// Bytecode signature for this field is unavailable, so let's use soot API
			type = f.getType().toString();
		}

		String name = f.getName();
		str.append(access + type + " " + name);
	
		return str.toString();
	}
	
	public static String getSingularHasString(SootClass startClazz, SootClass endClazz, boolean many) {
		String className = startClazz.getName();
		String arrowEnd = many ? "\"*\" " : "";
		return className + " --> " + arrowEnd + endClazz.getName();
	}
	
	public static String getSingularUsesString(SootClass startClazz, SootClass endClazz, boolean many) {
		String className = startClazz.getName();
		String arrowEnd = many ? "\"*\" " : "";
		return className + " ..> " + arrowEnd + endClazz.getName();
	}
	
	public static String getSingularImplementsString(SootClass startClazz, SootClass endClazz) {
			return startClazz.getName() + " -up..|> " + endClazz.getName();
	}

	public void addModifier(UMLModifier m) {
		this.modifiers.add(m);
	}

}
