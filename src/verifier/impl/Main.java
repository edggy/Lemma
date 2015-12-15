package verifier.impl;

import java.util.ArrayList;
import java.util.List;

import verifier.Line;
import verifier.Sentence;

public class Main {

	public static void main(String[] args) {
		Variable a = new Variable("A");
		Variable b = new Variable("B");
		Variable[] v = new Variable[10];
		for(char c = 0; c < v.length; c++) {
			v[c] =  new Variable("" + (char) (c + 'C'));
		}
		Operator ifop = new Operator("if");
		Operator andop = new Operator("and");
		Operator orop = new Operator("or");
		Operator notop = new Operator("not");
		List<Sentence> parts = new ArrayList<Sentence>();
		parts.add(a);
		parts.add(b);
		TreeSentence sen1 = new TreeSentence(parts, ifop);
		TreeSentence[] sens = new TreeSentence[10];
		parts = new ArrayList<Sentence>();
		parts.add(a);
		parts.add(sen1);
		Inference assume = new Inference("Assumption", null, a);
		Inference mp = new Inference("MP", parts, b);
		parts.clear();
		parts.add(a);
		parts.add(b);
		TreeSentence a_or_b = new TreeSentence(parts, orop);
		parts.clear();
		parts.add(a);
		Inference oi = new Inference("Or Intro", parts, a_or_b);
		
		
		parts.clear();
		parts.add(a);
		Sentence not_a = new TreeSentence(parts, notop);
		parts.add(not_a);
		Inference ci = new Inference("Contradiction Intro", parts, new  Variable.Contradiction());
		parts.clear();
		parts.add(a);
		parts.add(new Variable.Contradiction());
		Sentence a_leads_contra = new TreeSentence(parts, MetaInference.METAOPERATOR);
		parts.clear();
		parts.add(a_leads_contra);
		Inference ni = new Inference("Not Intro", parts, not_a);
		
		parts.clear();
		parts.add(not_a);
		Sentence not_not_a = new TreeSentence(parts, notop);
		parts.clear();
		parts.add(not_not_a);
		Inference ne = new Inference("Not Elim", parts, a);
		System.out.println(sen1);
		System.out.println(mp);
		System.out.println(sen1.clone());
		parts = new ArrayList<Sentence>();
		parts.add(v[0]);
		parts.add(v[1]);
		sens[0] = new TreeSentence(parts, ifop);
		sens[1] = new TreeSentence(parts, andop);
		parts = new ArrayList<Sentence>();
		parts.add(sens[0]);
		parts.add(sens[1]);
		sens[2] = new TreeSentence(parts, ifop);
		System.out.println(sens[2].clone());
		System.out.println("\n\n");
		//System.out.println(sen1.canMapInto(sens[2]));
		System.out.println(sen1.mapInto(sens[2]));
		
		//System.out.println(sens[2].canMapInto(sen1));
		System.out.println(sens[2].mapInto(sen1));
		verifier.Reference ref = new Reference();
		Line line = new Line();
		line.s = sens[0];
		ref.addReference(line);
		line = new Line();
		line.s = sens[2];
		ref.addReference(line);
		System.out.println(sens[0]);
		System.out.println(sens[2]);
		System.out.println("-------------");
		System.out.println(sens[1]);
		System.out.println(mp.isValid(sens[1], ref));
		Proof p = new Proof();
		p.addLines(3);
		p.addSentence(0, sens[0]);
		p.addSentence(1, sens[2]);
		p.addSentence(2, sens[1]);
		p.addInference(0, assume);
		p.addInference(1, assume);
		p.addInference(2, mp);
		ref = new Reference();
		ref.addReference(p.getLine(0));
		ref.addReference(p.getLine(1));
		p.addReference(2, ref);
		System.out.println();
		System.out.println(p);
		System.out.println(p.isValid());
		System.out.println("\n\n");
		
		parts.clear();
		parts.add(a);
		parts.add(not_a);
		Sentence a_or_not_a = new TreeSentence(parts, orop);
		parts.clear();
		parts.add(a_or_not_a);
		Sentence not_a_or_not_a = new TreeSentence(parts, notop);
		Proof ex_mid = new Proof();
		ex_mid.addLines(3);
		Proof ex_mid_1 = new Proof();
		ex_mid_1.addLines(5);
		Proof ex_mid_2 = new Proof();
		ex_mid_2.addLines(4);
		ex_mid_2.addSentence(0, a);
		ex_mid_2.addInference(0, Proof.ASSUMPTION);
		ex_mid_2.addSentence(1, not_a_or_not_a);
		ex_mid_2.addInference(1, Proof.ASSUMPTION);
		ex_mid_2.addSentence(2, a_or_not_a);
		ex_mid_2.addInference(2, oi);
		ex_mid_2.addReference(2, 0);
		ex_mid_2.addSentence(3, new Variable.Contradiction());
		ex_mid_2.addInference(3, ci);
		ex_mid_2.addReference(3, 1);
		ex_mid_2.addReference(3, 2);
		System.out.println(ex_mid_2);
		System.out.println(ex_mid_2.isValid()<0?"Invalid":"Valid");
		
		ex_mid_1.addSentence(0, not_a_or_not_a);
		ex_mid_1.addInference(0, Proof.ASSUMPTION);
		
		
		ex_mid_1.addSentence(1, a_leads_contra);
		Lemma ex_mid_sub_2_lem = new Lemma("Ex Mid sub 2", ex_mid_2);
		MetaInference ex_mid_sub_2 = new MetaInference("Ex Mid sub 2", ex_mid_sub_2_lem);
		ex_mid_1.addInference(1, ex_mid_sub_2);
		ex_mid_1.addReference(1, 0);
		ex_mid_1.addSentence(2, not_a);
		ex_mid_1.addInference(2, ni);
		ex_mid_1.addReference(2, 1);
		ex_mid_1.addSentence(3, a_or_not_a);
		ex_mid_1.addInference(3, oi);
		ex_mid_1.addReference(3, 2);
		ex_mid_1.addSentence(4, new Variable.Contradiction());
		ex_mid_1.addInference(4, ci);
		ex_mid_1.addReference(4, 0);
		ex_mid_1.addReference(4, 3);
		System.out.println(ex_mid_1);
		System.out.println(ex_mid_1.isValid()<0?"Invalid":"Valid");
		
		parts.clear();
		parts.add(not_a_or_not_a);
		parts.add(new Variable.Contradiction());
		Sentence not_a_or_not_a_leads_contra = new TreeSentence(parts, MetaInference.METAOPERATOR);
		ex_mid.addSentence(0, not_a_or_not_a_leads_contra);
		Lemma ex_mid_sub_1_lem = new Lemma("Ex Mid sub1", ex_mid_1);
		MetaInference ex_mid_sub_1 = new MetaInference("Ex Mid sub1", ex_mid_sub_1_lem);
		
		ex_mid.addInference(0, ex_mid_sub_1);
		parts.clear();
		parts.add(not_a_or_not_a);
		Sentence not_not_a_or_not_a = new TreeSentence(parts, notop);
		ex_mid.addSentence(1, not_not_a_or_not_a);
		ex_mid.addInference(1, ni);
		ex_mid.addReference(1, 0);
		//ne
		ex_mid.addSentence(2, a_or_not_a);
		ex_mid.addInference(2, ne);
		ex_mid.addReference(2, 1);
		int val = ex_mid.isValid();
		System.out.println(ex_mid);
		System.out.println(val<0?"Invalid: " + val:"Valid");
		//ex_mid.addSentence(line_num, sen);
	}

}
