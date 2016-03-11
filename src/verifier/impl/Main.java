package verifier.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import verifier.Line;
import verifier.Sentence;
import verifier.SentenceParser.InvalidSentenceException;

public class Main {

	public static void main(String[] args) {
		verifier.SentenceParser parse = new SentenceParser();
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
		
		//parts.clear();
		//parts.add(a);
		//Inference oi = new Inference("Or Intro", parts, a_or_b);
		
		
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
		
		parts.clear();
		try {
			parts.clear();
			parts.add(parse.parse("A"));
			parts.add(parse.parse("B"));
			Inference ai = new Inference("And Intro", parts, parse.parse("and(A, B)"));
			
			parts.clear();
			parts.add(parse.parse("and(A, B)"));
			Inference ael = new Inference("And Elim Left", parts, parse.parse("A"));
			
			parts.clear();
			parts.add(parse.parse("and(A, B)"));
			Inference aer = new Inference("And Elim Right", parts, parse.parse("B"));
			
			parts.clear();
			parts.add(parse.parse("A"));
			Inference oi = new Inference("Or Intro Left", parts, parse.parse("or(A,B)"));
			
			parts.clear();
			parts.add(parse.parse("A"));
			parts.add(parse.parse("if(A, B)"));
			Inference ife = new Inference("If Elim", parts, parse.parse("B"));
			
			parts.clear();
			parts.add(parse.parse("A"));
			parts.add(parse.parse("iff(A, B)"));
			Inference iffel = new Inference("Iff Elim Left", parts, parse.parse("B"));
			
			parts.clear();
			parts.add(parse.parse("B"));
			parts.add(parse.parse("iff(A, B)"));
			Inference iffer = new Inference("Iff Elim Right", parts, parse.parse("A"));
		
		
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
			
			
			Proof p2 = new Proof();
			p2.addLines(4);
			Sentence s1 = parse.parse("A");
			p2.addSentence(0, s1);
			p2.addInference(0, Proof.ASSUMPTION);
			Sentence s2 = parse.parse("not(or(A, not(A)))");
			p2.addSentence(1, s2);
			p2.addInference(1, Proof.ASSUMPTION);
			p2.addSentence(2, parse.parse("or(A, not(A))"));
			p2.addInference(2, oi);
			p2.addReference(2, 0);
			p2.addSentence(3, new Variable.Contradiction());
			p2.addInference(3, ci);
			p2.addReference(3, 1);
			p2.addReference(3, 2);
			System.out.println(p2);
			val = p2.isValid();
			System.out.println(val<0?"Invalid: " + val:"Valid");
			
			Inference mpocus = new Inference("MPocus", null, parse.parse("a"));
			Proof p3 = new Proof();
			p3.addLines(1);
			p3.addSentence(0, parse.parse("qwerty(cr, re(p, q), lip)"));
			p3.addInference(0, mpocus);
			System.out.println(p3);
			val = p3.isValid();
			System.out.println(val<0?"Invalid: " + val:"Valid");
			
			List<Sentence> allEprem = new LinkedList<Sentence>();
			Sentence allEpremPart = parse.parse("all(x,k)");
			
			parts.clear();
			parts.add(new Variable("x"));
			Sentence genSen = new TreeSentence(parts, new verifier.Operator.GenericOperator("f"));
			allEpremPart.replaceAll(parse.parse("k"), genSen);
			allEprem.add(allEpremPart);
			Sentence allEpremCon = genSen.clone().replaceAll(new Variable("x"), new Variable("a"));
			Inference allE = new Inference("All Elimination", allEprem, allEpremCon);
			System.out.println(allE);
			//allE.
			Proof p4 = new Proof();
			p4.addLines(3);
			p4.addSentence(0, parse.parse("all(x,or(x))"));
			p4.addInference(0, Proof.ASSUMPTION);
			p4.addSentence(1, parse.parse("or(a)"));
			p4.addInference(1, allE);
			p4.addReference(1, 0);
			p4.addSentence(2, parse.parse("or(g(x))"));
			p4.addInference(2, allE);
			p4.addReference(2, 0);
			System.out.println(p4);
			val = p4.isValid();
			System.out.println(val<0?"Invalid: " + val:"Valid");
			
			Proof l1 = new Proof(); 
			l1.addLines(5);
			
			l1.addSentence(0, parse.parse("and(R, and(C, not(F)))"));
			l1.addInference(0, Proof.ASSUMPTION);
			l1.addSentence(1, parse.parse("if(or(R, S), not(W))"));
			l1.addInference(1, Proof.ASSUMPTION);
			l1.addSentence(2, parse.parse("R"));
			l1.addInference(2, ael);
			l1.addReference(2, 0);
			l1.addSentence(3, parse.parse("or(R,S)"));
			l1.addInference(3, oi);
			l1.addReference(3, 2);
			l1.addSentence(4, parse.parse("not(W)"));
			l1.addInference(4, ife);
			l1.addReference(4, 1);
			l1.addReference(4, 3);
			
			System.out.println(l1);
			val = l1.isValid();
			System.out.println(val<0?"Invalid: " + val:"Valid");
			System.out.println();
			
			parts.clear();
			parts.add(parse.parse("None(A, B, C, D)"));
			Inference none4e1 = new Inference("None-4 Elim1", parts, parse.parse("not(A)"));
			
			verifier.Proof mc = new Proof();
			mc.addLine();
			mc.addSentence(0, parse.parse("iff(A, None(A,B,C,D))"));
			mc.addInference(0, Proof.ASSUMPTION);
			mc.addLine();
			mc.addSentence(1, parse.parse("iff(B, All(A,B,C,D))"));
			mc.addInference(1, Proof.ASSUMPTION);
			mc.addLine();
			mc.addSentence(2, parse.parse("iff(C, One(A,B,C,D))"));
			mc.addInference(2, Proof.ASSUMPTION);
			mc.addLine();
			mc.addSentence(3, parse.parse("iff(D, None(A,B,C))"));
			mc.addInference(3, Proof.ASSUMPTION);
			
			verifier.Proof mc_1 = new Proof();
			mc_1.addLine();
			mc_1.addSentence(0, parse.parse("A"));
			mc_1.addInference(0, Proof.ASSUMPTION);
			mc_1.addLine();
			mc_1.addSentence(1, mc.getSentence(0));
			mc_1.addInference(1, Proof.ASSUMPTION);
			mc_1.addLine();
			mc_1.addSentence(2, parse.parse("None(A,B,C,D)"));
			mc_1.addInference(2, iffel);
			mc_1.addReference(2, 0);
			mc_1.addReference(2, 1);
			mc_1.addLine();
			mc_1.addSentence(3, parse.parse("not(A)"));
			mc_1.addInference(3, none4e1);
			mc_1.addReference(3, 2);
			mc_1.addLine();
			mc_1.addSentence(4, new Variable.Contradiction());
			mc_1.addInference(4, ci);
			mc_1.addReference(4, 0);
			mc_1.addReference(4, 3);
			
			MetaInference mc_sub1 = new MetaInference("MC sub1", new Lemma("MC sub1", mc_1));
			
			mc.addLine();
			mc.addSentence(4, parse.parse("|-(A, !)"));
			mc.addInference(4, mc_sub1);
			mc.addReference(4, 0);
			
			System.out.println(mc_1);
			val = mc_1.isValid();
			System.out.println(val<0?"Invalid: " + val:"Valid");
			System.out.println(mc);
			val = mc.isValid();
			System.out.println(val<0?"Invalid: " + val:"Valid");
			
			
			
			
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (InvalidSentenceException e) {
			e.printStackTrace();
		}
		//ex_mid.addSentence(line_num, sen);
	}

}
