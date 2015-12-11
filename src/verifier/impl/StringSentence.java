package verifier.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSentence extends AbstractSentence {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7047509194124600962L;
	public static final String SENTENCE_PART = "part_#";
	public static final String SENTENCE_OPERATOR= "oper";
	
	Pattern pat;
	Matcher sen;
	
	public StringSentence(String regex, String s) {
		pat = Pattern.compile(regex);
		sen = pat.matcher(s);
		sen.reset().find();
	}
	
	public StringSentence(Pattern p, String s) {
		pat = p;
		sen = pat.matcher(s);
		sen.reset().find();
	}
	
	@Override
	public List<verifier.Sentence> parts() {
		ArrayList<verifier.Sentence> result = new ArrayList<verifier.Sentence>();
		int index = 1;
		while(true) {
			try {
				String str = sen.group(SENTENCE_PART.replaceAll("#", String.valueOf(index)));
				result.add(new StringSentence(pat, str));
			}
			catch(IllegalArgumentException e) {
				break;
			}
		}
		
		return result;
	}

	@Override
	public verifier.Operator getOperator() {
		String str = sen.group(SENTENCE_OPERATOR);
		return new Operator(str);
	}
	
	@Override
	public boolean canMapInto(verifier.Sentence sen) {
		return mapInto(sen) == null;
	}

	@Override
	public verifier.Sentence clone() {
		return new StringSentence(pat, sen.group(0));
	}

}
