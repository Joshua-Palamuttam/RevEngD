package csse374.revengd.application;

import soot.SootClass;
import soot.SootField;
import soot.SootMethod;

public interface Ifilter {
 public boolean filterClass(SootClass sootClass);
 public boolean filterField(SootField sootField);
 public boolean filterMethod(SootMethod sootMethod);
}
