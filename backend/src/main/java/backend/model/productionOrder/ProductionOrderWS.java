package backend.model.productionOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A lean version of a production order that is used by the WebService to transfer object data. The main difference to
 * the regular ProductionOrder is that IDs are used instead of object references.
 *
 * @author Michael
 */
public class ProductionOrderWS {
    /**
     * The ID of the production order.
     */
    private Integer productionOrderId;

    /**
     * The order date.
     */
    private Date orderDate;

    /**
     * The planned execution date.
     */
    private Date plannedExecutionDate;

    /**
     * The actual execution date.
     */
    private Date executionDate;

    /**
     * The status of the production order.
     */
    private ProductionOrderStatus status;

    /**
     * The items that are being produced.
     */
    private List<ProductionOrderItemWS> items;

    /**
     * Constructor.
     */
    public ProductionOrderWS() {
        this.items = new ArrayList<ProductionOrderItemWS>();
    }

    /**
     * Adds the given item to the order.
     *
     * @param productionOrderItem The item to be added.
     */
    public void addItem(final ProductionOrderItemWS productionOrderItem) {
        this.items.add(productionOrderItem);
    }

    /**
     * @return the productionOrderId
     */
    public Integer getProductionOrderId() {
        return productionOrderId;
    }

    /**
     * @param productionOrderId the productionOrderId to set
     */
    public void setProductionOrderId(final Integer productionOrderId) {
        this.productionOrderId = productionOrderId;
    }

    /**
     * @return the orderDate
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the plannedExecutionDate
     */
    public Date getPlannedExecutionDate() {
        return plannedExecutionDate;
    }

    /**
     * @param plannedExecutionDate the plannedExecutionDate to set
     */
    public void setPlannedExecutionDate(final Date plannedExecutionDate) {
        this.plannedExecutionDate = plannedExecutionDate;
    }

    /**
     * @return the executionDate
     */
    public Date getExecutionDate() {
        return executionDate;
    }

    /**
     * @param executionDate the executionDate to set
     */
    public void setExecutionDate(final Date executionDate) {
        this.executionDate = executionDate;
    }

    /**
     * @return the status
     */
    public ProductionOrderStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final ProductionOrderStatus status) {
        this.status = status;
    }

    /**
     * @return the items
     */
    public List<ProductionOrderItemWS> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(final List<ProductionOrderItemWS> items) {
        this.items = items;
    }
}
