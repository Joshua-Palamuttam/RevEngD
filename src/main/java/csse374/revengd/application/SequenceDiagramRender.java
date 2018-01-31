package csse374.revengd.application;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class SequenceDiagramRender extends Analyzable {
	private UnitGraph graph;
	private Scene scene;
	private IMethodResolutionAlgorithm mra;
	private Map<String, IMethodResolutionAlgorithm> mraMap;
	private Map<String, AggregateStrategy> agMap;

	public SequenceDiagramRender(Map<String, IMethodResolutionAlgorithm> mraMap, Map<String, AggregateStrategy> agMap2) {
		this.mraMap = mraMap;
		this.agMap = agMap2;
	}

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		int depth = 5;
		Map<String, String> configMap = data.getConfigMap();
		if (configMap.containsKey("depth")) {
			depth = Integer.parseInt(configMap.get("depth"));
		}
		String methodName = configMap.get("method");

		if (configMap.containsKey("mra")) {
			if (configMap.containsKey("aggregate")) {
				AggregateMRA aggie = new AggregateMRA(this.agMap.get(configMap.get("aggregate")));
				for (String s : configMap.get("mra").split(" ")) {
					aggie.add(this.mraMap.get(s));
				}
				this.mra = aggie;
			} else {
				this.mra = this.mraMap.get(configMap.get("mra").split(" ")[0]);
			}
		} else {
			this.mra = new CallGraphMRA();
		}

		this.scene = data.getScene();
		SootMethod entryMethod = this.scene.getMethod(methodName);
		StringBuilder str = new StringBuilder();
		str.append("@startuml\n");

		recursiveMethodGenerator(entryMethod, str, depth, entryMethod.getDeclaringClass(), true);

		str.append("@enduml");

		System.out.println(str);
		data.setUmlText(str.toString());

	}

	public void recursiveMethodGenerator(SootMethod method, StringBuilder str, int depth, SootClass callingClass,
			boolean toplevel) {
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
		str.append(methodCall.substring(0, methodCall.length() - 1) + "\n");
		
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

		if (method.isConcrete()) {
			Body body = method.retrieveActiveBody();
			UnitGraph cfg = new ExceptionalUnitGraph(body);
			cfg.forEach(stmt -> {
				Set<SootMethod> possibleMethods;
				Value op = null;
				if (stmt instanceof AssignStmt) {
					op = ((AssignStmt) stmt).getRightOp();
					if (op instanceof InvokeExpr) {
						InvokeExpr invkExpr = (InvokeExpr) op;
						SootMethod nextMethod = invkExpr.getMethod();
						possibleMethods = this.mra.resolve(this.scene, stmt, nextMethod);
						if(!possibleMethods.isEmpty()) {
							SootMethod fallback = nextMethod;
							nextMethod = possibleMethods.iterator().next();
							possibleMethods.add(fallback);
							for(SootMethod m : possibleMethods) {
								if(!m.equals(nextMethod) && this.useFiltersOn(m)) {
									str.append("'" + method.getDeclaringClass().toString() + " -> " + m.getDeclaringClass().toString() + ": ");
									String[] methodAlgorithmArray = nextMethod.getSignature().split(" ");
									String methodAlgorithmCall = methodAlgorithmArray[methodAlgorithmArray.length - 1];
									str.append(methodAlgorithmCall.substring(0, methodAlgorithmCall.length() - 1) + "\n");
								}
							}
							
						}
						
						recursiveMethodGenerator(nextMethod, str, depth - 1, method.getDeclaringClass(), false);
					}
				} else if (stmt instanceof InvokeStmt) {
					SootMethod nextMethod = ((InvokeStmt) stmt).getInvokeExpr().getMethod();
					possibleMethods = this.mra.resolve(this.scene, stmt, nextMethod);
					if(!possibleMethods.isEmpty()) {
						SootMethod fallback = nextMethod;
						nextMethod = possibleMethods.iterator().next();
						possibleMethods.add(fallback);
						for(SootMethod m : possibleMethods) {
							if(!m.equals(nextMethod) && this.useFiltersOn(m)) {
								str.append("'" + method.getDeclaringClass().toString() + " -> " + m.getDeclaringClass().toString() + ": ");
								String[] methodAlgorithmArray = nextMethod.getSignature().split(" ");
								String methodAlgorithmCall = methodAlgorithmArray[methodAlgorithmArray.length - 1];
								str.append(methodAlgorithmCall.substring(0, methodAlgorithmCall.length() - 1) + "\n");
							}
						}
					}
					recursiveMethodGenerator(nextMethod, str, depth - 1, method.getDeclaringClass(), false);
				}
			});
		}

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
