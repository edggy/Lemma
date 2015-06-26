/**
 * 
 */
package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author ethan
 *
 */
public interface Parser {
	
	public Object parseFile(File file) throws ParseException, FileNotFoundException, IOException;
	
	public Object parseFile(File file, String delimiter) throws ParseException, IOException;
	
	public Object parseString(String file) throws ParseException;

	public Object parseString(String file, String delimiter) throws ParseException;
}
