/**
 * 
 */
package lemma;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ethan
 *
 */
public class Settings {
	
	public static final String separator = "separator";

	public static final String blank = "blank";
	public static final String comment = "comment_line";
	public static final String system_variable = "system_variable";
	public static final String inference_start = "inference_start";
	public static final String inference_name = "inference_name";
	public static final String inference_premise = "inference_premise";
	public static final String inference_conclusion_start = "inference_conclusion_start";
	public static final String inference_conclusion = "inference_conclusion";
	
	
	
	public static final String separator_group1 = "name";
	public static final String separator_group2 = "value";	
	//public static final String inference_name_group1 = "name";
	//public static final String inference_premise_group1 = "premise";
	//public static final String inference_conclusion_group1 = "conclusion";
	
	
	private Map<String, Pattern> map;
	
	public Settings() {
		map = new HashMap<String, Pattern>();
	}
	
	public Settings(Settings s) {
		map = new HashMap<String, Pattern>();
		Set<Entry<String, Pattern>> es = s.map.entrySet();
		for(Entry<String, Pattern> e : es) {
			map.put(e.getKey(), e.getValue());
		}
	}
	
	public Pattern get(String name) {
		return map.get(name);
	}
	
	public void set(String name, String p) {
		map.put(name.toLowerCase(), Pattern.compile(p));
	}
	
	public void set(String name, Pattern p) {
		map.put(name.toLowerCase(), p);
	}
	
	public boolean matches(String name, String s) {
		Pattern p = map.get(name.toLowerCase());
		if(p == null) return false;
		return p.matcher(s).find();
	}
	
	public Matcher getCapturedGroups(String name, String s) {
		Pattern p = map.get(name.toLowerCase());
		if(p == null) return null;
		Matcher m = p.matcher(s);
		m.find();
		return m;
	}
	
	public static Settings parse(BufferedReader br) throws ParseException, IOException {
		Settings set = new Settings();
		//set.set(separator, "(?<"+separator_group1+">.+?)\\s*=\\s*\\\"(?<"+separator_group2+">.++)\\\"");
		set.set(separator, "(?<name>\\w+?)\\s*=\\s*\"(?<value>.+?)\"");
		set.set(blank, "^\\s*$");
		set.set(comment, "^\\s*#");
		int lineNum = 0;
		String s = "";
		while((s = br.readLine()) != null) {
			lineNum++;
			if(set.matches(comment, s) || set.matches(blank, s)) continue;
			if(!set.matches(separator, s)) throw new ParseException("No " + separator + ", line " + lineNum, lineNum);
			Matcher m = set.getCapturedGroups(separator, s);
			String name = m.group(separator_group1);
			String value = m.group(separator_group2);
			if(name == null) throw new ParseException("No " + separator_group1 + ", line " + lineNum, lineNum);
			if(value == null) throw new ParseException("No " +separator_group2 + ", line " + lineNum, lineNum);
			while(set.matches(system_variable,value)) {
				System.out.println(value);
				Matcher mat = set.getCapturedGroups(system_variable, value);
				System.out.println(mat.group());
				String var_name = mat.group(1);
				value = mat.replaceFirst(Matcher.quoteReplacement(set.get(var_name).pattern()));
			}
			System.out.println(value);
			if(set.matches(system_variable,value)) set.set(name, set.get(value));
			else set.set(name, value);
		}
		return set;
	}
	
	public static Settings parseFile(File file) throws ParseException, IOException {
		return parse(new BufferedReader(new FileReader(file)));
	}
	
	public static Settings parseFile(String path) throws ParseException, IOException {
		return parse(new BufferedReader(new FileReader(path)));
		/*List<String> list = Files.readAllLines(Paths.get(path));
		Settings set = new Settings();
		set.set("seperator", "(?<name>.+?)\\s*=\\s*\\\"(?<value>.++)\\\"");
		set.set("blank", "\\s*");
		//String seperator = "(?<name>.+?)\\s*=\\s*\\\"(?<value>.++)\\\"";
		//String comment = null;
		int lineNum = 0;
		for(String s : list) {
			lineNum++;
			if(set.matches("comment", s) || set.matches("blank", s)) continue;
			if(!set.matches("seperator", s)) throw new ParseException("No separator", lineNum);
			
			Matcher m = set.getCapturedGroups("seperator", s);
			String name = m.group("name");
			String value = m.group("value");
			if(name == null) throw new ParseException("No name", lineNum);
			if(value == null) throw new ParseException("No value", lineNum);
			set.set(name, value);
			
			//String[] tok = null;
			//if(comment != null && !comment.isEmpty()) s = s.split(comment)[0];
			//tok = s.split(seperator, 1);
			//if(tok.length == 2) set.set(tok[0], tok[1]);
			//else throw new ParseException("No separator", lineNum);
			//if(tok[0].equalsIgnoreCase("separator")) seperator = tok[1];
			//else if(tok[0].equalsIgnoreCase("comment")) comment = tok[1];
		}
		return set;*/
	}
}
