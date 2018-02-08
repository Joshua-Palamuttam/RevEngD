package csse374.revengd.application;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import soot.Scene;
import soot.SootClass;

public class RecursiveLoader extends Analyzable {

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		Scene scene = data.getScene();
		Set<SootClass> sootClasses = data.getSootClasses();
		
		scene.getApplicationClasses().forEach(clazz -> {
			if (this.useFiltersOn(clazz)){
				sootClasses.add(clazz);				
			}
		});
		
		Set<SootClass> temp = new HashSet<>();
		temp.addAll(sootClasses);
		
		temp.forEach(t -> {
			if (this.useFiltersOn(t)){
				computeAllSuperTypes(t,sootClasses);
			}
		});
		System.out.println("-------------After recursive---------");
		sootClasses.forEach(clazz ->{
			System.out.println(clazz.getName());
		});
		System.out.println("-------------------------------------");
	}

	private void computeAllSuperTypes(final SootClass clazz, final Collection<SootClass> allSuperTypes) {
		if (clazz.getName().equals("java.lang.Object"))
			return;

		Collection<SootClass> directSuperTypes = new ArrayList<>();

		SootClass superClazz = clazz.getSuperclass();
		if (superClazz != null && this.useFiltersOn(superClazz))
			directSuperTypes.add(superClazz);

		if (clazz.getInterfaceCount() > 0)
			directSuperTypes.addAll(clazz.getInterfaces());

		directSuperTypes.forEach(aType -> {
			if (!allSuperTypes.contains(aType) && this.useFiltersOn(aType)) {
				allSuperTypes.add(aType);
				this.computeAllSuperTypes(aType, allSuperTypes);
			}
		});
	}

}
