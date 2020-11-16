package backend.exception;

/**
 * Exception that indicates a mismatch of a common identifier that is shared between several objects that are linked by a one-to-one relationship.
 * 
 * @author Michael
 */
public class IdentifierMismatchException extends Exception {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 3243434586376293515L;

	/**
	 * Default constructor.
	 */
	public IdentifierMismatchException() {
		
	}
}
