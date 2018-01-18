package csse374.revengd.application;

import java.util.HashMap;
import java.util.Map;

public class CLParser {
	
	public Map<String, String> parseAll(String[] args){
		if (args.length==0) {
			return new HashMap<>();
		}
		Map<String, String> argMap = new HashMap<>();
		String flag = args[0];
		StringBuilder params = new StringBuilder();
		for (int i = 1; i < args.length; i++) {
			if (args[i].startsWith("-")) {
				argMap.put(flag.replace("-", ""), params.toString().trim());
				flag = args[i];
				params = new StringBuilder();
			} else {
				params.append(args[i] + " ");
			}
		}
		argMap.put(flag, params.toString().trim());
		return argMap;
	}
	
}
