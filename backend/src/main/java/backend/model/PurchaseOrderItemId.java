package backend.model;

import java.io.Serializable;

/**
 * The composite primary key of the PurchaseOrderItem.
 * 
 * @author Michael
 */
public class PurchaseOrderItemId implements Serializable {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -2653241217316504752L;

	/**
	 * The purchase order.
	 */
	public PurchaseOrder purchaseOrder;
	
	/**
	 * Purchase order item id.
	 */
	public Integer id;
}
