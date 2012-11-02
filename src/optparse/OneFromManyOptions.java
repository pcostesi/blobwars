package optparse;

import java.util.LinkedList;
import java.util.List;

public class OneFromManyOptions implements Option {

	private List<Option> options;
	private Option selected;
	
	public OneFromManyOptions(Option... options){
		this.options = new LinkedList<Option>();
		for (Option option : options){
			this.options.add(option);
		}
	}
	
	public boolean parse(String[] args) {
		for (Option option : options){
			Option parser = option.copy();
			if (parser.parse(args)){
				selected = parser;
				return true;
			}
		}
		return false;
	}

	public String value() {
		return selected.value();
	}

	public String[] values() {
		return selected.values();
	}

	public boolean validate(Validator validator) {
		return selected.validate(validator);
	}

	public Option copy() {
		return new OneFromManyOptions(options.toArray(new Option[0]));
	}

	public String name() {
		if (selected != null){
			return selected.name();
		}
		StringBuilder name = new StringBuilder();
		name.append(" ");
		for (Option option : options){
			name.append('-');
			name.append(option.name());
		}
		return name.toString();
	}

}
