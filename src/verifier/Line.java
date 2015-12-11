package verifier;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Line implements Cloneable, Externalizable {
	public Sentence s;
	public Inference i;
	public Reference r;
	public int number;
	public Line() {}
	public Line(Sentence s, Inference i, Reference r) {
		this.s = s;
		this.i = i;
		this.r = r;
	}
	
	@Override
	public Line clone() {
		Line copy = new Line();
		if(s != null) copy.s = this.s.clone();
		if(i != null) copy.i = this.i.clone();
		if(r != null) copy.r = this.r.clone();
		return copy;
	}
	
	@Override
	public int hashCode() {
		return (s!=null?s.hashCode()^2:0) + (i!=null?i.hashCode()^2:0) + (r!=null?r.hashCode()^2:0) % Integer.MAX_VALUE;
	}
	
	@Override
	public String toString() {
		return number + "\t" + s + "\t" + (i!=null?i.name():"") + "\t" + (i!=null&&r!=null?r:"");
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
