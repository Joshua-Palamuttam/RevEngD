package csse374.revengd.application;
import java.util.List;

import soot.SootClass;

public interface Analyzable {

	public List<SootClass> analyze(String path, List<String> classNames);

}
