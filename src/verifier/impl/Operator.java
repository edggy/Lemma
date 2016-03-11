package verifier.impl;

public class Operator implements verifier.Operator {
	
	String name;
	
	public Operator(String s) {
		name = s;
	}
	
	@Override
	public verifier.Operator clone() {
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof verifier.Operator)) return false;
		o = (verifier.Operator)o;
		return this.toString().equals(o.toString());
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}

}
