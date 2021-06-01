package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.SalesOrder;
import backend.model.SalesOrderStatus;

/**
 * Interface for sales order persistence.
 * 
 * @author Michael
 */
public interface SalesOrderDao {
	/**
	 * Inserts a sales order.
	 * 
	 * @param salesOrder The sales order to be inserted.
	 * @throws Exception Insertion failed.
	 */
	void insertSalesOrder(final SalesOrder salesOrder) throws Exception;
	
	
	/**
	 * Deletes a sales order.
	 * 
	 * @param salesOrder The sales order to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deleteSalesOrder(final SalesOrder salesOrder) throws Exception;
	
	
	/**
	 * Gets all sales orders.
	 * 
	 * @param orderStatusQuery Specifies the sales orders to be selected based on the status.
	 * @return All sales orders.
	 * @throws Exception Sales order retrieval failed.
	 */
	List<SalesOrder> getSalesOrders(final SalesOrderStatus orderStatusQuery) throws Exception;
	
	
	/**
	 * Gets the sales order with the given id.
	 * 
	 * @param id The id of the sales order.
	 * @return The sales order with the given id.
	 * @throws Exception Sales order retrieval failed.
	 */
	SalesOrder getSalesOrder(final Integer id) throws Exception;
	

	/**
	 * Updates the given sales order.
	 * 
	 * @param salesOrder The sales order to be updated.
	 * @throws ObjectUnchangedException The sales order has not been changed.
	 * @throws Exception Sales order update failed.
	 */
	void updateSalesOrder(final SalesOrder salesOrder) throws ObjectUnchangedException, Exception;
}
