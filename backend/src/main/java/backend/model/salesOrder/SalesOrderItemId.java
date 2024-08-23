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
    private Integer salesOrder;

    /**
     * Sales order item id.
     */
    private Integer id;

    /**
     * @return the salesOrder
     */
    public Integer getSalesOrder() {
        return salesOrder;
    }

    /**
     * @param salesOrder the salesOrder to set
     */
    public void setSalesOrder(final Integer salesOrder) {
        this.salesOrder = salesOrder;
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
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Calculates the hashCode of a SalesOrderItemId.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, salesOrder);
    }

    /**
     * Indicates whether some other SalesOrderItemId is "equal to" this one.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SalesOrderItemId other = (SalesOrderItemId) obj;
        return Objects.equals(id, other.id) && Objects.equals(salesOrder, other.salesOrder);
    }
}
