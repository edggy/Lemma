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
		// (A)
		// (not A)
		// ((A), (B))
		// (or A, B)
		// (or (A), (B))
		// (or  (or (A), (B)), (or (C), (D)))
		if(sentence == null) throw new InvalidSentenceException();
		sentence = sentence.trim();
		int countOpen = sentence.length() - sentence.replaceAll("\\(", "").length();
		int countClose = sentence.length() - sentence.replaceAll("\\)", "").length();
		int countSpace = sentence.length() - sentence.replaceAll("\\s", "").length();
		if(countOpen != countClose) throw new InvalidSentenceException();
		if(countOpen == 0 && countSpace == 0) {
			// A
			return new Variable(sentence);
		}
		
		int first_paren = sentence.indexOf('(');
		int last_paren = getMatchingParen(sentence, first_paren);
		if(first_paren != 0 || last_paren != sentence.length()-1) throw new InvalidSentenceException();
		sentence = sentence.substring(1, sentence.length()-1).trim();
		countOpen--;
		countClose--;
		
		// A
		// not A
		// (A), (B)
		// or A, B
		// or (A), (B)
		// or  (or (A), (B)), (or (C), (D))
		
		if(countOpen == 0) {
			// A
			// not A
			String[] tok = sentence.replaceAll(",", "").split("\\s+");
			if(tok.length == 1) return new Variable(sentence);
			if(tok.length >= 2) {
				verifier.Operator operator = new verifier.impl.Operator(tok[0]);
				List<Sentence> parts = new LinkedList<Sentence>();
				
				for(int i = 1; i < tok.length; i++) parts.add(new Variable(tok[i]));
				return new TreeSentence(parts, operator);
			}
			
			throw new InvalidSentenceException();
		}
		
		//System.out.println(sentence);
		// not (A)
		// (A), (B)
		// or (A), (B)
		// or  (or (A), (B)), (or (C), (D))
		first_paren = sentence.indexOf('(');
		verifier.Operator operator = null;
		if(first_paren != 0) {
			// not (A)
			// or (A), (B)
			// or (or (A), (B)), (or (C), (D))
			String oper = sentence.substring(0, first_paren).trim();
			operator = new verifier.impl.Operator(oper);
			sentence = sentence.substring(first_paren);
		}
		// (A)
		// (A), (B)
		// or (A), (B)
		// (or (A), (B)), (or (C), (D))
		
		List<Sentence> parts = new LinkedList<Sentence>();
		while(sentence.length() > 0) {
			int match_paren = getMatchingParen(sentence, 0);
			if(match_paren < 0) throw new InvalidSentenceException();
			parts.add(parse(sentence.substring(1, match_paren)));
			try {
				//while(sentence.charAt(match_paren) != ',') match_paren++;
				while(sentence.charAt(match_paren) != '(') match_paren++;
				sentence = sentence.substring(match_paren);
			} 
			catch(IndexOutOfBoundsException e) {
				break;
			}
		}
		return new TreeSentence(parts, operator);
	}

}
