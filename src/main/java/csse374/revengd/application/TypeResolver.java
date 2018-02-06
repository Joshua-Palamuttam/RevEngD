package csse374.revengd.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.rosehulman.jvm.sigevaluator.FieldEvaluator;
import edu.rosehulman.jvm.sigevaluator.GenericType;
import edu.rosehulman.jvm.sigevaluator.MethodEvaluator;
import soot.Scene;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Type;
import soot.tagkit.Tag;

public class TypeResolver {
	public static Map<SootClass, Boolean> resolveMethodParameters(SootMethod m, Scene scene) {
		return resolveMethod(m, scene, "parameter");
	}

	private static Map<SootClass, Boolean> resolveMethod(SootMethod m, Scene scene, String s) {
		Tag signatureTag = m.getTag("SignatureTag");
		Map<SootClass, Boolean> classMap = new HashMap<>();
		if (signatureTag != null) {
			// Use SignatureEvaluator API for parsing the field signature
			String signature = signatureTag.toString();
			MethodEvaluator methodEvaluator = new MethodEvaluator(signature);
			Set<GenericType> depends = new HashSet<>();
			try {
				if (s.equals("return")) {
					depends.add(methodEvaluator.getReturnType());
				} else if (s.equals("parameter")) {
					depends.addAll(methodEvaluator.getParameterTypes());
				} else {
					depends.add(methodEvaluator.getReturnType());
					depends.addAll(methodEvaluator.getParameterTypes());
				}

			} catch (RuntimeException e) {

			}
			for (GenericType depend : depends) {
				// elementTypes is empty if the GenericType is not a collection
				Set<String> elementTypes = depend.getAllElementTypes();
				if (elementTypes.isEmpty()) {
					classMap.put(scene.getSootClass(depend.getContainerType()), false);
				} else {
					elementTypes.forEach(element -> {
						classMap.put(scene.getSootClass(element), true);
					});
				}
			}
		} else {
			Set<Type> depends = new HashSet<>();
			if (s.equals("return")) {
				depends.add(m.getReturnType());
			} else if (s.equals("parameter")) {
				depends.addAll(m.getParameterTypes());
			} else {
				depends.add(m.getReturnType());
				depends.addAll(m.getParameterTypes());
			}
			for (Type depend : depends) {
				String typeString = depend.toString();
				if (typeString.contains("[]")) {
					classMap.put(scene.getSootClass(typeString.replace("[]", "")), true);
				} else {
					classMap.put(scene.getSootClass(typeString), false);
				}
			}
		}
		return classMap;
	}

	public static Map<SootClass, Boolean> resolveMethodReturnType(SootMethod m, Scene scene) {
		return resolveMethod(m, scene, "return");

	}

	public static Map<SootClass, Boolean> resolve(SootMethod m, Scene scene) {
		return resolveMethod(m, scene, "");

	}

	public static Map<SootClass, Boolean> resolve(SootField f, Scene scene) {
		Tag signatureTag = f.getTag("SignatureTag");
		Map<SootClass, Boolean> classMap = new HashMap<>();
		if (signatureTag != null) {
			// Use SignatureEvaluator API for parsing the field signature
			String signature = signatureTag.toString();
			FieldEvaluator fieldEvaluator = new FieldEvaluator(signature);
			GenericType fieldType = fieldEvaluator.getType();
			Set<String> elementTypes = fieldType.getAllElementTypes();
			elementTypes.forEach(element -> {
				classMap.put(scene.getSootClass(element), true);
			});
			// Add container types as well maybe
		}
		String typeString = f.getType().toString();
		if (typeString.contains("[]")) {
			classMap.put(scene.getSootClass(typeString.replace("[]", "")), true);
		} else {
			classMap.put(scene.getSootClass(typeString), false);
		}
		return classMap;

	}
}
