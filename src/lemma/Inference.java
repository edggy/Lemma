/**
 * 
 */
package lemma;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * @author ethan
 *
 */
public class Inference {
	private enum Mode {
		Before, Name, Premises, Conclusion, After, Error;
	}
	private String name;
	private Set<Sentence> premises;
	private Sentence conclusion;
	
	public Inference() {
		name = "";
		premises = new HashSet<Sentence>();
	}
	
	public Inference(String name) {
		this.name = name;
		premises = new HashSet<Sentence>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addPremise(Sentence premise) {
		premises.add(premise);
	}
	
	public void addPremises(Collection<Sentence> premise) {
		premises.addAll(premise);
	}
	
	public void removePremise(Sentence premise) {
		premises.remove(premise);
	}
	
	public void removePremises(Collection<Sentence> premise) {
		premises.removeAll(premise);
	}
	
	public void setConclusion(Sentence conclusion) {
		this.conclusion = conclusion;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Inference)) return false;
		Inference i = (Inference)o;
		if(premises == null && i.premises != null) return false;
		if(!premises.equals(i.premises)) return false;
		if(conclusion == null && i.conclusion != null) return false;
		if(!conclusion.equals(i.conclusion)) return false;
		return true;
	}
	
	public int hashCode() {
		return premises.hashCode()^(conclusion.hashCode()+1);
	}
	
	public String toString() {
		//TODO
		return null;
	}
	
	public static Inference parse(BufferedReader br, Settings set) throws ParseException, IOException {
		return parse(br, set, 0);
	}
	
	public static Inference parse(BufferedReader br, Settings set, int start) throws ParseException, IOException {
		Inference in = new Inference();
		Mode mode = Mode.Before;
		String s = "";
		int lineNum = start;
		while((s = br.readLine()) != null && !mode.equals(Mode.After) && !mode.equals(Mode.Error)) {
			lineNum++;
			if(set.matches(Settings.comment, s) || set.matches(Settings.blank, s)) continue;
			switch(mode) {
				case Before: 
					if(set.matches(Settings.inference_start, s)) {
						mode = Mode.Name;
					}
					else break;
				case Name:
					if(set.matches(Settings.inference_name, s)) {
						in.setName(set.getCapturedGroups(Settings.inference_name, s).group(1));
						mode = Mode.Premises;
					}
					break;
				case Premises:
					if(set.matches(Settings.inference_conclusion_start, s)) {
						mode = Mode.Conclusion;
					}
					else if(set.matches(Settings.inference_premise, s)) {
						in.addPremise(Sentence.parse(set.getCapturedGroups(Settings.inference_premise, s).group(1)));
						break;
					}
				case Conclusion:
					if(set.matches(Settings.inference_conclusion, s)) {
						in.setName(set.getCapturedGroups(Settings.inference_conclusion, s).group(1));
						mode = Mode.After;
					}
					break;
				default:
					mode = Mode.Error;
			}
		}
		if(!mode.equals(Mode.After)) throw new ParseException("Inference Parsing Error" + ", line " + lineNum, lineNum);
		return in;
	}
}
