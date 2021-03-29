package backend.exception;

/**
 * Exception that indicates the usage of duplicative identifier keys.
 * 
 * @author Michael
 */
public class DuplicateIdentifierException extends Exception {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 5720658076502161481L;
	
	/**
	 * The duplicate identifier.
	 */
	private String duplicateIdentifier;

	
	/**
	 * Constructor.
	 * 
	 * @param duplicateIdentifier The distinct identifier that is used multiple times.
	 */
	public DuplicateIdentifierException(final String duplicateIdentifier) {
		this.duplicateIdentifier = duplicateIdentifier;
	}

	
	/**
	 * @return the duplicateIdentifier
	 */
	public String getDuplicateIdentifier() {
		return duplicateIdentifier;
	}

	
	/**
	 * @param duplicateIdentifier the duplicateIdentifier to set
	 */
	public void setDuplicateIdentifier(String duplicateIdentifier) {
		this.duplicateIdentifier = duplicateIdentifier;
	}
}
