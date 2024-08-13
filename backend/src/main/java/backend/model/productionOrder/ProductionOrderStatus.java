package backend.model.productionOrder;

/**
 * Status of a production order.
 *
 * @author Michael
 */
public enum ProductionOrderStatus {
    /**
     * The production order has been created but not yet processed.
     */
    OPEN,

    /**
     * The production order is currently being processed.
     */
    IN_PROCESS,

    /**
     * The production order has been fully processed.
     */
    FINISHED,

    /**
     * The production order has been canceled.
     */
    CANCELED
}
