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
    private Integer productionOrder;

    /**
     * The production order item id.
     */
    private Integer id;

    /**
     * @return the productionOrder
     */
    public Integer getProductionOrder() {
        return productionOrder;
    }

    /**
     * @param productionOrder the productionOrder to set
     */
    public void setProductionOrder(final Integer productionOrder) {
        this.productionOrder = productionOrder;
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
     * Calculates the hashCode of a ProductionOrderItemId.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, productionOrder);
    }

    /**
     * Indicates whether some other ProductionOrderItemId is "equal to" this one.
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
        ProductionOrderItemId other = (ProductionOrderItemId) obj;
        return Objects.equals(id, other.id) && Objects.equals(productionOrder, other.productionOrder);
    }
}
