/**
 * 
 */
package lemma;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author Ethan
 *
 */
public class Proof {
	
	public static final String proof_end = "proof_end";
	
	private List<Sentence> sen;
	
	public Proof(String proof, Settings set) throws ParseException {
		int lineNum = 0;
		String s = "";
		sen = new Vector<Sentence>();
		try {
			while((s = Util.readLine(proof, set)) != null) {
				lineNum++;
				sen.add(Sentence.parse(s, set));
			}
		} catch (ParseException e) {
			throw new ParseException(e.getMessage(), lineNum, e.getColumn());
		}
	}
	
	/**
	 * Proof verifier
	 * @param inferences The Inferences to use to verify the proof
	 * @param settings The Settings to use to verify the proof
	 * @return Whether the proof is valid based on the Inferences and Settings
	 */
	public boolean verify(Inferences inferences) {
		//TODO
		return false;
	}

	public static Proof parse(BufferedReader br, Settings set) throws ParseException, IOException {
		String s = Util.readUntil(br, set, proof_end);
		return new Proof(s, set);
	}
}
