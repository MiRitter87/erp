package backend.model.productionOrder;

import java.io.Serializable;

/**
 * The composite primary key of the ProductionOrderItem.
 * 
 * @author Michael
 */
public class ProductionOrderItemId implements Serializable {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -5757825128896479184L;

	/**
	 * The production order.
	 */
	public ProductionOrder productionOrder;
	
	/**
	 * The production order item id.
	 */
	public Integer id;
}
