package verifier;

public interface SentenceParser {
	public class InvalidSentenceException extends Exception {

		private static final long serialVersionUID = 7638900789282584749L;
		
	}
	
	public Sentence parse(String sentence) throws InvalidSentenceException;
}
