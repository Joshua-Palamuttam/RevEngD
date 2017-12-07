package csse374.revengd.application;

import java.util.ArrayList;
import java.util.List;

import csse374.revengd.soot.MainMethodMatcher;
import csse374.revengd.soot.SceneBuilder;
import soot.Scene;
import soot.SootClass;


public class RegularAnalyzable implements Analyzable {

	@Override
	public List<SootClass> analyze(String path, List<String> classNames) {
		Scene scene = SceneBuilder.create()
				.addDirectory(path)
				.setEntryClass(classNames.get(0)) //Should figure out a more flexible way to find main.
				.addEntryPointMatcher(new MainMethodMatcher(classNames.get(0))) //Will need to be updated.
				.build();
		
		List<SootClass> sootClasses = new ArrayList<>();
				
		scene.getApplicationClasses().forEach(clazz -> {
			if (classNames.contains(clazz.getName())){
				sootClasses.add(clazz);
			}
		});
		
		return sootClasses;
	}

}
