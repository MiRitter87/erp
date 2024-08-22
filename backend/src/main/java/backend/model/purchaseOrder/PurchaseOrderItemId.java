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
    private Integer purchaseOrder;

    /**
     * Purchase order item id.
     */
    private Integer id;

    /**
     * @return the purchaseOrder
     */
    public Integer getPurchaseOrder() {
        return purchaseOrder;
    }

    /**
     * @param purchaseOrder the purchaseOrder to set
     */
    public void setPurchaseOrder(final Integer purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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
     * Calculates the hashCode of a PurchaseOrderItemId.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, purchaseOrder);
    }

    /**
     * Indicates whether some other PurchaseOrderItemId is "equal to" this one.
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

        PurchaseOrderItemId other = (PurchaseOrderItemId) obj;

        return Objects.equals(id, other.id) && Objects.equals(purchaseOrder, other.purchaseOrder);
    }
}
