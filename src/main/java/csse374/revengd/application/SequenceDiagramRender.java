package csse374.revengd.application;

import java.io.OutputStream;

import soot.Body;
import soot.Scene;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.tagkit.LineNumberTag;
import soot.tagkit.Tag;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class SequenceDiagramRender extends Analyzable {
	private UnitGraph graph;

	public void analyze(AnalyzableData data, OutputStream out) {
		int depth = Integer.parseInt(data.getConfigMap().get("--depth"));
		String methodName = data.getConfigMap().get("--method");
		Scene scene = data.getScene();
		SootMethod entryMethod = scene.getMethod(methodName);
		StringBuilder str = new StringBuilder();
		str.append("@startuml\n");
		recursiveMethodGenerator(entryMethod, str, depth);

		str.append("@enduml");

		System.out.println(str);
		data.setUmlText(str.toString());

	}

	public void recursiveMethodGenerator(SootMethod method, StringBuilder str, int depth) {
		System.out.println(method.getSignature());
		if(depth == 0 || method.getDeclaringClass().getName().startsWith("java")) {
			return;
		}
		
		Body body = method.retrieveActiveBody();
		UnitGraph cfg = new ExceptionalUnitGraph(body);
		cfg.forEach(stmt -> {
			Value op = null;
			if (stmt instanceof AssignStmt) {
				op = ((AssignStmt) stmt).getRightOp();
				if (op instanceof InvokeExpr) {
					InvokeExpr invkExpr = (InvokeExpr) op;
					SootMethod nextMethod = invkExpr.getMethod();
					recursiveMethodGenerator(nextMethod, str, depth - 1);
				}
			} else if (stmt instanceof InvokeStmt) {
				SootMethod nextMethod = ((InvokeStmt) stmt).getInvokeExpr().getMethod();
				
				recursiveMethodGenerator(nextMethod, str, depth - 1);
			}
		});
	}

	void prettyPrint(String title, Iterable<Unit> stmts) {
		System.out.println("-------------------------------------------------");
		System.out.println(title);
		System.out.println("-------------------------------------------------");
		stmts.forEach(stmt -> {
			System.out.format("[%d] [%s] %s%n", this.getLineNumber(stmt), stmt.getClass().getName(), stmt.toString());
		});
		System.out.println("-------------------------------------------------");
	}

	int getLineNumber(Unit stmt) {
		for (Tag tag : stmt.getTags()) {
			if (tag instanceof LineNumberTag)
				return ((LineNumberTag) tag).getLineNumber();
		}
		return -1;
	}

}
