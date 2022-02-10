package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.productionOrder.ProductionOrder;
import backend.model.productionOrder.ProductionOrderStatus;

/**
 * Interface for production order persistence.
 * 
 * @author Michael
 */
public interface ProductionOrderDao {
	/**
	 * Inserts a production order.
	 * 
	 * @param productionOrder The production order to be inserted.
	 * @throws Exception Insertion failed.
	 */
	void insertProductionOrder(final ProductionOrder productionOrder) throws Exception;
	
	
	/**
	 * Deletes a production order.
	 * 
	 * @param productionOrder The production order to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deleteProductionOrder(final ProductionOrder productionOrder) throws Exception;
	
	
	/**
	 * Gets all production orders.
	 * 
	 * @param orderStatusQuery Specifies the production orders to be selected based on the status.
	 * @return All production orders.
	 * @throws Exception Production order retrieval failed.
	 */
	List<ProductionOrder> getProductionOrders(final ProductionOrderStatus orderStatusQuery) throws Exception;
	
	
	/**
	 * Gets the production order with the given id.
	 * 
	 * @param id The id of the production order.
	 * @return The production order with the given id.
	 * @throws Exception Production order retrieval failed.
	 */
	ProductionOrder getProductionOrder(final Integer id) throws Exception;
	

	/**
	 * Updates the given production order.
	 * 
	 * @param productionOrder The production order to be updated.
	 * @throws ObjectUnchangedException The production order has not been changed.
	 * @throws Exception Production order update failed.
	 */
	void updateProductionOrder(final ProductionOrder productionOrder) throws ObjectUnchangedException, Exception;
}
