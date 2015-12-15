/**
 * 
 */
package verifier;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <b>Sentence</b><br>
 * A  finite sequence of symbols from a given alphabet that is part of a formal language
 * @author Ethan Gertler
 *
 */
public interface Sentence extends Cloneable, Serializable  {
	public List<Sentence> parts();
	public Operator getOperator();
	public boolean equals(Object o);
	public Map<Variable, Sentence> mapInto(Sentence sen);
	public boolean canMapInto(Sentence sen);
	public Sentence clone();
	
}
