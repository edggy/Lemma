package verifier;

/**
 * <b>Variable</b><br>
 * The simplest valid Sentence containing just 
 * one proposition with no Operator
 * @author Ethan Gertler
 *
 */
public interface Variable extends Sentence {
	/**
	 * <b>name</b><br>
	 * @return A String representing the name of this Variable
	 */
	public String name();
}
