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
	public String toString() {
		return name;
	}

}
