package optparse;

import java.util.Arrays;

public class ValuedOption implements Option {

	private String option;
	private int arity;
	private String[] res;

	public ValuedOption(String option, int arity) {
		this.option = option;
		this.arity = arity;
	}
	
	public ValuedOption(String option) {
		this(option, 1);
	}

	public boolean parse(String[] args) {
		String flag = "-" + option;
		int idx = 0;
		for (; idx < args.length; idx++){
			if (flag.equals(args[idx])){
				break;
			}
		}
		if (args.length >= idx + 1 + arity){
			this.res = Arrays.copyOfRange(args, idx + 1, idx + 1 + arity);
			return true;
		}
		this.res = null;
		return false;
	}

	public String[] values() {
		return res;
	}

	public String value() {
		StringBuilder sb = new StringBuilder();
		if (res == null){
			return null;
		}
		for (int i = 0; i < res.length; i++) {
			sb.append(res[i]);
			if (i < res.length - 1) {
				sb.append(' ');
			}
		}
		return sb.toString();
	}

	public boolean validate(Validator validator) {
		return validator.validate(this.values());
	}

	public Option copy() {
		// TODO Auto-generated method stub
		return new ValuedOption(option, arity);
	}

	public String name() {
		return option;
	}



}
