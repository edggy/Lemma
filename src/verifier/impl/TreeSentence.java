package verifier.impl;

import java.util.ArrayList;
import java.util.List;
import verifier.Sentence;


public class TreeSentence extends AbstractSentence {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4668485688242515429L;
	/*public static class Variable extends TreeSentence{
		protected String name;
		
		public Variable(String name) {
			this.name = name;
			this.parts = null;
			this.operator = null;
		}
		
		@Override
		public boolean isVariable() {
			return true;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		@Override
		public verifier.Sentence clone() {
			return this; 
		}
	}*/
	protected List<verifier.Sentence> parts;
	protected verifier.Operator operator;
	
	public TreeSentence() {
		this.parts = new ArrayList<verifier.Sentence>();
		this.operator = null;
	}
	public TreeSentence(verifier.Sentence sen) {
		this.parts = new ArrayList<verifier.Sentence>();
		for(verifier.Sentence s : sen.parts()) {
			this.parts.add(s.clone());
		}
		
		verifier.Operator op = sen.getOperator();
		this.operator = op != null?op.clone():null;
	}
	
	public TreeSentence(List<Sentence> parts, verifier.Operator operator) {
		this.parts = new ArrayList<verifier.Sentence>();
		for(verifier.Sentence s : parts) {
			this.parts.add(s.clone());
		}
		this.operator = operator != null?operator.clone():null;
	}
	
	@Override
	public List<verifier.Sentence> parts() {
		ArrayList<verifier.Sentence> result = new ArrayList<verifier.Sentence>();
		for(verifier.Sentence sen : this.parts) {
			//if(sen.isVariable()) result.add(new Variable(sen.toString()));
			result.add(sen.clone());
		}
		return result;
	}

	@Override
	public verifier.Operator getOperator() {
		verifier.Operator op = this.operator;
		return op != null?op.clone():null;
	}
	
	@Override
	public verifier.Sentence clone() {
		/*
		TreeSentence copy = new TreeSentence();
		verifier.Operator op = this.getOperator();
		
		if(parts.size() <= 0) {
			return copy;
		}
		for(verifier.Sentence sen : this.parts) {
			copy.parts.add(sen.clone());
		}
		return copy;
		*/
		return new TreeSentence(this);
	}
	
	@Override
	public String toString() {
		String result = "(";
		result += this.operator != null?this.operator + " ":"";
		boolean first = true;
		for(verifier.Sentence sen : this.parts) {
			if(first) {
				result += sen;
				first = false;
			}
			else {
				result += "," + sen;
			}
		}
		result += ")";
		return result;
	}
	


}
