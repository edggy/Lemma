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
	
	public boolean hasInference(Inference i) {
		return inferenceList.contains(i);
	}

	public int hashCode() {
		return inferenceList.hashCode();
	}

	public boolean equals(Object o) {
		if(!(o instanceof Inferences)) return false;
		for(Inference i : inferenceList) {
			if(!((Inferences) o).hasInference(i)) return false;
		}
		return true;
	}

	public String toString() {
		//TODO
		String result = "";
		for(Inference i : inferenceList) {
			result += i.toString() + '\n';
		}
		return result;
	}

	public static Inferences parse(File file, Settings set) throws ParseException, IOException {
		return parse(new BufferedReader(new FileReader(file)), set);
	}

	public static Inferences parse(String path, Settings set) throws ParseException, IOException {
		return parse(new BufferedReader(new FileReader(path)), set);
	}

	public static Inferences parse(BufferedReader br, Settings set) throws ParseException, IOException {
		int lineNum = 0;
		Inferences inferences = new Inferences();
		try{
			String line;
			while((line = Util.readUntil(br, set, Settings.inference)) != null) {
				lineNum++;
				inferences.addInference(Inference.parse(line, set));
			}
		} catch(ParseException e) {
			throw new ParseException(e.getMessage(),e.getErrorOffset() + lineNum);
		}
		return inferences;
	}
}
