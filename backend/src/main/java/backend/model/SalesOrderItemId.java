package backend.model;

import java.io.Serializable;

/**
 * The composite primary key of the SalesOrderItem.
 * 
 * @author Michael
 */
public class SalesOrderItemId implements Serializable {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -9054163610694490614L;

	/**
	 * The sales order.
	 */
	public SalesOrder salesOrder;
	
	/**
	 * Sales order item id.
	 */
	public Integer id;
}
