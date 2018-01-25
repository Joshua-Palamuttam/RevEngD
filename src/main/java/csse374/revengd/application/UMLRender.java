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
		if (!data.getConfigMap().containsKey("curved")) {
			str.append("skinparam linetype ortho\n");
		}
		if (relationships != null) {

			relationships.forEach(r -> {
				boolean keep = this.useFiltersOn(r.getThisClass());

				if (keep) {
					str.append(getClassString(r) + " { \n" + getFieldsString(r) + getMethodsString(r) + "} \n"
							+ getExtendsString(r) + getImplementsString(r) + getHasAString(r) + getUsesString(r));
				}
			});
		}

		str.append("@enduml");
		System.out.println(str);
		data.setUmlText(str.toString());
		// out.write(str.toString());
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
			String classMod = m.getClassMod(r.getThisClass());
			if (!classMod.equals("")) {
				str.append(classMod);
				break;
			}
		}
		return str.toString();
	}

	public String getFieldsString(Relationship r) {
		StringBuilder str = new StringBuilder();

		r.getThisClass().getFields().forEach(f -> {

			boolean keep = this.useFiltersOn(f);

			if (keep) {
				String modifiers = "";
				if (f.isPrivate()) {
					modifiers += "- ";
				} else if (f.isProtected()) {
					modifiers += "# ";
				} else {
					modifiers += "+ ";
				}

				if (f.isStatic()) {
					modifiers += "{static} ";
				}
				if (f.isFinal()) {
					modifiers += "final ";
				}

				String type;
				Tag signatureTag = f.getTag("SignatureTag");
				if (signatureTag != null) {
					// Use SignatureEvaluator API for parsing the field signature
					String signature = signatureTag.toString();
					FieldEvaluator fieldEvaluator = new FieldEvaluator(signature);
					GenericType fieldType = fieldEvaluator.getType();
					type = fieldType.toString();
				} else {
					// Bytecode signature for this field is unavailable, so let's use soot API
					type = f.getType().toString();
				}

				String name = f.getName();
				str.append(modifiers + type + " " + name + "\n");
			}

		});
		return str.toString();
	}

	public String getMethodsString(Relationship r) {
		StringBuilder str = new StringBuilder();
		r.getThisClass().getMethods().forEach(m -> {
			boolean keep = this.useFiltersOn(m);

			if (keep) {
				String modifiers = "";
				if (m.isPrivate()) {
					modifiers += "- ";
				} else if (m.isProtected()) {
					modifiers += "# ";
				} else {
					modifiers += "+ ";
				}

				if (m.isAbstract()) {
					modifiers += "{abstract} ";
				}
				if (m.isStatic()) {
					modifiers += "{static} ";
				}
				if (m.isFinal()) {
					modifiers += "final ";
				}

				String rType;
				StringBuilder params = new StringBuilder();
				Tag signatureTag = m.getTag("SignatureTag");

				// TODO: Workaround because java.* breaks methodEvaluator
				if (signatureTag != null && !m.getDeclaringClass().toString().startsWith("java")) {
					// Use SignatureEvaluator API for parsing the field signature
					String signature = signatureTag.toString();
					MethodEvaluator methodEvaluator = new MethodEvaluator(signature);

					GenericType returnType = methodEvaluator.getReturnType();
					rType = returnType.toString();
					List<GenericType> paramTypes = methodEvaluator.getParameterTypes();
					for (GenericType param : paramTypes) {
						params.append(param.toString());
						params.append(", ");
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

				str.append(modifiers + rType + " " + m.getName() + "(" + params.toString() + ")\n");
			}

		});
		return str.toString();
	}

	public String getExtendsString(Relationship r) {

		if (r.getExtendz() != null) {
			boolean keep = this.useFiltersOn(r.getExtendz());
			if (keep) {
				String modifier = "";
				for (UMLModifier m : this.modifiers) {
					modifier = m.getExtendsMod(r.getThisClass(), r.getExtendz());
					if (!modifier.equals("")) {
						break;
					}
				}
				return r.getThisClass().getName() + " -up--|> " + r.getExtendz().getName() + " " + modifier + "\n";
			}
		}
		return "";

	}

	public String getImplementsString(Relationship r) {
		StringBuilder str = new StringBuilder();
		Set<SootClass> implementz = r.getImplementz();
		if (implementz != null) {
			implementz.forEach(i -> {
				boolean keep = this.useFiltersOn(i);

				if (keep) {
					String modifier = "";
					for (UMLModifier m : this.modifiers) {
						modifier = m.getImplementsMod(r.getThisClass(), i);
						if (!modifier.equals("")) {
							break;
						}
					}
					
					str.append(r.getThisClass().getName() + " -up..|> " + i.getName() + modifier + "\n");
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
			String className = r.getThisClass().getName();
			usez.forEach(u -> {
				boolean keep = this.useFiltersOn(u);
				// keep &= !r.getHas().containsKey(u);

				String arrowEnd = usesMap.get(u) ? "\"*\" " : "";

				if (keep) {
					String modifier = "";
					for (UMLModifier m : this.modifiers) {
						modifier = m.getUsesMod(r.getThisClass(), u);
						if (!modifier.equals("")) {
							break;
						}
					}
					
					str.append(className + " ..> " + arrowEnd + u.getName() + modifier + "\n");
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
			String className = r.getThisClass().getName();
			haz.forEach(h -> {
				boolean keep = this.useFiltersOn(h);

				String arrowEnd = hasMap.get(h) ? "\"*\" " : "";

				if (keep) {
					String modifier = "";
					for (UMLModifier m : this.modifiers) {
						modifier = m.getHasMod(r.getThisClass(), h);
						if (!modifier.equals("")) {
							break;
						}
					}
					
					str.append(className + " --> " + arrowEnd + h.getName() + modifier + "\n");
				}

			});
		}
		return str.toString();
	}

	public void addModifier(UMLModifier m) {
		this.modifiers.add(m);
	}

}
