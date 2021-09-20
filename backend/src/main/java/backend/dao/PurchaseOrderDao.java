package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.purchaseOrder.PurchaseOrder;
import backend.model.purchaseOrder.PurchaseOrderStatus;

/**
 * Interface for purchase order persistence.
 * 
 * @author Michael
 */
public interface PurchaseOrderDao {
	/**
	 * Inserts a purchase order.
	 * 
	 * @param purchaseOrder The purchase order to be inserted.
	 * @throws Exception Insertion failed.
	 */
	void insertPurchaseOrder(final PurchaseOrder purchaseOrder) throws Exception;
	
	
	/**
	 * Deletes a purchase order.
	 * 
	 * @param purchaseOrder The purchase order to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deletePurchaseOrder(final PurchaseOrder purchaseOrder) throws Exception;
	
	
	/**
	 * Gets all purchase orders.
	 * 
	 * @param orderStatusQuery Specifies the purchase orders to be selected based on the status.
	 * @return All purchase orders.
	 * @throws Exception Purchase order retrieval failed.
	 */
	List<PurchaseOrder> getPurchaseOrders(final PurchaseOrderStatus orderStatusQuery) throws Exception;
	
	
	/**
	 * Gets the purchase order with the given id.
	 * 
	 * @param id The id of the purchase order.
	 * @return The purchase order with the given id.
	 * @throws Exception Purchase order retrieval failed.
	 */
	PurchaseOrder getPurchaseOrder(final Integer id) throws Exception;
	

	/**
	 * Updates the given purchase order.
	 * 
	 * @param purchaseOrder The purchase order to be updated.
	 * @throws ObjectUnchangedException The purchase order has not been changed.
	 * @throws Exception Purchase order update failed.
	 */
	void updatePurchaseOrder(final PurchaseOrder purchaseOrder) throws ObjectUnchangedException, Exception;
}
