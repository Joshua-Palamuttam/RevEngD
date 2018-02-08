package csse374.revengd.application;

import java.util.HashSet;
import java.util.Set;

import soot.SootClass;

public class AdapterModifier extends AbstractUMLModifier {
	private final static String ADAPTER_STEREOTYPE = "<<Adapter>>";
	private final static String ADAPTEE_STEREOTYPE = "<<Adaptee>>";
	private final static String TARGET_STEREOTYPE = "<<Target>>";
	private final static String HAS_STEREOTYPE = "<<adapts>>";
	private final static String COLOR = "Fuchsia";
	
	@Override
	public String getHeaderMod() {
		StringBuilder header = new StringBuilder();
		header.append("skinparam class {\n");
		String[] stereotypes = {ADAPTER_STEREOTYPE, ADAPTEE_STEREOTYPE, TARGET_STEREOTYPE};
		for  (String s : stereotypes) {
			header.append("BackgroundColor"+s+" "+ COLOR + "\n");
		}
		header.append("}\n");
		return header.toString();
	}

	@Override
	public String getClassMod(String original, SootClass clazz) {
		Set<IPattern> patterns = this.data.getPatternsByName(AdapterDetector.PATTERN);

		StringBuilder mod = new StringBuilder();
		mod.append(original);
		
		//boolean already
		for(IPattern p : patterns) {
			if(p.getComponents(AdapterDetector.ADAPTEE).contains(clazz)) {
				mod.append(" " + ADAPTEE_STEREOTYPE);
			} else if (p.getComponents(AdapterDetector.ADAPTER).contains(clazz)) {
				mod.append(" " + ADAPTER_STEREOTYPE);
			} else if (p.getComponents(AdapterDetector.TARGET).contains(clazz)) {
				mod.append(" " + TARGET_STEREOTYPE);
			}
		}
		return mod.toString();
	}

	@Override
	public String getHasMod(String original, SootClass startClazz, SootClass endClazz) {
		Set<IPattern> patterns = this.data.getPatternsByName(AdapterDetector.PATTERN);
		
		for (IPattern pattern : patterns) {
			if ((pattern.getComponents(AdapterDetector.ADAPTER).contains(startClazz))
					&& pattern.getComponents(AdapterDetector.ADAPTEE).contains(endClazz)) {
				return original + ":" + HAS_STEREOTYPE;
			}
		}
		return original;
	}

}
