/**
 * 
 */
package lemma;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * All the Inference rules for the system
 * @author Ethan
 *
 */
public class Inferences {
	private Set<Inference> inferenceList;

	public Inferences() {
		inferenceList = new HashSet<Inference>();
	}

	public void addInference(Inference i) {
		inferenceList.add(i);
	}

	public void addInference(Collection<Inference> i) {
		inferenceList.addAll(i);
	}

	public void removeInference(Inference i) {
		inferenceList.remove(i);
	}

	public void removeInference(Collection<Inference> i) {
		inferenceList.removeAll(i);
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

	public static Inferences parseFile(File file, Settings set) throws ParseException, IOException {
		return parseFile(new BufferedReader(new FileReader(file)), set);
	}

	public static Inferences parseFile(String path, Settings set) throws ParseException, IOException {
		return parseFile(new BufferedReader(new FileReader(path)), set);
	}

	public static Inferences parseFile(BufferedReader br, Settings set) throws ParseException, IOException {
		int lineNum = 0;
		Inferences inferences = new Inferences();
		try{
			String s;
			while((s = br.readLine()) != null) {
				lineNum++;
				inferences.addInference(Inference.parse(br, set));
			}
		} catch(ParseException e) {
			throw new ParseException(e.getMessage(),e.getErrorOffset() + lineNum);
		}
		return inferences;
	}
}
