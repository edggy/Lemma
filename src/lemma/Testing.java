/**
 * 
 */
package lemma;

import java.io.IOException;
import java.text.ParseException;

/**
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
				Settings s = Settings.parseFile("B:\\Users\\Ethan.Ethan-Alienware\\git\\Lemma\\DefaultSettings.txt");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
