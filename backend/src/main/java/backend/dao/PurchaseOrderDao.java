package backend.dao;

import backend.model.PurchaseOrder;

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
}
