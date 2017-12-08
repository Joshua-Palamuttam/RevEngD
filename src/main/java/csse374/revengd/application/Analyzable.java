package csse374.revengd.application;
import java.util.List;
import java.util.Set;

import soot.SootClass;

public interface Analyzable {

	public Set<SootClass> analyze(String path, List<String> classNames);

}
