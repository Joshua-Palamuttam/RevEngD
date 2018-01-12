package csse374.revengd.application;

import java.io.OutputStream;
import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class SequenceDiagramRender extends Analyzable {
	private UnitGraph graph;
	private Scene scene;

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		int depth = 5;
		if (data.getConfigMap().containsKey("--depth")){
			depth = Integer.parseInt(data.getConfigMap().get("--depth"));
		}
		String methodName = data.getConfigMap().get("--method");
		
		String filterName = "-JDK";
		if(this.availableFilterMap != null && data.getConfigMap().containsKey(filterName)){
			if(this.availableFilterMap.containsKey(filterName)){
				this.activeFilters.add(this.availableFilterMap.get(filterName));
			}
		}
		
		Scene scene = data.getScene();
		this.scene = scene;
		SootMethod entryMethod = scene.getMethod(methodName);
		StringBuilder str = new StringBuilder();
		str.append("@startuml\n");
		
		recursiveMethodGenerator(entryMethod, str, depth, entryMethod.getDeclaringClass(), true);

		str.append("@enduml");

		System.out.println(str);
		data.setUmlText(str.toString());

	}

	public void recursiveMethodGenerator(SootMethod method, StringBuilder str, int depth, SootClass callingClass, boolean toplevel) {
		if (!this.useFiltersOn(method)) {
			return;
		}
		String callingName = callingClass.getName();
		String recievingName = method.getDeclaringClass().getName();
		if (toplevel) {
			str.append(" -> " + recievingName + ": ");
		} else {
			str.append(callingName + " -> " + recievingName + ": ");
		}
		String[] methodArray = method.getSignature().split(" ");
		String methodCall = methodArray[methodArray.length - 1];
		str.append(methodCall.substring(0, methodCall.length()-1) + "\n");
		boolean active = (callingClass.equals(method.getDeclaringClass()));
		if (!active) {
			str.append("activate " + recievingName + "\n");
		}
		System.out.println(method.getSignature());

		if (depth == 0 || method == null || method.getDeclaringClass().getName().startsWith("java")) {
			if (!active) {
				str.append("deactivate " + recievingName + "\n");
			}
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
					nextMethod = ResolvedMethodFinder.resolveMethod(this.scene, stmt, nextMethod);
					recursiveMethodGenerator(nextMethod, str, depth - 1, method.getDeclaringClass(), false);
				}
			} else if (stmt instanceof InvokeStmt) {
				SootMethod nextMethod = ((InvokeStmt) stmt).getInvokeExpr().getMethod();

				nextMethod = ResolvedMethodFinder.resolveMethod(this.scene, stmt, nextMethod);
				recursiveMethodGenerator(nextMethod, str, depth - 1, method.getDeclaringClass(), false);
			}
		});

		Type returnType = method.getReturnType();
		if (!returnType.toString().equals("void")) {
			if (toplevel) {
				str.append("<-- " + callingName + ": " + returnType + "\n");
			} else {
				str.append(recievingName + " --> " + callingName + ": " + returnType + "\n");
			}
		}
		if (!active) {
			str.append("deactivate " + recievingName + "\n");
		}

	}

}
