package csse374.revengd.application;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import soot.SootClass;
import soot.SootField;

public class Relationship {
	private SootClass thisClass;
	private Map<SootClass, Boolean> has;
	private Map<SootClass, Boolean> uses;
	private Set<SootClass> implementz;
	private SootClass extendz;
	
	public Relationship(SootClass clazz) {
		this.thisClass = clazz;
	}
	
	public void filterIn(Set<SootClass> keep) {
		
		Predicate<SootClass> notInKeep = new Predicate<SootClass>() {
			@Override
			public boolean test(SootClass clazz) {
				return (!keep.contains(clazz));
			}
		};
		
		Predicate<SootClass> inKeep = new Predicate<SootClass>() {
			@Override
			public boolean test(SootClass clazz) {
				return (keep.contains(clazz));
			}
		};
		
		if (this.has != null) {
			Set<SootClass> keys = this.has.keySet();
			keys.removeIf(notInKeep);
		}
		
		if (this.uses != null) {
			Set<SootClass> keys = this.uses.keySet();
			keys.removeIf(notInKeep);
		}
		
		if (this.implementz != null){
			this.implementz.removeIf(notInKeep);
		}
		
		if (this.extendz != null && !keep.contains(this.extendz)) {
			this.extendz = null;
		}
	}
	

	public SootClass getThisClass() {
		return this.thisClass;
	}

	public void setThisClass(SootClass thisClass) {
		this.thisClass = thisClass;
	}

	public Map<SootClass, Boolean> getHas() {
		return this.has;
	}

	public void setHas(Map<SootClass, Boolean> has) {
		this.has = has;
	}
	
	public void addHas(SootClass clazz, boolean many) {
		if (this.has == null) {
			return;
		}
		
		if (this.thisClass.equals(clazz)) {
			return;
		}
		
		if (this.has.containsKey(clazz)) {
			many = this.has.get(clazz) || many;
		}
		this.has.put(clazz, many);
	}

	public Map<SootClass, Boolean> getUses() {
		return this.uses;
	}

	public void setUses(Map<SootClass, Boolean> uses) {
		this.uses = uses;
	}
	
	public void addUses(SootClass clazz, boolean many) {
		if (this.uses == null) {
			return;
		}
		
		if (this.extendz.equals(clazz) || this.implementz.contains(clazz) || this.has.containsKey(clazz) || this.thisClass.equals(clazz)) {
			return;
		}
		
		if (this.uses.containsKey(clazz)) {
			many = this.uses.get(clazz) || many;
		}
		this.uses.put(clazz, many);
	}

	public Set<SootClass> getImplementz() {
		return this.implementz;
	}

	public void setImplementz(Set<SootClass> implementz) {
		this.implementz = implementz;
	}

	public SootClass getExtendz() {
		return this.extendz;
	}

	public void setExtendz(SootClass extendz) {
		this.extendz = extendz;
	}
	

}
