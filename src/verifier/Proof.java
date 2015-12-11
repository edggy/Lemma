package verifier;

import java.io.Serializable;
import java.util.List;

/**
 * <b>Proof</b><br>
 * A finite sequence of sentences (called well-formed formulas in the case of a formal language) each of which is an axiom, an assumption, or follows from the preceding sentences in the sequence by a rule of inference
 * @author Ethan Gertler
 *
 */
public interface Proof extends Serializable {
	public static final Inference ASSUMPTION = new verifier.impl.Inference("Assumption", null, null);
	
	public int length();
	
	public int size();
	
	public void addLine();
	
	public void addLines(int amount);
	
	public Line getLine(int line_num);
	
	public List<Line> getLines();
	
	public List<Line> getLines(int line_num, int amount);
	
	public void insertLine(int line_num);
	
	public void insertLines(int line_num, int amount);
	
	public void removeLine(int line_num);
	
	public void removeLines(int line_num, int amount);
	
	public Line getLastLine();
	/**
	 * <b>addSentence</b><br>
	 * Adds or replaces a Sentence at a given line
	 * @param sen A Sentence to be added
	 * @param line_num The number of the line to add the Sentence to
	 */
	public void addSentence(int line_num, Sentence sen);
	
	/**
	 * <b>addSentences</b><br>
	 * Adds or replaces a List of Sentences starting from the line given
	 * @param sens A List of Sentences to be added
	 * @param line_num The number of the line to start adding Sentences to
	 */
	public void addSentences(int line_num, List<Sentence> sens);
	
	/**
	 * <b>getSentence</b><br>
	 * Returns the Sentence at a given line
	 * @param line_num The line number of the Sentence to return
	 */
	public Sentence getSentence(int line_num);
	public List<Sentence> getSentences(int line_num, int amount);
	
	public void removeSentence(int line_num);
	public void removeSentences(int line_num, int amount);
	
	/**
	 * <b>addInference</b><br>
	 * Adds or replaces an Inference to support the line
	 * @param line_num The line number to support
	 * @param inf The Inference that is used to validate this line
	 */
	public void addInference(int line_num, verifier.Inference inf);
	
	public verifier.Inference getInference(int line_num);
	
	public void removeInference(int line_num);
	
	/**
	 * <b>addReference</b><br>
	 * Adds or replaces a Reference to support the Inference
	 * @param line_num The line number to support
	 * @param ref A Reference to the supported lines
	 */
	public void addReference(int line_num, Reference ref);
	
	public void addReference(int line_num, int ref_line);
	
	public Reference getReference(int line_num);
	
	public void removeReference(int line_num, int ref_line);
	
	public void removeReferences(int line_num);
	
	public int isValid();
}
