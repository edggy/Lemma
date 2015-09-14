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
		return readUntil(br, set, Settings.line);
	}
	
	public static String readLine(String text, Settings set) {
		try {
			return readUntil(stringToBr(text), set, Settings.line);
		} catch (IOException e) {
		}
		return null;
	}
	
	public static String readUntil(BufferedReader br, Settings set, String setting_match) throws IOException {
		String line = "";
		while(!set.matches(setting_match, line)) {
			int nextChar = br.read();
			if(nextChar < 0) return null;
			line += (char) nextChar;
		}
		return line;
	}
	
	public static BufferedReader stringToBr(String s) {
		return new BufferedReader(new StringReader(s));
	}
}
