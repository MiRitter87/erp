package backend.model.productionOrder;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(id, productionOrder);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductionOrderItemId other = (ProductionOrderItemId) obj;
        return Objects.equals(id, other.id) && Objects.equals(productionOrder, other.productionOrder);
    }
}
