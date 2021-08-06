package backend.model;

import java.math.BigDecimal;

/**
 * An item of a purchase order representing a product in a certain quantity.
 * 
 * @author Michael
 */
public class PurchaseOrderItem {
	/**
	 * The ID.
	 */
	private Integer id;
	
	/**
	 * The PurchaseOrder to which the item belongs to.
	 */
	private PurchaseOrder purchaseOrder;
	
	/**
	 * The material that is being ordered.
	 */
	private Material material;
	
	/**
	 * The quantitiy that is being ordered.
	 */
	private Long quantity;
	
	/**
	 * The total price of the purchase order item at the time of order issuance.
	 */
	private BigDecimal priceTotal;
	
	
	/**
	 * Default constructor.
	 */
	public PurchaseOrderItem() {
		
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
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the purchaseOrder
	 */
	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}


	/**
	 * @param purchaseOrder the purchaseOrder to set
	 */
	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}


	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}


	/**
	 * @param material the material to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
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