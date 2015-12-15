package verifier;

import java.io.Serializable;

/**
 * <b>Line</b><br>
 * A container class that holds all the
 * components for a line in a proof
 * @author Ethan Gertler
 *
 */
public class Line implements Cloneable, Serializable {
	private static final long serialVersionUID = -2610182201610903505L;
	/**
	 * <b>s</b><br>
	 * This Line's Sentence
	 * <p>May be null
	 */
	public Sentence s;
	
	/**
	 * <b>i</b><br>
	 * This Line's Inference
	 * <p>May be null
	 */
	public Inference i;
	
	/**
	 * <b>r</b><br>
	 * This Line's Reference
	 * <p>May be null
	 */
	public Reference r;
	
	/**
	 * <b>number</b><br>
	 * This Line's sequence in the Proof
	 * <p>May not be current
	 */
	public int number;
	
	/**
	 * <b>Line</b><br>
	 * Creates an empty Line
	 */
	public Line() {}
	
	/**
	 * <b>Line</b><br>
	 * Creates a Line given its parts
	 * @param s The Sentence to use
	 * @param i The Inference to use
	 * @param r The Reference to use
	 */
	public Line(Sentence s, Inference i, Reference r) {
		this.s = s;
		this.i = i;
		this.r = r;
	}
	
	/**
	 * <b>clone</b><br>
	 * Returns a deep copy of this Line
	 * @return A deep copy of this Line
	 */
	@Override
	public Line clone() {
		Line copy = new Line();
		if(s != null) copy.s = this.s.clone();
		if(i != null) copy.i = this.i.clone();
		if(r != null) copy.r = this.r.clone();
		copy.number = this.number;
		return copy;
	}
	
	@Override
	public int hashCode() {
		return (s!=null?s.hashCode()^2:0) + (i!=null?i.hashCode()^2:0) + (r!=null?r.hashCode()^2:0) % Integer.MAX_VALUE;
	}
	
	@Override
	public String toString() {
		return number + "\t" + s + "\t" + (i!=null?i.name():"") + "\t" + (i!=null&&r!=null?r:"");
	}
}
