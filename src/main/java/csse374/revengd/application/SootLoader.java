package csse374.revengd.application;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import csse374.revengd.soot.MainMethodMatcher;
import csse374.revengd.soot.SceneBuilder;
import soot.Scene;
import soot.SootClass;

public class SootLoader extends Analyzable {

	@Override
	public void analyze(AnalyzableData data, OutputStream out) {
		Map<String, String> configMap  = data.getConfigMap();
		String path = configMap.get("--path");
		String classNamesString = configMap.get("--class");
		String [] classNames = classNamesString.trim().split(" ");
		
		Scene scene = SceneBuilder.create()
				.addClassPath(path)
				.addClasses(Arrays.asList(classNames))
				.setEntryClass(classNames[0]) //Should figure out a more flexible way to find main.
				.addEntryPointMatcher(new MainMethodMatcher(classNames[0])) //Will need to be updated.
				.build();
		
		Set<SootClass> sootClasses = new HashSet<>();
		System.out.println("----Loaded----");
		for(int i = 0; i < classNames.length; i++) {
			SootClass clazz = scene.getSootClass(classNames[i]);
			boolean keep = this.useFiltersOn(clazz);
			
			if(keep) {
				sootClasses.add(clazz);
				System.out.println(clazz.getName());
			}
		}
		System.out.println("-----------------------------");
		data.setSootClasses(sootClasses);
		data.setScene(scene);

	}

}
