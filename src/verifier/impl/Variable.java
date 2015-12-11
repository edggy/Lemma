package verifier.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import verifier.Operator;
import verifier.Sentence;

public class Variable extends AbstractSentence implements verifier.Variable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6130239830546251981L;

	public static class Contradiction extends Variable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5930626804557127162L;

		public Contradiction() {
			name = "Contradiction";
		}
		
		public Map<verifier.Variable, Sentence> mapInto(verifier.Sentence sen) {
			if(this.equals(sen)) {
				Map<verifier.Variable, Sentence> result = new HashMap<verifier.Variable, Sentence>();
				result.put(this, this);
				return result;
			}
			return null;
		}
		
		public boolean equals(Object o) {
			if(!(o instanceof Contradiction)) return false;
			return this.name.equals(((Contradiction)o).name);
		}
	}
	
	protected String name;
	private static int index = 1;
	
	public Variable() {
		this.name = "X" + index++;
	}
	
	public Variable(String name) {
		this.name = name;
	}
	
	@Override
	public List<Sentence> parts() {
		return null;
	}

	@Override
	public Operator getOperator() {
		return null;
	}
	
	@Override
	public Map<verifier.Variable, Sentence> mapInto(verifier.Sentence sen) {
		Map<verifier.Variable, Sentence> result = new HashMap<verifier.Variable, Sentence>();
		result.put(this, sen);
		return result;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Variable)) return false;
		return this.name.equals(((Variable)o).name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public verifier.Sentence clone() {
		return this; 
	}

	@Override
	public String name() {
		return name;
	}

}
