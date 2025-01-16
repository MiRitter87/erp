package frontend.model;

/**
 * A ComboBox Item represented by the ID of an element as well as a description that can be displayed.
 *
 * @author Michael
 */
public class ComboBoxItem {
    /**
     * The distinct ID of an item.
     */
    private String id;

    /**
     * The description of an item.
     */
    private String description;

    /**
     * Initializes the Item.
     *
     * @param id          The distinct identifier of an item.
     * @param description The description of an item.
     */
    public ComboBoxItem(final String id, final String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return this.description;
    }
}
