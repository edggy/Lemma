package verifier.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import verifier.Sentence;
import verifier.Variable;

public class Util {
	public static Map<verifier.Variable, Sentence> mapMerge(Map<Variable, Sentence> a, Map<Variable, Sentence> b) {
		if(a == null) return b;
		if(b == null) return a;
		Map<verifier.Variable, Sentence> merge = new HashMap<verifier.Variable, Sentence>();
		//For each entry in a
		for(Entry<verifier.Variable, Sentence> e : a.entrySet()) {
			//If b contains it
			if(b.containsKey(e.getKey())) {
				//And they are not equal
				if(!a.get(e.getKey()).equals(b.get(e.getKey()))) {
					//No mapping found
					return null;
				}
			}
			//Put it in the map
			merge.put(e.getKey(), e.getValue());
		}
		//For each entry in b
		for(Entry<verifier.Variable, Sentence> e : b.entrySet()) {
			//If it is not in a (ignore repeats)
			if(!a.containsKey(e.getKey())) {
				//Put it in the map
				merge.put(e.getKey(), e.getValue());
			}
		}
		return merge;
	}
}
