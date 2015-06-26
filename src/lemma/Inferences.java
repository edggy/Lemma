/**
 * 
 */
package lemma;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

/**
 * @author ethan
 *
 */
public class Inferences {
	private Set<Inference> inferenceList;
	
	public Inferences() {
		inferenceList = new HashSet<Inference>();
	}
	
	public int hashCode() {
		return inferenceList.hashCode();
	}
	
	public boolean equals(Object o) {
		//TODO
		return false;
	}
	
	public String toString() {
		//TODO
		return null;
	}
	
	public static Inference parseFile(File file, Settings set) throws ParseException, IOException {
		return parseFile(file.getPath(), set);
	}
	
	public static Inference parseFile(String path, Settings set) throws ParseException, IOException {
		
	}
}
