package verifier;

/**
 * <b>Lemma</b><br>
 * A Lemma is an enclosure of a Proof 
 * that is used as an Inference rule
 * @author Ethan Gertler
 *
 */
public interface Lemma extends Inference  {
	/**
	 * <b>getProof</b><br>
	 * Returns the Proof that is used to support this Lemma
	 * @return The Proof that this Lemma references
	 */
	public Proof getProof();
}
