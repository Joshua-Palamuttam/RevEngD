package csse374.revengd.application;

import java.io.OutputStream;
import java.util.*;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.internal.JNewExpr;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.util.Chain;

public class RelationshipFinder extends Analyzable {
	private AnalyzableData data;
	private boolean analyzeBodies;

	@SuppressWarnings("hiding")
	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		this.data = data;
		this.analyzeBodies = !(data.getConfigMap().containsKey("method_bodies")
				&& data.getConfigMap().get("method_bodies").equals("false"));
		Set<SootClass> sootClasses = data.getSootClasses();
		Collection<Relationship> relationships = new ArrayList<>();
		sootClasses.forEach(clazz -> {
			Relationship r = new Relationship(clazz);
			// order matters here
			extendsAFinder(r);
			implementsAFinder(r);
			hasAFinder(r);
			usesAFinder(r);
			relationships.add(r);
			r.filterIn(sootClasses);
			// put IFilters in, probably in each method
		});
		data.setRelationships(relationships);
	}

	@SuppressWarnings("boxing")
	private void hasAFinder(Relationship r) {
		SootClass clazz = r.getThisClass();
		if (clazz.getName().equals("java.lang.Object")) {
			return;
		}
		Scene scene = this.data.getScene();

		r.setHas(new HashMap<>());
		clazz.getFields().forEach(f -> {
			Map<SootClass, Boolean> toAdd = TypeResolver.resolve(f, scene);
			toAdd.keySet().forEach(c -> {
				r.addHas(c, toAdd.get(c));
			});
		});

	}

	private static void extendsAFinder(Relationship r) {
		SootClass clazz = r.getThisClass();
		if (!clazz.hasSuperclass()) {
			return;
		}
		SootClass sClazz = clazz.getSuperclass();
		r.setExtendz(sClazz);
	}

	public void implementsAFinder(Relationship r) {
		SootClass clazz = r.getThisClass();
		if (clazz.getName().equals("java.lang.Object")) {
			return;
		}
		Chain<SootClass> iClazz = clazz.getInterfaces();
		Set<SootClass> iClazzSet = new HashSet<>();
		iClazzSet.addAll(iClazz);
		r.setImplementz(iClazzSet);
	}

	@SuppressWarnings("boxing")
	private static void methodTypeFinder(Relationship r, SootMethod m, Scene scene) {
		Map<SootClass, Boolean> toAdd = TypeResolver.resolve(m, scene);
		toAdd.keySet().forEach(c -> {
			r.addUses(c, toAdd.get(c));
		});
	}

	public void usesAFinder(Relationship r) {
		SootClass clazz = r.getThisClass();
		if (clazz.getName().equals("java.lang.Object")) {
			return;
		}

		Scene scene = this.data.getScene();

		r.setUses(new HashMap<>());
		clazz.getMethods().forEach(m -> {
			methodTypeFinder(r, m, scene);

			if (this.analyzeBodies && m.isConcrete()) {
				
				// analyze method bodies
				Body body = m.retrieveActiveBody();
				UnitGraph cfg = new ExceptionalUnitGraph(body);
				cfg.forEach(stmt -> {
					Value op = null;
					SootMethod nextMethod = null;
					if (stmt instanceof AssignStmt) {
						op = ((AssignStmt) stmt).getRightOp();
						if (op instanceof InvokeExpr) {
							InvokeExpr invkExpr = (InvokeExpr) op;
							nextMethod = invkExpr.getMethod();
						} else if (op instanceof JNewExpr) {
							JNewExpr newStmt = (JNewExpr) op;
							r.addUses(scene.getSootClass(newStmt.getType().toString()), false);
						}
					} else if (stmt instanceof InvokeStmt) {
						nextMethod = ((InvokeStmt) stmt).getInvokeExpr().getMethod();
					} else if (stmt instanceof JNewExpr) {
						JNewExpr newStmt = (JNewExpr) stmt;
						r.addUses(scene.getSootClass(newStmt.getType().toString()), false);
					}

					if (nextMethod != null) {
						if (nextMethod.isConstructor()) {
							if (r.getThisClass().hasSuperclass() && !r.getThisClass().getSuperclass().equals(nextMethod.getDeclaringClass())) {
								r.addUses(nextMethod.getDeclaringClass(), false);
							}
						} else if (nextMethod.isStatic()) {
							r.addUses(nextMethod.getDeclaringClass(), false);
							methodTypeFinder(r, nextMethod, scene);
						} else {
							methodTypeFinder(r, nextMethod, scene);
						}
					}
				});
			}
		});
	}

}
