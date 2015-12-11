package verifier.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import verifier.Line;
import verifier.Sentence;
import verifier.Variable;

public class Inference implements verifier.Inference {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2701842258408590458L;
	protected List<verifier.Sentence> premises;
	protected verifier.Sentence conclusion;
	protected String name;
	
	public Inference() {
		name = null;
		premises = new LinkedList<verifier.Sentence>();
		conclusion = null;
	}
	
	public Inference(String name, List<verifier.Sentence> premises, verifier.Sentence conclusion) {
		this.name = name;
		this.premises = new LinkedList<verifier.Sentence>();
		if(premises != null) {
			for(Sentence sen : premises) {
				this.premises.add(sen.clone());
			}
		}
		if(conclusion != null) this.conclusion = conclusion.clone();
	}
	
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public boolean isValid(Sentence sen, verifier.Reference ref) {
		//List<Map<Sentence, Sentence>> feasibleMaps = new LinkedList<Map<Sentence, Sentence>>();
		if(conclusion == null) return true;
		Map<verifier.Variable, Sentence> conclusionMap = conclusion.mapInto(sen);
		if(conclusionMap == null) return false;
		
		List<verifier.Sentence> senList = new LinkedList<verifier.Sentence>();
		if(ref != null) {
			for(Line l : ref.getReference()) {
				senList.add(l.s);
			}
		}
		
		//For each premise we need it to match at least one reference
		Map<Variable, Sentence> map = makeMapping(conclusionMap, premises, senList);
		//System.out.println(map);
		return (map != null);
	}
	
	public String toString() {
		String ret = name;
		ret += ":\n";
		for(Sentence p : premises) {
			ret += p + "\n";
		}
		ret += "-------------\n" + conclusion;
		return ret;
	}
	
	protected Map<verifier.Variable, Sentence> makeMapping(Map<verifier.Variable, Sentence> conclusionMap, List<verifier.Sentence> prem, List<Sentence> sens) {
		if(prem == null || prem.isEmpty()) {
			return conclusionMap;
		}
		Sentence curPrem = prem.remove(0);
		for(Sentence s : sens) {
			Map<verifier.Variable, Sentence> m = curPrem.mapInto(s);
			if(m != null) {
				Map<verifier.Variable, Sentence> merge = Util.mapMerge(conclusionMap, m);
				if(merge != null) {
					m = makeMapping(merge, prem, sens);
					if(m != null) {
						return m;
					}
				}
			}
		}
		prem.add(curPrem);
		return null;
	}
	
	public Inference clone() {
		return new Inference(name, premises, conclusion);
	}

}
