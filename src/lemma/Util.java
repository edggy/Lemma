/**
 * 
 */
package lemma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author Ethan
 *
 */
public class Util {
	public static String readLine(BufferedReader br, Settings set) throws IOException {
		String line = "";
		while(!set.matches(Settings.line, line)) {
			int nextChar = br.read();
			if(nextChar < 0) return null;
			line += (char) nextChar;
		}
		return line;
	}
	
	public static String readLine(String text, Settings set) {
		try {
			return readLine(stringToBr(text), set);
		} catch (IOException e) {
		}
		return null;
	}
	
	public static BufferedReader stringToBr(String s) {
		return new BufferedReader(new StringReader(s));
	}
}
