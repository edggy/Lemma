package verifier.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import verifier.Sentence;
import verifier.SentenceParser;
import verifier.Variable;

public abstract class AbstractSentence implements Sentence {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1324374494088588165L;
	public static verifier.SentenceParser parser = new verifier.impl.SentenceParser();
	
	public abstract Sentence clone();
	
	@Override
	public Map<verifier.Variable, Sentence> mapInto(verifier.Sentence sen) {
		Map<verifier.Variable, Sentence> result = new HashMap<verifier.Variable, Sentence>();
		if(this instanceof verifier.Variable) {
			result.put((Variable) this, sen);
			return result;
		}
		if(getOperator() != null) {
			if(!getOperator().equals(sen.getOperator())) return null;
		}
		else if(sen.getOperator() != null) return null;
		if(this.parts().size() != sen.parts().size()) return null;
		Iterator<Sentence> thisPartsi = this.parts().iterator();
		Iterator<Sentence> senPartsi = sen.parts().iterator();
		while(thisPartsi.hasNext() && senPartsi.hasNext()) {
			Sentence thisPart = thisPartsi.next();
			Sentence senPart = senPartsi.next();
			Map<verifier.Variable, Sentence> m = thisPart.mapInto(senPart);
			if(m == null) return null;
			result = Util.mapMerge(result, m);
			if(result == null) return null;
		}
		return result;
	}
	
	@Override
	public boolean canMapInto(verifier.Sentence sen) {
		return mapInto(sen) != null;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Sentence)) return false;
		Sentence s = (Sentence)o;
		if(this.getOperator() == null) {
			
			if(s.getOperator() != null) return false;
		}
		else {
			if(!this.getOperator().equals(s.getOperator())) {
				return false;
			}
		}
		return this.parts().equals(s.parts());
	}
	
	@Override
	public void setParser(SentenceParser p) {
		parser = p;
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	public String toString() {
		return parser.toString(this);
	}
}
