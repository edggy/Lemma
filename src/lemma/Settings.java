/**
 * 
 */
package lemma;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author ethan
 *
 */
public class Settings {

	private Map<String, String> map;
	
	public Settings() {
		map = new HashMap<String, String>();
	}
	
	public Settings(Settings s) {
		map = new HashMap<String, String>();
		Set<Entry<String, String>> es = s.map.entrySet();
		for(Entry<String, String> e : es) {
			map.put(e.getKey(), e.getValue());
		}
	}
	
	public String get(String s) {
		return map.get(s);
	}
	
	public void set(String s, String v) {
		map.put(s, v);
	}
	
	public static Settings parseFile(File file) throws ParseException, IOException {
		return parseFile(file.getPath());
	}
	
	public static Settings parseFile(String path) throws ParseException, IOException {
		List<String> list = Files.readAllLines(Paths.get(path));
		Settings set = new Settings();
		String seperator = "\t";
		String comment = null;
		int lineNum = 0;
		for(String s : list) {
			lineNum++;
			String[] tok = null;
			if(comment != null && !comment.isEmpty()) s = s.split(comment, 1)[0];
			tok = s.split(seperator, 1);
			if(tok.length == 2) set.set(tok[0], tok[1]);
			else throw new ParseException("No seperator", lineNum);
			if(tok[0].equalsIgnoreCase("seperator")) seperator = tok[1];
			else if(tok[0].equalsIgnoreCase("comment")) comment = tok[1];
		}
		return set;
	}
}
