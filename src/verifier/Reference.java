/**
 * 
 */
package verifier;

import java.io.Serializable;
import java.util.Collection;

/**
 * <b>Reference</b><br>
 * A Collection of Sentences used to validate the Inference
 * @author Ethan Gertler
 *
 */
public interface Reference extends Cloneable, Serializable {
	public Collection<Line> getReference();
	public void addReference(Line ref);
	public void addReferences(Collection<Line> refs);
	public void removeReference(Line ref);
	public void removeReferences(Collection<Line> refs);
	public Reference clone();
}
