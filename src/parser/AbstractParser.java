/**
 * 
 */
package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ethan
 *
 */
public abstract class AbstractParser implements Parser {
	
	public static final String ls = System.getProperty("line.separator");
	
	@Override
	public List<String> parseFile(File file) throws ParseException, IOException {
		return parseFile(file, ls);
	}
	
	@Override
	public List<String> parseFile(File file, String delimiter) throws ParseException, IOException {
		
		List<String> list = new LinkedList<String>();
		String line = "";
		String section = "";
		BufferedReader reader = new BufferedReader( new FileReader (file));
		
		// For each line
		while( (line = reader.readLine()) != null ) {
			
			//append it to the current section
			section += line = ls;
			
			//Split it up into tokens based on the delimiter
	        String[] tok = section.split(delimiter);
	        
	        //For all but the last token, add it to the list
			for(int i = 0; i < tok.length - 1; i++) {
	        	list.add(tok[i]);
	        }
			
			//make the current section the last unprocessed token
			section = tok[tok.length-1];
	    }
		
		//add the last section to the list
		list.add(section);
		
		reader.close();
	    return list;
	}
	
	@Override
	public List<String> parseString(String str, String delimiter) throws ParseException {
		List<String> list = new LinkedList<String>();
		String[] tok = str.split(delimiter);
		for(String s : tok) {
			list.add(s);
		}
		return list;
	}
}
