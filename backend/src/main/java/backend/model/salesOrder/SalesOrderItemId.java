package backend.model.salesOrder;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(id, salesOrder);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SalesOrderItemId other = (SalesOrderItemId) obj;
        return Objects.equals(id, other.id) && Objects.equals(salesOrder, other.salesOrder);
    }
}
