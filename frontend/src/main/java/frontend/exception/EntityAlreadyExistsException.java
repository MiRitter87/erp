package frontend.exception;

/**
 * Exception that occurs when an entity already exists within a data structure.<br>
 * This exception might be thrown if someone tries to add an entity to a list which already contains an entity with the
 * same primary key.
 *
 * @author Michael
 */
public class EntityAlreadyExistsException extends Exception {

    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 7009910290626516846L;

    /**
     * Default constructor.
     */
    public EntityAlreadyExistsException() {

    }
}
