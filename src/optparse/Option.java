package optparse;

public interface Option {

	boolean parse(String[] args);
	public String value();
	public String[] values();
	public boolean validate(Validator validator);
	public Option copy();
	public String name();
}
