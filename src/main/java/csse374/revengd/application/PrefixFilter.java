package csse374.revengd.application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public class PrefixFilter implements IFilter {
	private Set<String> excludePrefixes;
	private Set<String> includeNames;
	private Set<String> includePrefixes;
	
	public PrefixFilter(Map<String, String> argMap) {
		this.excludePrefixes = new HashSet<>();
		this.includeNames = new HashSet<>();
		this.includePrefixes = new HashSet<>();
		String prefixes = argMap.get("exclude");
		if (null != prefixes) {
			this.excludePrefixes.addAll(Arrays.asList(prefixes.split(" ")));
		}
		
		prefixes = argMap.get("include");
		if (null != prefixes) {
			this.includePrefixes.addAll(Arrays.asList(prefixes.split(" ")));
		}
		
		String names = argMap.get("class");
		if (null != names) {
			this.includeNames.addAll(Arrays.asList(names.split(" ")));
		}
	}
	
	@Override
	public boolean filterClass(SootClass sootClass) {
		if (this.includeNames.contains(sootClass.getName())) {
			return true;
		}
		for (String prefix : this.includePrefixes) {
			if (sootClass.getName().startsWith(prefix)) {
				return true;
			}
		}
		for (String prefix : this.excludePrefixes) {
			if (sootClass.getName().startsWith(prefix)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean filterField(SootField sootField) {
		if (this.includeNames.contains(sootField.getDeclaringClass().getName())) {
			return true;
		}
		for (String prefix : this.includePrefixes) {
			if (sootField.getDeclaringClass().getName().startsWith(prefix)) {
				return true;
			}
		}
		for (String prefix : this.excludePrefixes) {
			if (sootField.getDeclaringClass().getName().startsWith(prefix)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean filterMethod(SootMethod sootMethod) {
		if (this.includeNames.contains(sootMethod.getDeclaringClass().getName())) {
			return true;
		}
		for (String prefix : this.includePrefixes) {
			if (sootMethod.getDeclaringClass().getName().startsWith(prefix)) {
				return true;
			}
		}
		for (String prefix : this.excludePrefixes) {
			if (sootMethod.getDeclaringClass().getName().startsWith(prefix)) {
				return false;
			}
		}
		return true;
	}

}
