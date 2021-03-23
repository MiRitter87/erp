package backend.exception;

/**
 * Exception that indicates that the ordered quantity exceeds the inventory of the material.
 * 
 * @author Michael
 */
public class QuantityExceedsInventoryException extends Exception {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 4424543144783433188L;

	/**
	 * Default-Constructor.
	 */
	public QuantityExceedsInventoryException() {
		
	}
}
