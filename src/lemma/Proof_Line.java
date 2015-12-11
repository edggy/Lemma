package lemma;

import java.util.regex.Matcher;

public class Proof_Line {

	/**
	 * The setting name for a regex that matches a line in a proof
	 */
	public static final String PROOF_LINE = "proof_line";
	
	/**
	 * The setting name for regex tag in PROOF_LINE that matches the entire depth
	 */
	public static final String PROOF_DEPTH = "proof_depth";
	
	/**
	 * The setting name for regex tag in PROOF_LINE that matches the Sentence
	 */
	public static final String PROOF_SENTENCE = "proof_sen";
	
	/**
	 * The setting name for regex tag in PROOF_LINE that matches the line's inference rule
	 */
	public static final String PROOF_INFERENCE = "proof_ref";
	
	/**
	 * The setting name for regex tag in PROOF_LINE that matches the line's references
	 */
	public static final String PROOF_REFERENCES = "proof_ref";
	
	/**
	 * The setting name for a regex that matches one depth
	 */
	public static final String DEPTH_MATCH = "depth_match";
	
	private Sentence sen;
	private Inference inf;
	private String ref;
	private int depth;
	
	public Proof_Line(String s, Settings set) throws ParseException {
		Matcher m = set.getMatcher(PROOF_LINE, s);
		if(!m.find()) throw new ParseException();
		//set.get(PROOF_DEPTH).toString()
		//String depth_str = m.group(PROOF_DEPTH);
		//depth = get_depth(depth_str, sen);
		//sen = new Sentence(m.group(PROOF_SENTENCE), set);
		
		//TODO  parse wff from the reference lines
	}
	
	public Sentence getSentence() {
		return sen;
	}
	
	public String getReferences() {
		return ref;
	}
	
	public boolean verify(Inferences inferences) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static Proof_Line parse(String s, Settings set) throws ParseException  {
		return new Proof_Line(s, set);
	}
	
	//private static int get_depth(String s, Settings set) {
	//	Matcher m = set.getMatcher(DEPTH_MATCH, s);
	//}

}
