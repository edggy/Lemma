package verifier.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import verifier.Line;

public class Proof implements verifier.Proof {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -3096088496085261678L;
	List<verifier.Line> lines;
	
	public Proof() {
		lines = new LinkedList<verifier.Line>();
	}
	
	@Override
	public void addLine() {
		lines.add(new verifier.Line());
	}
	
	@Override
	public void addLines(int amount) {
		for(int i = 0; i < amount; i++) 
			lines.add(new verifier.Line());
	}
	
	@Override
	public void insertLine(int line_num) {
		lines.add(line_num, new verifier.Line());
	}
	
	@Override
	public void insertLines(int line_num, int amount) {
		for(int i = 0; i < amount; i++) {
			insertLine(line_num);
		}
	}
	
	@Override
	public void removeLine(int line_num) {
		lines.remove(line_num);
	}
	
	@Override
	public void removeLines(int line_num, int amount) {
		for(int i = 0; i < amount; i++) {
			removeLine(line_num);
		}
	}
	
	@Override
	public void addSentence(int line_num, verifier.Sentence sen) {
		lines.get(line_num).s = sen;
	}

	@Override
	public void addSentences(int line_num, List<verifier.Sentence> sens) {
		int count = 0;
		for(verifier.Sentence sen : sens) {
			addSentence(line_num + count++, sen);
		}
	}

	@Override
	public verifier.Sentence getSentence(int line_num) {
		return lines.get(line_num).s;
	}

	@Override
	public List<verifier.Sentence> getSentences(int line_num, int amount) {
		List<verifier.Sentence> ret = new LinkedList<verifier.Sentence>();
		List<verifier.Line> sub = lines.subList(line_num, line_num + amount);
		for(verifier.Line line : sub) {
			ret.add(line.s);
		}
		return ret;
	}

	@Override
	public void removeSentence(int line_num) {
		lines.get(line_num).s = null;
	}

	@Override
	public void removeSentences(int line_num, int amount) {
		for(int i = 0; i < amount; i++) {
			removeSentence(line_num + i);
		}
	}

	@Override
	public void addInference(int line_num, verifier.Inference inf) {
		lines.get(line_num).i = inf;
		
	}

	@Override
	public verifier.Inference getInference(int line_num) {
		return lines.get(line_num).i;

	}

	@Override
	public void removeInference(int line_num) {
		lines.get(line_num).i = null;
	}

	@Override
	public void addReference(int line_num, verifier.Reference ref) {
		Line curLine = lines.get(line_num);
		if(curLine.r == null) curLine.r = ref;
		curLine.r.addReferences(ref.getReference());
	}
	
	@Override
	public void addReference(int line_num, int ref_line) {
		verifier.Reference ref = new Reference();
		ref.addReference(lines.get(ref_line));
		
		addReference(line_num, ref);
	}

	@Override
	public verifier.Reference getReference(int line_num) {
		return lines.get(line_num).r;
	}

	public void removeReference(int line_num, int ref_line) {
		verifier.Reference curRef = lines.get(line_num).r;
		if(curRef == null) return;
		curRef.removeReference(lines.get(ref_line));
	}
	
	@Override
	public void removeReferences(int line_num) {
		lines.get(line_num).r = null;
	}

	@Override
	public int isValid() {
		int line_num = 1;
		for(verifier.Line line : lines) {
			line.number = line_num++;
		}
		
		for(verifier.Line line : lines) {
			if(line.i == null) return -line.number;
			if(line.r != null) {
				for(Line l : line.r.getReference()) {
					if(l.number >= line.number) return -line.number;
				}
			}
			if(!line.i.isValid(line.s, line.r)) {
				return -line.number;
			}
			line_num++;
		}
		return 1;
	}

	@Override
	public int length() {
		return lines.size();
	}

	@Override
	public int size() {
		return lines.size();
	}

	@Override
	public verifier.Line getLine(int line_num) {
		return lines.get(line_num);
	}

	@Override
	public List<verifier.Line> getLines() {
		return new ArrayList<verifier.Line>(lines);
	}

	@Override
	public List<verifier.Line> getLines(int line_num, int amount) {
		List<verifier.Line> l = new ArrayList<verifier.Line>();
		for(int i = line_num; i < amount; i++) {
			l.add(lines.get(i));
		}
		return l;
	}
	
	@Override
	public Line getLastLine() {
		return lines.get(lines.size()-1);
	}
	
	@Override
	public String toString() {
		String res = "";
		int lineNum = 1;
		for(Line l : lines) {
			l.number = lineNum++;
			res += l + "\n";
		}
		return res.substring(0, res.length() - 1);
	}

}
