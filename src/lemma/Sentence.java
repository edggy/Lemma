/**
 * 
 */
package lemma;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * A logic sentence
 * @author ethan
 *
 */
public class Sentence {
	
	public static final String operator = "operator";
	public static final String variable = "variable";
	public static final String sentence = "wff";
	
	private String leftPart;
	private String rightPart;
	private String op;

	public Sentence(String s, Settings set) throws ParseException {
		Matcher m = set.getMatcher(sentence, s);
		
		List<String> groups = new LinkedList<String>();
		while(m.find()) {
			groups.add(m.group());
		}
		if(groups.size() <= 0)throw new ParseException("Invalid Sentence", -1, 0);
		int index = 0;
		for(String gp : groups) {
			if(index == 0) {
				
			}
			else if(set.matches(operator, gp)) {
				op = gp;
			}
			else if(set.matches(sentence, gp)) {
				if(leftPart == null) leftPart = gp;
				else if(rightPart == null) rightPart = gp;
				else throw new ParseException("Too many sentence capuring groups", -1, m.start(index));
			}
			else throw new ParseException("Too many capuring groups", -1, m.start(index));
			index++;
		}
		
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Sentence)) return false;
		Sentence s = (Sentence) o;
		if(leftPart != s.leftPart) return false;
		if(rightPart != s.rightPart) return false;
		if(op != s.op) return false;
		return true;
	}
	
	public int hashCode() {
		return (leftPart + op + rightPart).hashCode();
	}
	
	public String toString() {
		return leftPart + ' ' +  op + ' ' + rightPart;
	}
	
	public static Sentence parse(String s, Settings set) throws ParseException  {
		return new Sentence(s, set);
	}
}
