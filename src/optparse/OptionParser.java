package optparse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OptionParser {

	private Map<String, Option> options;
	private List<Option> required;
	
	public OptionParser(Option... options){
		this.options = new HashMap<String, Option>();
		this.required = new LinkedList<Option>();
		for (Option option : options){
			addOption(option);
		}
	}
	
	public OptionParser addOption(String name, Option option){
		this.options.put(name, option);
		return this;
	}
	
	public OptionParser addOption(Option option){
		return addOption(option.name(), option);
	}
	
	public OptionParser addOption(String name, Option option, boolean required){
		this.required.add(option);
		return addOption(name, option);
	}
	
	public OptionParser addOption(Option option, boolean required){
		return addOption(option.name(), option, required);
	}
	
	public Options parse(String[] args){
		Map<String, String> values = new HashMap<String, String>();
		Map<String, String[]> values2 = new HashMap<String, String[]>();
		for (Entry<String, Option> es : options.entrySet()){
			Option option = es.getValue();
			Option parser = option.copy();
			String name = es.getKey();
			boolean parsed = parser.parse(args);
			if (required.contains(option)){
				required.remove(option);
				if (!parsed){
					throw new RuntimeException("Couldn't parse " + name);
				}
			}
			if (name.startsWith(" ")){
				name = parser.name();
			}
			values.put(name, parser.value());
			values2.put(name, parser.values());
		}
		return new Options(values, values2);
	}
}
