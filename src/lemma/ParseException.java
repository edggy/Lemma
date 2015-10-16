/**
 * 
 */
package lemma;

/**
 * @author edgchess
 *
 */
public class ParseException extends Exception {

	private int line;
	private int col;
	/**
	 * 
	 */
	public ParseException() {
		line = -1;
		col = -1;
	}

	/**
	 * @param arg0
	 */
	public ParseException(String arg0) {
		super(arg0);
		line = -1;
		col = -1;
	}
	
	public ParseException(String arg0, int line) {
		super(arg0);
		this.line = line;
		col = -1;
	}
	
	public ParseException(String arg0, int line, int col) {
		super(arg0);
		this.line = line;
		this.col = col;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return col;
	}

}
