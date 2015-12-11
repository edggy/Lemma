package verifier.impl;

import java.util.LinkedList;
import java.util.List;
import verifier.Line;
import verifier.Proof;
import verifier.Reference;
import verifier.Sentence;

public class Lemma extends Inference implements verifier.Lemma {
	/**
	 * 
	 */
	private static final long serialVersionUID = 724998202949087078L;
	protected Proof proof;
	//protected String name;
	
	public Lemma(String name, Proof proof) {
		this.proof = proof;
		this.name = name;
	}
	
	/*private Lemma(List<Sentence> premises, Sentence conclusion) {
		super(premises, conclusion);
	}*/

	@Override
	public boolean isValid(Sentence sen, Reference ref) {
		if(proof.isValid() < 0) return false;
		List<Line> lines = proof.getLines();
		//TODO generalize to use any line as a conclusion
		this.conclusion = lines.get(lines.size()-1).s;
		//ASSUMPTION;
		this.premises = new LinkedList<Sentence>();
		for(Line l : lines) {
			if(Proof.ASSUMPTION.equals(l.i)) {
				this.premises.add(l.s);
			}
		}
		return super.isValid(sen, ref);
		/*
		Map<Sentence, Sentence> conclusionMap = conclusion.s.mapInto(sen);
		if(conclusionMap == null) return false;
		super.makeMapping(conclusionMap, prem, sens)
		return false;*/
	}

	@Override
	public Proof getProof() {
		return proof;
	}
	
	@Override
	public String name() {
		return name;
	}

}
