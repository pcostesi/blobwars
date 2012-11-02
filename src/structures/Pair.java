package structures;

/**
 * An useful class to represents a pair of things 
 *
 * @param <F> the generic type
 * @param <S> the generic type
 */
public class Pair<F, S>{
	
	F first;
	S second;
	
	/**
	 * Instantiates a new pair.
	 *
	 * @param first the first
	 * @param second the second
	 */
	public Pair(F first, S second){
		this.first = first;
		this.second = second;
	}
	
	/**
	 * Gets the first element.
	 *
	 * @return the first
	 */
	public F getFirst(){
		return first;
	}
	
	/**
	 * Gets the second element
	 *
	 * @return the second
	 */
	public S getSecond(){
		return second;
	}
	
	/**
	 * Sets the first element
	 *
	 * @param first the new first
	 */
	public void setFirst(F first){
		this.first = first;
	}
	
	/**
	 * Sets the second element
	 *
	 * @param second the new second element
	 */
	public void setSecond(S second){
		this.second = second;
	}
}
