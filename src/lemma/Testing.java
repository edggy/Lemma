/**
 * 
 */
package lemma;

import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

/**
 * A debugging class
 * @author ethan
 *
 */
public class Testing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				Settings s = Settings.parseFile("DefaultSettings.txt");
				System.out.println(s.matches("comment_line", "    # this is a comment"));
				Set<String> keys = s.getAll();
				for(String key : keys) {
					System.out.println(key + ": " + s.get(key));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
