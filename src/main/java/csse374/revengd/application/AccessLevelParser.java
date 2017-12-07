package csse374.revengd.application;

import java.util.Map;

public class AccessLevelParser implements Argable{
	private ClassFilter cF;
	private Map<String, Filterable> argToFilterable;
	public AccessLevelParser(ClassFilter classFilter, Map<String, Filterable> argToFilterableMap) {
		this.cF = classFilter;
		this.argToFilterable = argToFilterableMap;
	}

	@Override
	public void parseArgs(String[] args) {
		// TODO Auto-generated method stub
		boolean accessLevelFound = false;
		for(int i = 0; i < args.length; i++){
			if(accessLevelFound){
				if(this.argToFilterable.containsKey(args[i])){
					Filterable filterable = this.argToFilterable.get(args[i]);
					this.cF.addFilterable(filterable);
				}
				else{
					System.out.format("%s is not a valid access level arg\n", args[i]);
					//program should exit somehow
				}
				break;
			}
			
			if(args[i].equals("--access")){
				accessLevelFound = true;
			}
		}
		
	}

}
