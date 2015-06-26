/**
 * 
 */
package lemma;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ethan
 *
 */
public class Inference {
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
	
	public void addPremise(Sentence premise) {
		premises.add(premise);
	}
	
	public void addPremises(Collection<Sentence> premise) {
		premises.addAll(premise);
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
	
	
}
