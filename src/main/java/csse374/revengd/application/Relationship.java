package csse374.revengd.application;

import java.util.List;
import java.util.Set;

import soot.SootClass;

public class Relationship {
	private SootClass thisClass;
	private Set<SootClass> has;
	private Set<SootClass> uses;
	private Set<SootClass> implementz;
	private SootClass extendz;
	
	public Relationship(SootClass clazz) {
		this.thisClass = clazz;
	}
	
	public void filterIn(List<SootClass> keep) {
		if (this.has != null) {
			this.has.forEach(clazz -> {
				if (!keep.contains(clazz)) {
					this.has.remove(clazz);
				}
			});
		}
		
		if (this.uses != null) {
			this.uses.forEach(clazz -> {
				if (!keep.contains(clazz)) {
					this.uses.remove(clazz);
				}
			});
		}
		
		if (this.implementz != null){
			this.implementz.forEach(clazz -> {
				if (!keep.contains(clazz)) {
					this.implementz.remove(clazz);
				}
			});
		}
		
		if (this.extendz != null && !keep.contains(this.extendz)) {
			this.extendz = null;
		}
	}

	public SootClass getThisClass() {
		return thisClass;
	}

	public void setThisClass(SootClass thisClass) {
		this.thisClass = thisClass;
	}

	public Set<SootClass> getHas() {
		return has;
	}

	public void setHas(Set<SootClass> has) {
		this.has = has;
	}

	public Set<SootClass> getUses() {
		return uses;
	}

	public void setUses(Set<SootClass> uses) {
		this.uses = uses;
	}

	public Set<SootClass> getImplementz() {
		return implementz;
	}

	public void setImplementz(Set<SootClass> implementz) {
		this.implementz = implementz;
	}

	public SootClass getExtendz() {
		return extendz;
	}

	public void setExtendz(SootClass extendz) {
		this.extendz = extendz;
	}
	

}
