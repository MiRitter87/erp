package backend.exception;

/**
 * Exception that indicates that the entity already exists.
 *
 * @author Michael
 */
public class EntityExistsException extends Exception {
    /**
     * Serialization ID.
     */
    private static final long serialVersionUID = 2616623283632757878L;

    /**
     * The identifier of the entity that already exists.
     */
    private Integer id;

    /**
     * Constructor.
     *
     * @param id The identifier of the existing entity.
     */
    public EntityExistsException(final Integer id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }
}
