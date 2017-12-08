package csse374.revengd.application;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import csse374.revengd.soot.MainMethodMatcher;
import csse374.revengd.soot.SceneBuilder;
import soot.Scene;
import soot.SootClass;


public class RegularAnalyzable implements Analyzable {

	@Override
	public Set<SootClass> analyze(String path, List<String> classNames) {
		Scene scene = SceneBuilder.create()
				.addClassPath(path)
				.addClasses(classNames)
				.setEntryClass(classNames.get(0)) //Should figure out a more flexible way to find main.
				.addEntryPointMatcher(new MainMethodMatcher(classNames.get(0))) //Will need to be updated.
				.build();
		
		Set<SootClass> sootClasses = new HashSet<>();
		System.out.println("----Loaded----");
		classNames.forEach(name -> {
			SootClass clazz = scene.getSootClass(name);
			sootClasses.add(clazz);
			System.out.println(clazz.getName());
		});
		System.out.println("-----------------------------");
		
		return sootClasses;
	}

}
