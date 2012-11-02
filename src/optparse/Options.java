package optparse;

import java.util.Map;

public class Options {

	private Map<String, String> values;
	private Map<String, String[]> values2;
	
	Options(Map<String, String> values, Map<String, String[]> values2){
		this.values = values;
		this.values2 = values2;
	}
	
	public String getValue(String name){
		return values.get(name);
	}
	
	public int getIntValue(String name){
		return Integer.parseInt(values.get(name));
	}
	
	public boolean hasFlag(String name){
		return values.get(name) != null;
	}
	
	public String[] getValues(String name){
		return values2.get(name);
	}
}
