package backend.webservice.common;

import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.model.Material;
import backend.model.PurchaseOrder;
import backend.model.PurchaseOrderItem;
import backend.model.PurchaseOrderStatus;

/**
 * Manages the material inventory handling of the purchase order service.
 * 
 * Updating and deleting of purchase orders can influence the material inventory.
 * This manager increases or reduces the material inventory based on the ordered material quantities.
 * 
 * @author Michael
 */
public class PurchaseOrderInventoryManager {
	/**
	 * Updates the inventory of the materials when the purchase order is being updated.
	 * 
	 * @param purchaseOrder The purchase order being updated.
	 * @param databasePurchaseOrder The database state of the purchase order before the update has been performed.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	public void updateMaterialInventory(final PurchaseOrder purchaseOrder, final PurchaseOrder databasePurchaseOrder) throws Exception {
		//If the GOODS_RECEIPT status changes from inactive to active, the ordered materials are added to the inventory.
		if(!databasePurchaseOrder.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT) && purchaseOrder.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT)) {
			this.addMaterialInventoryForOrder(purchaseOrder);
			return;
		}
		
		//If the GOODS_RECEIPT status changes from active to inactive, the ordered materials are removed from the inventory.
		if(databasePurchaseOrder.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT) && !purchaseOrder.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT)) {
			this.reduceMaterialInventoryForOrder(purchaseOrder);
			return;
		}
		
		//If the status GOODS_RECEIPT was already active and the status CANCELED changes from inactive to active, 
		//the ordered materials are removed from the inventory.
		if(databasePurchaseOrder.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT) && !databasePurchaseOrder.isStatusActive(PurchaseOrderStatus.CANCELED) &&
				purchaseOrder.isStatusActive(PurchaseOrderStatus.CANCELED)) {
			
			this.reduceMaterialInventoryForOrder(purchaseOrder);
			return;
		}		
		
		//If the status GOODS_RECEIPT was already active and the status CANCELED changes from active to inactive,
		//the ordered materials are added to the inventory.
	}
	
	
	/**
	 * Adds the material quantities of the whole purchase order to the material inventory.
	 * 
	 * @param purchaseOrder The purchase order of which the material quantities are added to the inventory.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	private void  addMaterialInventoryForOrder(final PurchaseOrder purchaseOrder) throws Exception {
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		Material currentMaterial;
		
		for(PurchaseOrderItem item:purchaseOrder.getItems()) {
			currentMaterial = item.getMaterial();
			currentMaterial.setInventory(currentMaterial.getInventory() + item.getQuantity());
			materialDAO.updateMaterial(currentMaterial);
		}			
	}
	
	
	/**
	 * Reduces the inventory of the materials that are ordered.
	 * 
	 * @param purchaseOrder The purchase order whose material inventories have to be reduced.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	private void reduceMaterialInventoryForOrder(final PurchaseOrder purchaseOrder) throws Exception {
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		Material currentMaterial;
		
		for(PurchaseOrderItem item:purchaseOrder.getItems()) {
			currentMaterial = item.getMaterial();
			currentMaterial.setInventory(currentMaterial.getInventory() - item.getQuantity());
			materialDAO.updateMaterial(currentMaterial);
		}			
	}
}
