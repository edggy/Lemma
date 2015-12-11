package verifier.impl;

import java.util.LinkedList;
import java.util.List;

import verifier.Line;
import verifier.Reference;
import verifier.Sentence;

/*
 *	P |- Contradiction	MI1
 */
/*	MI1:
 *	P	Assumption
 *	.
 *	.
 *	Contradiction
 */
public class MetaInference extends Inference implements verifier.Inference {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2091463917624086308L;
	public static final verifier.Operator METAOPERATOR = new Operator("|-");
	protected Lemma lemma;
	
	public MetaInference(String name, Lemma l) {
		this.name = name;
		this.lemma = l;
	}
	
	@Override
	public boolean isValid(Sentence sen, Reference ref) {
		if(sen == null) return false;
		verifier.Proof p = lemma.getProof();
		if(p.isValid() < 0) return false;
		Line firstLine = p.getLine(0);
		List<Sentence> assums = new LinkedList<Sentence>();
		for(Line line : p.getLines()) {
			if(Proof.ASSUMPTION.equals(line.i) && !firstLine.equals(line)) {
				assums.add(line.s);
			}
		}
		if(assums.size() > 1) {
			if(ref == null) return false;
			List<Sentence> ref_sens = new LinkedList<Sentence>();
			for(Line line : ref.getReference()) {
				ref_sens.add(line.s);
			}
			if(!ref_sens.containsAll(assums)) return false;
		}
		
		Line lastLine = p.getLastLine();
		if(firstLine == null || lastLine == null) return false;
		if(!Proof.ASSUMPTION.equals(firstLine.i)) return false;
		if(sen.parts().size() != 2) return false;
		if(!METAOPERATOR.equals(sen.getOperator())) return false;
		Sentence leftPart = sen.parts().get(0);
		Sentence rightPart = sen.parts().get(1);
		if(leftPart == null || rightPart == null) return false;
		if(!leftPart.equals(firstLine.s)) return false;
		if(!rightPart.equals(lastLine.s)) return false;
		return true;
		
	}
}
