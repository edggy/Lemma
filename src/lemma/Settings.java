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
 * A Settings class, used to define rules to parse proofs
 * @author ethan
 *
 */
public class Settings {
	
	public static final String separator = "separator";
	public static final String line = "line";
	public static final String blank = "blank";
	public static final String comment = "comment_line";
	public static final String system_variable = "system_variable";
	public static final String settings_end = "settings_end";
	public static final String inference_start = "inference_start";
	public static final String inference_name = "inference_name";
	public static final String inference_premise = "inference_premise";
	public static final String inference_conclusion_start = "inference_conclusion_start";
	public static final String inference_conclusion = "inference_conclusion";
	public static final String inference = "inference";
	
	
	public static final String separator_group1 = "name";
	public static final String separator_group2 = "value";	
	//public static final String inference_name_group1 = "name";
	//public static final String inference_premise_group1 = "premise";
	//public static final String inference_conclusion_group1 = "conclusion";
	
	public static final int system_variable_timeout = 4;	
	
	private Map<String, Pattern> map;
	private Map<String, Boolean> expanded;
	
	public Settings() {
		map = new HashMap<String, Pattern>();
		expanded = new HashMap<String, Boolean>();
	}
	
	public Settings(Settings s) {
		map = new HashMap<String, Pattern>();
		expanded = new HashMap<String, Boolean>();
		Set<Entry<String, Pattern>> es = s.map.entrySet();
		for(Entry<String, Pattern> e : es) {
			map.put(e.getKey(), e.getValue());
		}
		Set<Entry<String, Boolean>> exs = s.expanded.entrySet();
		for(Entry<String, Boolean> e : exs) {
			expanded.put(e.getKey(), e.getValue());
		}
	}
	
	/**
	 * Retrieves the Pattern associated with this setting
	 * @param name The name of the setting
	 * @return The Pattern associated with this setting
	 */
	public Pattern get(String name) {
		return map.get(name);
	}
	
	public Set<String> getAll() {
		return map.keySet();
	}
	
	/**
	 * Sets the setting to the regex
	 * @param name The name of the setting
	 * @param regex The String representation of the Pattern
	 */
	public void set(String name, String regex) {
		expanded.put(name.toLowerCase(), false);
		map.put(name.toLowerCase(), Pattern.compile(regex));
	}
	
	/**
	 * Sets the setting to the Pattern
	 * @param name The name of the setting
	 * @param p The Pattern to set the setting to
	 */
	public void set(String name, Pattern p) {
		expanded.put(name.toLowerCase(), false);
		map.put(name.toLowerCase(), p);
	}
	
	public boolean matches(String name, String s) {
		expand(name);
		Pattern p = map.get(name.toLowerCase());
		if(p == null) return false;
		return p.matcher(s).find();
	}
	
	public Matcher getMatcher(String name, String s) {
		expand(name.toLowerCase());
		Pattern p = map.get(name.toLowerCase());
		if(p == null) return null;
		Matcher m = p.matcher(s);
		return m;
	}
	
	private void expand(String name) {
		if(expanded.get(name.toLowerCase())) return;
		if(system_variable.equalsIgnoreCase(name)) return;
		int count = 0;
		//System.out.println(name + " " + this.get(name));
		while(this.matches(system_variable, this.get(name).pattern())) {
			//if(count >= system_variable_timeout) throw new ParseException("System Variable replacement timed out" + ", line " + lineNum, lineNum);
			if(count >= system_variable_timeout) break;
			Matcher mat = this.getMatcher(system_variable, this.get(name).pattern());
			StringBuffer sb = new StringBuffer();
			while(mat.find()) {
				String var_name = mat.group(1);
				//System.out.println("Found: "+var_name+" Replacing: "+this.get(var_name).pattern());
				mat.appendReplacement(sb, Matcher.quoteReplacement(this.get(var_name).pattern()));
			}
			mat.appendTail(sb);
			//System.out.println("\t" + var_name + " " + this.get(var_name));
			//String value = mat.replaceFirst(Matcher.quoteReplacement(this.get(var_name).pattern()));
			this.set(name, sb.toString());
			count++;
		}
		expanded.put(name.toLowerCase(), true);
	}
	
	/**
	 * <b>parse</b><br>
	 * Parses a BufferedReader into a Settings object
	 * @param br A BufferedReader to parse into a Settings object
	 * @return A Settings file from the data from the BufferedReader
	 * @throws ParseException Thrown if there is an error in the syntax
	 * @throws IOException Thrown for any IO errors
	 */
	public static Settings parse(BufferedReader br) throws ParseException, IOException {
		Settings set = new Settings();
		//Default settings
		//This makes "=" the separator with quotes around the value
		set.set(separator, "(?<" + separator_group1 + ">\\w+?)\\s*=\\s*\"(?<" + separator_group2 + ">.+?)\"");
		set.set(line, ".*\n");
		set.set(blank, "^\\s*$");
		set.set(comment, "^\\s*#");
		set.set(system_variable, "@(?<name>\\w+)@");
		set.set(settings_end, "^\\s*SETTINGS END");
		int lineNum = 0;
		String s = "";
		while((s = Util.readLine(br, set)) != null) {
			lineNum++;
			if(set.matches(comment, s) || set.matches(blank, s)) continue;
			if(set.matches(settings_end, s)) return set;
			if(!set.matches(separator, s)) throw new ParseException("No " + separator + ", line " + lineNum, lineNum);
			Matcher m = set.getMatcher(separator, s);
			m.find();
			String name = m.group(separator_group1);
			String value = m.group(separator_group2);
			if(name == null) throw new ParseException("No " + separator_group1 + ", line " + lineNum, lineNum);
			if(value == null) throw new ParseException("No " + separator_group2 + ", line " + lineNum, lineNum);
			set.set(name, value);
			/*int count = 0;
			System.out.println(name + ": " + value);
			while(set.matches(system_variable, set.get(name).pattern())) {
				//if(count >= system_variable_timeout) throw new ParseException("System Variable replacement timed out" + ", line " + lineNum, lineNum);
				if(count >= system_variable_timeout) break;
				Matcher mat = set.getMatcher(system_variable, value);
				mat.find();
				String var_name = mat.group(1);
				System.out.println("\t" + var_name + " " + set.get(var_name));
				value = mat.replaceAll(Matcher.quoteReplacement(set.get(var_name).pattern()));
				set.set(name, value);
				count++;
			}*/
			//System.out.println(value);
			//if(set.matches(system_variable,value)) set.set(name, set.get(value));
			//else set.set(name, value);
			//set.set(name, value);
		}
		return set;
	}
	
	/**
	 * <b>parseFile</b><br>
	 * Takes a File and parses it into a Setting object
	 * @param file An input file to parse
	 * @return A Settings object of the File
	 * @throws ParseException Thrown if there is an error in the syntax
	 * @throws IOException Thrown for any IO errors
	 */
	public static Settings parseFile(File file) throws ParseException, IOException {
		return parse(new BufferedReader(new FileReader(file)));
	}
	
	/**
	 * <b>parseFile</b><br>
	 * Parses a file into a Setting object
	 * @param path The path to a file to parse
	 * @return A Settings object of the file at the given path
	 * @throws ParseException Thrown if there is an error in the syntax
	 * @throws IOException Thrown for any IO errors
	 */
	public static Settings parseFile(String path) throws ParseException, IOException {
		return parse(new BufferedReader(new FileReader(path)));
	}
}
