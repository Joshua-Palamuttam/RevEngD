package csse374.revengd.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import csse374.revengd.soot.MainMethodMatcher;
import csse374.revengd.soot.SceneBuilder;
import soot.Scene;
import soot.SootClass;

public class DiscoveredAnalyzable implements Analyzable {

	@Override
	public Set<SootClass> analyze(String path, List<String> classNames) {
		Scene scene = SceneBuilder.create()
				.addClassPath(path)
				.addClasses(classNames)
				.setEntryClass(classNames.get(0)) //Should figure out a more flexible way to find main.
				.addEntryPointMatcher(new MainMethodMatcher(classNames.get(0))) //Will need to be updated.
				.build();
		
		Set<SootClass> sootClasses = new HashSet<>();
		
		System.out.println("----Recur Loaded----");
		classNames.forEach(name -> {
			SootClass clazz = scene.getSootClass(name);
			sootClasses.add(clazz);
			computeAllSuperTypes(clazz, sootClasses);

		});
		
		sootClasses.forEach(clazz -> {
			System.out.println(clazz.getName());
		});
		
		System.out.println("-----------------------------");
		
		
		return sootClasses;
	}
	
	void computeAllSuperTypes(final SootClass clazz, final Collection<SootClass> allSuperTypes) {
		if(clazz.getName().equals("java.lang.Object"))
			return;
		
		Collection<SootClass> directSuperTypes = new ArrayList<SootClass>();

		SootClass superClazz = clazz.getSuperclass();
		if(superClazz != null)
			directSuperTypes.add(superClazz);
		
		if(clazz.getInterfaceCount() > 0)
			directSuperTypes.addAll(clazz.getInterfaces());

		directSuperTypes.forEach(aType -> {
			if(!allSuperTypes.contains(aType)) {
				allSuperTypes.add(aType);
				this.computeAllSuperTypes(aType, allSuperTypes);					
			}
		});
	}


}
