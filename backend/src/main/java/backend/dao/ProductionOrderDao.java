package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.productionOrder.ProductionOrder;
import backend.model.productionOrder.ProductionOrderStatus;

/**
 * Interface for ProductionOrder persistence.
 *
 * @author Michael
 */
public interface ProductionOrderDao {
    /**
     * Inserts a ProductionOrder.
     *
     * @param productionOrder The production order to be inserted.
     * @throws Exception Insertion failed.
     */
    void insertProductionOrder(ProductionOrder productionOrder) throws Exception;

    /**
     * Deletes a ProductionOrder.
     *
     * @param productionOrder The production order to be deleted.
     * @throws Exception Deletion failed.
     */
    void deleteProductionOrder(ProductionOrder productionOrder) throws Exception;

    /**
     * Gets all production orders.
     *
     * @param orderStatusQuery Specifies the production orders to be selected based on the status.
     * @return All production orders.
     * @throws Exception Production order retrieval failed.
     */
    List<ProductionOrder> getProductionOrders(ProductionOrderStatus orderStatusQuery) throws Exception;

    /**
     * Gets the ProductionOrder with the given id.
     *
     * @param id The id of the production order.
     * @return The production order with the given id.
     * @throws Exception Production order retrieval failed.
     */
    ProductionOrder getProductionOrder(Integer id) throws Exception;

    /**
     * Updates the given ProductionOrder.
     *
     * @param productionOrder The production order to be updated.
     * @throws ObjectUnchangedException The production order has not been changed.
     * @throws Exception                Production order update failed.
     */
    void updateProductionOrder(ProductionOrder productionOrder) throws ObjectUnchangedException, Exception;
}
