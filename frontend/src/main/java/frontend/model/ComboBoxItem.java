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
	 * @param id The distinct identifier of an item.
	 * @param description The description of an item.
	 */
	public ComboBoxItem(final String id, final String description) {
		this.id = id;
		this.description = description;
	}
	
	
	public String getId() {
		return id;
	}


	public String getDescription() {
		return description;
	}


	@Override
	public String toString() {
		return this.description;
	}
}
