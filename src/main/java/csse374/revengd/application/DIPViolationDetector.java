package csse374.revengd.application;

import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.internal.JCastExpr;
import soot.jimple.internal.JNewExpr;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class DIPViolationDetector extends Analyzable {

	public static final String PATTERN = "dipviolation";
	public static final String VIOLATOR = "violator";
	public static final String DEPENDENCY = "dependency";
	public static final String METHOD_OVERRIDE = "methodoverride";
	public static final String FIELD = "field";
	public static final String REFERENCE = "reference";
	
	private AnalyzableData data;
	private Scene scene;

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		this.data = data;
		this.scene = data.getScene();
		Collection<Relationship> relationships = data.getRelationships();
		relationships.forEach(r -> {
			if (this.useFiltersOn(r.getThisClass())) {
				IPattern pattern = new Pattern(PATTERN);
				boolean concreteReference = hasConcreteReference(r, pattern);
				boolean concreteInheritance = hasConcreteInheritance(r, pattern);
				boolean methodOverride = methodOverride(r, pattern);
				if (concreteReference || concreteInheritance || methodOverride) {
					pattern.putComponent(VIOLATOR, r);
					data.putPattern(PATTERN, pattern);
				}
			}
		});
		this.data = null;

	}

	private boolean hasConcreteReference(Relationship r, IPattern pattern) {
		boolean hasIt = false;
		
		for (SootField f : r.getThisClass().getFields()) {
			for (SootClass ref : TypeResolver.resolve(f, data.getScene()).keySet()) {
				if (this.badConcreteClass(ref)) {
					pattern.putComponent(DEPENDENCY, data.getRelationship(ref));
					pattern.putField(FIELD, f);
					hasIt = true;
				}
			}
		}
		
		for (SootMethod m : r.getThisClass().getMethods()) {
			boolean paramCheck = false;
			boolean usesNew = false;
			boolean usesCast = false;
			boolean localVar = false;
			
			for (SootClass c : TypeResolver.resolve(m, data.getScene()).keySet()) {
				if (badConcreteClass(c)) {
					paramCheck = true;
					pattern.putComponent(DEPENDENCY, data.getRelationship(c));
				}
			}
			
			if (m.isConcrete()) {
				Body body = m.retrieveActiveBody();
				UnitGraph cfg = new ExceptionalUnitGraph(body);
				for (Unit stmt : cfg) {
					Value op = null;
					if (stmt instanceof AssignStmt) {
						SootClass local = scene.getSootClass(((AssignStmt) stmt).getRightOp().getType().toString());
						if (this.badConcreteClass(local)) {
							localVar = true;
							pattern.putComponent(DEPENDENCY, data.getRelationship(local));
						}
						
						op = ((AssignStmt) stmt).getRightOp();
						if (op instanceof JNewExpr) {
							usesNew = true;
							JNewExpr newStmt = (JNewExpr) op;
							SootClass newClass = scene.getSootClass(newStmt.getType().toString());
							if (this.useFiltersOn(newClass)) {
								pattern.putComponent(DEPENDENCY, 
									data.getRelationship(newClass));
							}
							
						} else if (op instanceof JCastExpr) {
							usesCast = true;
							JCastExpr castStmt = (JCastExpr) op;
							SootClass newClass = scene.getSootClass(castStmt.getType().toString());
							if (this.useFiltersOn(newClass)) {
								pattern.putComponent(DEPENDENCY, 
									data.getRelationship(newClass));
							}
						}
					} else if (stmt instanceof JNewExpr) {
						usesNew = true;
						JNewExpr newStmt = (JNewExpr) stmt;
						SootClass newClass = scene.getSootClass(newStmt.getType().toString());
						if (this.useFiltersOn(newClass)) {
							pattern.putComponent(DEPENDENCY, 
								data.getRelationship(newClass));
						}
					} else if (stmt instanceof JCastExpr) {
						usesCast = true;
						JCastExpr castStmt = (JCastExpr) stmt;
						SootClass newClass = scene.getSootClass(castStmt.getType().toString());
						if (this.useFiltersOn(newClass)) {
							pattern.putComponent(DEPENDENCY, 
								data.getRelationship(newClass));
						}
					}
				}
			}
			
			if (paramCheck || usesCast || usesNew || localVar) {
				hasIt = true;
				pattern.putMethod(REFERENCE, m);
			}
			
		}
		
		return hasIt;
	}
	
	private boolean badConcreteClass(SootClass clazz) {
		return this.useFiltersOn(clazz) 
				&& clazz.isConcrete()
				&& (clazz.getInterfaceCount() != 0
					|| (clazz.hasSuperclass() 
						&& !clazz.getSuperclass().getName().equals("java.lang.Object")));
	}

	private boolean methodOverride(Relationship r, IPattern pattern) {
		Set<SootClass> superClasses = new HashSet<>();
		SootClass extendz = r.getExtendz();
		SootClass superClazz = extendz;
		while (superClazz != null) {
			superClasses.add(superClazz);
			if (superClazz.getName().equals("java.lang.Object"))
				break;
			superClazz = superClazz.getSuperclass();
		}

		Set<SootMethod> methods = new HashSet<>();
		for (SootClass clazz : superClasses) {
			methods.addAll(clazz.getMethods());
		}
		boolean notReturn = methods.stream().allMatch(m -> {
			if (this.useFiltersOn(m) && !m.isConstructor() && m.isConcrete()) {
				SootMethod thisMethod = r.getThisClass().getMethodUnsafe(m.getSubSignature());
				if (thisMethod != null) {
					pattern.putComponent(DEPENDENCY, data.getRelationship(m.getDeclaringClass()));
					pattern.putMethod(METHOD_OVERRIDE, thisMethod);
					return false;
				}

			}
			return true;

		});
		return !notReturn;
	}

	private boolean hasConcreteInheritance(Relationship r, IPattern pattern) {
		SootClass extendz = r.getExtendz();
		if (this.useFiltersOn(extendz) && !extendz.getName().equals("java.lang.Object") && extendz.isConcrete()) {
			pattern.putComponent(DEPENDENCY, data.getRelationship(extendz));
			return true;
		}
		return false;
	}
}
