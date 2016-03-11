package verifier.impl;

import java.util.LinkedList;
import java.util.List;

import verifier.Sentence;

public class SentenceParser implements verifier.SentenceParser {
	
	private int getMatchingParen(String sentence, int index) {
		int pos = index;
		int openBr = 1;
		while(openBr > 0) {
			pos++;
			if(pos >= sentence.length()) return -1;
			if(sentence.charAt(pos) == '(') openBr++;
			else if(sentence.charAt(pos) == ')') openBr--;
		}
		return pos;
	}

	@Override
	public Sentence parse(String sentence) throws InvalidSentenceException {
		// A
		// op($)
		// op($, $)
		// op($, $, $, ...)
		
		if(sentence == null || sentence == "") throw new InvalidSentenceException();
		sentence = sentence.replaceAll("\\s+", "").trim();
		int countOpen = sentence.length() - sentence.replaceAll("\\(", "").length();
		int countClose = sentence.length() - sentence.replaceAll("\\)", "").length();
		if(countOpen != countClose) throw new InvalidSentenceException();
		// A
		// op($)
		// op($,$)
		// op($,$,$,...)
		if(countOpen == 0) {
			// A
			if(sentence.equals("!")) {
				return new Variable.Contradiction();
			}
			return new Variable(sentence);
		}
		int first_paren = sentence.indexOf('(');
		int last_paren = getMatchingParen(sentence, first_paren);
		
		// Operator is everything before the first (
		String oper = sentence.substring(0, first_paren);
		verifier.Operator operator = null;
		if(oper.equals(MetaInference.METAOPERATOR.toString())) {
			operator = MetaInference.METAOPERATOR;
		}
		else {
			operator = new verifier.impl.Operator(oper);
		}
		
		List<Sentence> parts = new LinkedList<Sentence>();
		String[] tok = sentence.substring(first_paren+1, last_paren).split(",");
		String part = "";
		for(int i = 0; i < tok.length; i++) {
			String newPart = part + tok[i];
			countOpen = newPart.length() - newPart.replaceAll("\\(", "").length();
			countClose = newPart.length() - newPart.replaceAll("\\)", "").length();
			if(countOpen == countClose) {
				parts.add(parse(newPart));
				part = "";
			}
			else {
				part += tok[i] + ",";
			}
		}
		return new TreeSentence(parts, operator);
	}

	@Override
	public String toString(Sentence sentence) {
		String ret = sentence.getOperator().toString() + "(";
		boolean first = true;
		for(Sentence s : sentence.parts()) {
			if(first) {
				ret += s;
				first = false;
			}
			else {
				ret += ", " + s;
			}
		}
		ret += ")";
		return ret;
	}

}
