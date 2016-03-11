package verifier;

/**
 * 
 * @author Ethan Gertler
 *
 */
public interface SentenceParser {
	/**
	 * 
	 * @author Ethan Gertler
	 *
	 */
	public class InvalidSentenceException extends Exception {

		private static final long serialVersionUID = 7638900789282584749L;
		
	}
	
	/**
	 * <p>Parses a {@code String} into a {@code Sentence}</p>
	 * <b>Note:</b>
	 * <p style="margin-left: 40px">
	 * 		For any valid {@code String}, str, {@code toString(parse(str))} should return str<br>
	 * 		And For any valid Sentence, sen, {@code parse(toString(sen))} should return sen
	 * </p>
	 * @param sentence A {@code String} representation of a {@code Sentence}
	 * @return A {@code Sentence} that the {@code String} represents
	 * @throws InvalidSentenceException If the {@code String} is not a valid {@code Sentence}
	 * @see SentenceParser#toString()
	 */
	public Sentence parse(String sentence) throws InvalidSentenceException;
	
	/**
	 * <p>Transforms a {@code Sentence} into its representation as a {@code String}</p>
	 * <b>Note:</b>
	 * <p style="margin-left: 40px">
	 * 		For any valid {@code String}, str, {@code toString(parse(str))} should return str<br>
	 * 		And For any valid Sentence, sen, {@code parse(toString(sen))} should return sen<br>
	 * 		
	 * </p>
	 * @param sentence The {@code Sentence} to be converted
	 * @return A {@code String} representation of the {@code Sentence}
	 * @see SentenceParser#parse()
	 */
	public String toString(Sentence sentence);
}
