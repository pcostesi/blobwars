package optparse;

public class FlagOption implements Option {
	
	private String flag;
	private boolean exists = false;
	
	public FlagOption(String flag){
		this.flag = flag;
	}

	public boolean parse(String[] args) {
		String dashflag = "-" + flag;
		for (String s : args){
			if (dashflag.equals(s)){
				exists = true;
				return true;
			}
		}
		return false;
	}

	public String value() {
		if (exists){
			return flag;
		}
		return null;
	}

	public String[] values() {
		if (exists){
			return new String[]{flag};
		}
		return null;
	}

	public boolean validate(Validator validator) {
		return validator.validate(this.values());
	}

	public Option copy() {
		return new FlagOption(flag);
	}

	public String name() {
		return flag;
	}

	
	
}
