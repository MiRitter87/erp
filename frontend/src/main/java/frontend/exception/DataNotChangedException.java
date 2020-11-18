package frontend.exception;

/**
 * Exception indicating that data have not been changed.<br>
 * This exception might be thrown if someone tries to save data that have not been edited.
 * 
 * @author Michael
 */
public class DataNotChangedException extends Exception {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -4314766723434661769L;

	/**
	 * Default constructor.
	 */
	public DataNotChangedException() {
		
	}
}
