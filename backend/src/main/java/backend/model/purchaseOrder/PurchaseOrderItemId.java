package backend.model.purchaseOrder;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(id, purchaseOrder);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PurchaseOrderItemId other = (PurchaseOrderItemId) obj;
        return Objects.equals(id, other.id) && Objects.equals(purchaseOrder, other.purchaseOrder);
    }
}
