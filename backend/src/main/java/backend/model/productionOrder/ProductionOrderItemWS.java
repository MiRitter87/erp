package backend.model.productionOrder;

/**
 * A lean version of a production order item that is used by the WebService to transfer object data.
 * The main difference to the regular ProductionOrderItem is that IDs are used instead of object references.
 * 
 * @author Michael
 */
public class ProductionOrderItemWS {
	/**
	 * The ID of the item.
	 */
	private Integer itemId;
	
	/**
	 * The ID of the material that is being produced.
	 */
	private Integer materialId;
	
	/**
	 * The quantity that is being produced.
	 */
	private Long quantity;

	
	/**
	 * @return the itemId
	 */
	public Integer getItemId() {
		return itemId;
	}

	
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	
	/**
	 * @return the materialId
	 */
	public Integer getMaterialId() {
		return materialId;
	}

	
	/**
	 * @param materialId the materialId to set
	 */
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	
	/**
	 * @return the quantity
	 */
	public Long getQuantity() {
		return quantity;
	}

	
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
