package backend.model;

/**
 * Status of a sales order.
 * 
 * @author Michael
 */
public enum SalesOrderStatus {
	/**
	 * The sales order has been created but not yet processed.
	 */
	OPEN,
	
	/**
	 * The sales order is currently being processed.
	 */
	IN_PROCESS,
	
	/**
	 * The sales order has been fully processed.
	 */
	FINISHED,
	
	/**
	 * The sales order has been canceled.
	 */
	CANCELED
}
