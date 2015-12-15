/**
 * 
 */
package verifier;

import java.io.Serializable;

/**
 * <b>Inference</b><br>
 * A logical form consisting of a function which takes premises, analyzes their syntax, and returns a conclusion
 * @author Ethan Gertler
 *
 */
public interface Inference extends Cloneable, Serializable{
	/**
	 * <b>name</b><br>
	 * Gets the name off the Inference
	 * @return The name of this Inference
	 */
	public String name();
	
	/**
	 * <b>isValid</b><br>
	 * Verifies that this Inference is valid
	 * <p>This implies that using this Inference
	 * and the Reference,
	 * you can deductively conclude the Sentence
	 * @param sen The concluding Sentence
	 * @param ref A Reference to the supporting Lines
	 * @return True iff the supporting Lines deductively
	 * lead to the concluding Sentence using this Inference
	 */
	public boolean isValid(Sentence sen, Reference ref);
	
	/**
	 * <b>clone</b><br>
	 * Creates a copy of this Inference
	 * @return A copy of this Inference
	 */
	public Inference clone();
}
