package lemma;

import java.util.regex.Matcher;

public class Proof_Line {

	public static final String proof_line = "proof_line";
	
	private Sentence sen;
	private String ref;
	
	public Proof_Line(String s, Settings set) throws ParseException {
		Matcher m = set.getMatcher(proof_line, s);
		//TODO  parse wff from the reference lines
	}
	
	public Sentence getSentence() {
		return sen;
	}
	
	public String getReferences() {
		return ref;
	}
	
	public static Proof_Line parse(String s, Settings set) throws ParseException  {
		return new Proof_Line(s, set);
	}

}
