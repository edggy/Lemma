package verifier.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
		return new TreeSentence(this);
	}
	
	@Override
	public Sentence replaceAll(Sentence find, Sentence replace) {
		final ListIterator<Sentence> li = this.parts.listIterator();
	     while (li.hasNext()) {
	    	 Sentence cur = li.next();
	    	 if(cur.equals(find)) li.set(replace);
	    	 else cur.replaceAll(find, replace);
	     }
		return this;
	}

}
