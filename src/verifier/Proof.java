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
	
	public static class AssumptionInference implements Inference {
		private static final long serialVersionUID = 6579731403445321639L;
		public AssumptionInference() {}
		@Override public String name() {return "Assumption";}
		@Override public boolean isValid(Sentence sen, Reference ref) {return true;}
		@Override public Inference clone() {return this;}
	}
	/**
	 * <b>ASSUMPTION</b><br>
	 * An Inference to be used to assume new Sentences 
	 * as premises in a Proof
	 */
	public static final Inference ASSUMPTION = new AssumptionInference();
	
	/**
	 * <b>length</b><br>
	 * Gets the size of the current Proof
	 * @return The number of lines in the proof
	 */
	public int length();
	
	/**
	 * <b>size</b><br>
	 * Gets the size of the current Proof
	 * @return The number of lines in the proof
	 */
	public int size();
	
	/**
	 * <b>addLine</b><br>
	 * Adds an empty Line to the end of the Proof
	 */
	public void addLine();
	
	/**
	 * <b>addLines</b><br>
	 * Adds some number of empty Lines to the end of the Proof
	 * @param amount The number of empty Lines to add
	 */
	public void addLines(int amount);
	
	/**
	 * <b>getLine</b><br>
	 * Gets a Line from the Proof
	 * @param line_num The index of the line to get
	 * @return The line of the Proof
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public Line getLine(int line_num) throws IndexOutOfBoundsException;
	
	/**
	 * <b>getLines</b><br>
	 * Returns a List of all the Lines in the Proof
	 * @return A List of all the Lines
	 */
	public List<Line> getLines();
	
	/**
	 * <b>getLines</b><br>
	 * Returns a List of some number of the Lines 
	 * in the Proof starting at a particular index
	 * @param line_num The Line number of the first Line to return
	 * @param amount The number of Lines to return
	 * @return The amount of lines starting at line_num
	 * @throws IndexOutOfBoundsException if the line_num or line_num + amount is out of range (index < 0 || index >= size())
	 */
	public List<Line> getLines(int line_num, int amount) throws IndexOutOfBoundsException;
	
	/**
	 * <b>insertLine</b><br>
	 * Inserts an empty Line at the given index
	 * @param line_num The line number to insert a new Line at
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public void insertLine(int line_num) throws IndexOutOfBoundsException;
	
	/**
	 * <b>insertLines</b><br>
	 * Inserts some number of empty Line at the given index
	 * @param line_num The line number to insert the first new Line at
	 * @param amount The number of Lines to insert
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public void insertLines(int line_num, int amount)throws IndexOutOfBoundsException;
	
	/**
	 * <b>removeLine</b><br>
	 * Deletes a Line from the Proof
	 * @param line_num The Line number to delete
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public void removeLine(int line_num) throws IndexOutOfBoundsException;
	
	/**
	 * <b>removeLines</b><br>
	 * Deletes some number of Lines from the Proof
	 * @param line_num The first Line number to delete
	 * @param amount The number of Lines to delete
	 * @throws IndexOutOfBoundsException if the line_num or line_num + amount is out of range (index < 0 || index >= size())
	 */
	public void removeLines(int line_num, int amount) throws IndexOutOfBoundsException;
	
	/**
	 * <b>getLastLine</b><br>
	 * Gets the last Line of the Proof
	 * @return The last Line
	 */
	public Line getLastLine();
	
	/**
	 * <b>addSentence</b><br>
	 * Adds or replaces a Sentence at a given line
	 * @param sen A Sentence to be added
	 * @param line_num The number of the line to add the Sentence to
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public void addSentence(int line_num, Sentence sen) throws IndexOutOfBoundsException;
	
	/**
	 * <b>addSentences</b><br>
	 * Adds or replaces a List of Sentences starting from the line given
	 * @param sens A List of Sentences to be added
	 * @param line_num The number of the line to start adding Sentences to
	 * @throws IndexOutOfBoundsException if the line_num or line_num + sens.length() is out of range (index < 0 || index >= size())
	 */
	public void addSentences(int line_num, List<Sentence> sens) throws IndexOutOfBoundsException;
	
	/**
	 * <b>getSentence</b><br>
	 * Returns the Sentence at a given line
	 * @param line_num The line number of the Sentence to return
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public Sentence getSentence(int line_num) throws IndexOutOfBoundsException;
	
	/**
	 * <b>getSentences</b><br>
	 * Gets some amount of Sentences starting at line_num
	 * @param line_num The first Line to get
	 * @param amount The amount of Lines to get
	 * @return A List of some amount of Sentences starting with the one at line_num
	 * @throws IndexOutOfBoundsException if the line_num or line_num + amount is out of range (index < 0 || index >= size())
	 */
	public List<Sentence> getSentences(int line_num, int amount) throws IndexOutOfBoundsException;
	
	/**
	 * <b>removeSentence</b><br>
	 * Removes the Sentence from the Line
	 * @param line_num The number of the Line to remove the Sentence from
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public void removeSentence(int line_num) throws IndexOutOfBoundsException;
	
	/**
	 * <b>removeSentences</b><br>
	 * Removes some amount of Sentences from the Lines starting at line_num
	 * @param line_num The Line number of the first Sentence to remove
	 * @param amount The amount of lines to remove
	 * @throws IndexOutOfBoundsException if the line_num or line_num + amount is out of range (index < 0 || index >= size())
	 */
	public void removeSentences(int line_num, int amount) throws IndexOutOfBoundsException;
	
	/**
	 * <b>addInference</b><br>
	 * Adds or replaces an Inference to support the line
	 * @param line_num The line number to support
	 * @param inf The Inference that is used to validate this line
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public void addInference(int line_num, verifier.Inference inf) throws IndexOutOfBoundsException;
	
	/**
	 * <b>getInference</b><br>
	 * Gets the Inference used at the specified Line number
	 * @param line_num The Line number to get the Inference of
	 * @return The inference used at line_num
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public verifier.Inference getInference(int line_num) throws IndexOutOfBoundsException;
	
	/**
	 * <b>removeInference</b><br>
	 * Removes the Inference at the given Line
	 * @param line_num The Line number to remove the Inference at
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public void removeInference(int line_num) throws IndexOutOfBoundsException;
	
	/**
	 * <b>addReference</b><br>
	 * Adds a Reference to support the Inference
	 * @param line_num The line number to support
	 * @param ref A Reference to the supported lines
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public void addReference(int line_num, Reference ref) throws IndexOutOfBoundsException;
	
	/**
	 * <b>addReference</b><br>
	 * Adds a line to the Reference to support the Inference
	 * @param line_num The Line number to add support to
	 * @param ref_line The support Line number to add
	 * @throws IndexOutOfBoundsException if the line_num or ref_line is out of range (index < 0 || index >= size())
	 */
	public void addReference(int line_num, int ref_line) throws IndexOutOfBoundsException;
	
	/**
	 * <b>getReference</b><br>
	 * Gets Reference that this line uses 
	 * to support its Inference
	 * @param line_num The Line number to get the Reference from
	 * @return A Reference of lines used to support this step
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public Reference getReference(int line_num) throws IndexOutOfBoundsException;
	
	/**
	 * <b>removeReference</b><br>
	 * Removes a Line from this Line's Reference
	 * @param line_num The Line number to edit
	 * @param ref_line The Line number to remove
	 * @throws IndexOutOfBoundsException if the line_num or ref_line is out of range (index < 0 || index >= size())
	 */
	public void removeReference(int line_num, int ref_line) throws IndexOutOfBoundsException;
	
	/**
	 * <b>removeReferences</b><br>
	 * Removes References from this Line
	 * @param line_num The Line number to remove the Reference from
	 * @throws IndexOutOfBoundsException if the line_num is out of range (index < 0 || index >= size())
	 */
	public void removeReferences(int line_num) throws IndexOutOfBoundsException;
	
	/**
	 * <b>isValid</b><br>
	 * Verifies the entire Proof and ensures each Line is a
	 * valid deduction using its Inference and Reference
	 * @return 1 if every Line in this proof is valid, <br>-line number of error if there is an invalid line
	 */
	public int isValid();
}
