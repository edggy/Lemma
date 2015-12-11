package verifier.impl;

import java.util.Collection;
import java.util.HashSet;

import verifier.Line;

public class Reference implements verifier.Reference {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7820253842073862914L;
	Collection<Line> refs;
	
	public Reference() {
		refs = new HashSet<Line>();
	}
	
	@Override
	public Collection<Line> getReference() {
		return refs;
	}

	@Override
	public void addReference(Line ref) {
		refs.add(ref);
	}

	@Override
	public void addReferences(Collection<Line> refs) {
		for(Line i : refs) {
			addReference(i);
		}
	}

	@Override
	public void removeReference(Line ref) {
		refs.remove(ref);
	}

	@Override
	public void removeReferences(Collection<Line> refs) {
		for(Line i : refs) {
			removeReference(i);
		}
	}
	@Override
	public int hashCode() {
		int hash = 0;
		for(Line l : refs) {
			hash += l.number^10;
		}
		return hash;
	}
	
	public String toString() {
		String ret = "";
		for(Line l : refs) {
			ret += l.number + ", ";
		}
		return ret.substring(0, ret.length() - 2);
	}
	
	public Reference clone() {
		Reference copy = new Reference();
		for(Line l : refs) {
			copy.refs.add(l);
		}
		return copy;
	}
	
}
