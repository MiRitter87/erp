package backend.model;

/**
 * Status of a purchase order.
 * 
 * @author Michael
 */
public enum PurchaseOrderStatus {
	/**
	 *  The purchase order has been created but not yet been processed.
	 */
	OPEN,
	
	/**
	 * The purchase order has been fully processed.
	 */
	FINISHED,
	
	/**
	 * The purchase order has been canceled.
	 */
	CANCELED,
	
	/**
	 * The invoice has been received.
	 */
	INVOICE_RECEIPT,
	
	/**
	 * The goods have been received.
	 */
	GOODS_RECEIPT,
	
	/**
	 * The invoice has been paid.
	 */
	INVOICE_SETTLED
}
