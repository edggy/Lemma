package verifier;

/**
 * <b>Variable</b><br>
 * The simplest valid Sentence containing just 
 * one proposition with no Operator
 * @author Ethan Gertler
 *
 */
public interface Variable extends Sentence {
	public static class GenericVariable extends verifier.impl.Variable {
		private static final long serialVersionUID = 7086529640074535416L;

		public boolean equals(Object o) {
			if(!(o instanceof Variable)) return false;
			return true;
		}
		
		@Override
		public int hashCode() {
			return 0;
		}
	}
	
	/**
	 * <b>name</b><br>
	 * @return A String representing the name of this Variable
	 */
	public String name();
}
