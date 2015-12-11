/**
 * 
 */
package verifier;

import java.io.Serializable;

/**
 * <b>Inference</b><br>
 * A logical form consisting of a function which takes premises, analyzes their syntax, and returns a conclusion
 * @author edgchess
 *
 */
public interface Inference extends Cloneable, Serializable{
	public String name();
	public boolean isValid(Sentence sen, Reference ref);
	public Inference clone();
}
