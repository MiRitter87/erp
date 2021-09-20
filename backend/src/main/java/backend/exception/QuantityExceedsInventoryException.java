package backend.exception;

import backend.model.salesOrder.SalesOrderItem;

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
	 * The sales order item where the quantity exceeds the inventory.
	 */
	private SalesOrderItem salesOrderItem;

	
	/**
	 * Default-Constructor.
	 * 
	 * @param salesOrderItem The sales order item where the quantity exceeds the inventory.
	 */
	public QuantityExceedsInventoryException(final SalesOrderItem salesOrderItem) {
		this.salesOrderItem = salesOrderItem;
	}

	
	/**
	 * @return the salesOrderItem
	 */
	public SalesOrderItem getSalesOrderItem() {
		return salesOrderItem;
	}

	
	/**
	 * @param salesOrderItem the salesOrderItem to set
	 */
	public void setSalesOrderItem(SalesOrderItem salesOrderItem) {
		this.salesOrderItem = salesOrderItem;
	}
}
