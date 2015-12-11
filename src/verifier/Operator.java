package verifier;

/**
 * A logical operator that takes one or more arguments and returns a boolean value
 * @author edgchess
 *
 */
public interface Operator extends Cloneable {
	/**
	 *  <b>equals</b><br>
	 *  Two Operators are equal iff they have the same output for every input combination
	 * @param o Another Object
	 * @return True iff the objects are equlivant
	 */
	public boolean equals(Object o);

	public Operator clone();
}
