package backend.model.salesOrder;

import java.math.BigDecimal;

/**
 * A lean version of a sales order item that is used by the WebService to transfer object data.
 * The main difference to the regular SalesOrderItem is that IDs are used instead of object references.
 * 
 * @author Michael
 */
public class SalesOrderItemWS {
	/**
	 * The ID of the item.
	 */
	private Integer itemId;
	
	/**
	 * The ID of the material that is being ordered.
	 */
	private Integer materialId;
	
	/**
	 * The quantity that is being ordered.
	 */
	private Long quantity;
	
	/**
	 * The total price of the sales order item at the time of order issuance.
	 */
	private BigDecimal priceTotal;

	
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

	
	/**
	 * @return the priceTotal
	 */
	public BigDecimal getPriceTotal() {
		return priceTotal;
	}

	
	/**
	 * @param priceTotal the priceTotal to set
	 */
	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}
}
