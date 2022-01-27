package backend.controller;

import java.util.HashMap;
import java.util.Map;

import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.model.material.Material;
import backend.model.purchaseOrder.PurchaseOrder;
import backend.model.purchaseOrder.PurchaseOrderItem;
import backend.model.purchaseOrder.PurchaseOrderStatus;

/**
 * Manages the material inventory handling of the purchase order service.
 * 
 * Updating and deleting of purchase orders can influence the material inventory.
 * This manager increases or reduces the material inventory based on the ordered material quantities.
 * 
 * @author Michael
 */
public class PurchaseOrderInventoryController {
	/**
	 * Updates the inventory of the materials when the purchase order is being updated.
	 * 
	 * @param purchaseOrder The purchase order being updated.
	 * @param databasePurchaseOrder The database state of the purchase order before the update has been performed.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	public void updateMaterialInventoryOnOrderUpdate(final PurchaseOrder purchaseOrder, final PurchaseOrder databasePurchaseOrder) throws Exception {
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
		if(databasePurchaseOrder.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT) && databasePurchaseOrder.isStatusActive(PurchaseOrderStatus.CANCELED) &&
				!purchaseOrder.isStatusActive(PurchaseOrderStatus.CANCELED)) {
			
			this.addMaterialInventoryForOrder(purchaseOrder);
			return;
		}
		
		if(purchaseOrder.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT))
			this.updateMaterialInventoryForItems(purchaseOrder, databasePurchaseOrder);
	}
	
	
	/**
	 * Updates the material inventory on deletion of a purchase order.
	 * 
	 * @param purchaseOrder The purchase order that is being deleted.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	public void updateMaterialInventoryOnOrderDeletion(final PurchaseOrder purchaseOrder) throws Exception {
		//If the GOODS_RECEIPT status is active, the ordered material quantities have to be reduced from the inventory.
		if(purchaseOrder.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT))
			this.reduceMaterialInventoryForOrder(purchaseOrder);
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
	
	
	/**
	 * Updates the material inventories according to the changes of the order items.
	 * To determine the changes, the database state is compared with the order to be updated.
	 * 
	 * @throws Exception In case the update of the material inventory fails.
	 */
	private void updateMaterialInventoryForItems(final PurchaseOrder purchaseOrder, final PurchaseOrder databasePurchaseOrder) throws Exception {
		HashMap<Integer, Long> additions = this.getMaterialAdditions(purchaseOrder, databasePurchaseOrder);
		HashMap<Integer, Long> reductions = this.getMaterialReductions(purchaseOrder, databasePurchaseOrder);
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		Material currentMaterial;
		
		//Increase the material inventory by the additionally ordered quantities.
		for (Map.Entry<Integer, Long> entry : additions.entrySet()) {
			currentMaterial = materialDAO.getMaterial(entry.getKey());
			currentMaterial.setInventory(currentMaterial.getInventory() + entry.getValue());
			materialDAO.updateMaterial(currentMaterial);
		}
		
		//Decrease the material inventory by the reduced ordered quantities.
		for (Map.Entry<Integer, Long> entry : reductions.entrySet()) {
			currentMaterial = materialDAO.getMaterial(entry.getKey());
			currentMaterial.setInventory(currentMaterial.getInventory() - entry.getValue());
			materialDAO.updateMaterial(currentMaterial);
		}
	}
	
	
	/**
	 * Compares the database state of the purchase order with the given purchase order. All material additions of the purchase order are returned.
	 * 
	 * @param purchaseOrder The purchase order that is being checked for additions.
	 * @param databasePurchaseOrder The database version of the purchase order.
	 * @return The IDs and quantities of all materials that have their quantities increased compared to the database state.
	 */
	private HashMap<Integer, Long> getMaterialAdditions(final PurchaseOrder purchaseOrder, final PurchaseOrder databasePurchaseOrder) {
		HashMap<Integer, Long> additions = new HashMap<Integer, Long>();	//<MaterialId, Quantity>
		boolean materialExistsInDatabaseItem;
		Long databaseItemQuantity;
		
		for(PurchaseOrderItem tempOrderItem:purchaseOrder.getItems()) {
			materialExistsInDatabaseItem = false;
			databaseItemQuantity = Long.valueOf(0);
			
			for(PurchaseOrderItem tempDatabaseItem:databasePurchaseOrder.getItems()) {
				if(tempDatabaseItem.getMaterial().getId().intValue() == tempOrderItem.getMaterial().getId().intValue()) {
					materialExistsInDatabaseItem = true;
					databaseItemQuantity = databaseItemQuantity + tempDatabaseItem.getQuantity();
				}
			}
			
			//Add quantities for a new material.
			if(materialExistsInDatabaseItem == false) {
				additions.put(tempOrderItem.getMaterial().getId(), tempOrderItem.getQuantity());
			}
			else {
				//Determine possible additional quantities of an existing material.
				if(tempOrderItem.getQuantity() > databaseItemQuantity)
					additions.put(tempOrderItem.getMaterial().getId(), tempOrderItem.getQuantity()-databaseItemQuantity);
			}
		}
		
		return additions;
	}
	
	
	/**
	 * Compares the database state of the purchase order with the given purchase order. All material reductions of the purchase order are returned.
	 * 
	 * @param purchaseOrder The purchase order that is being checked for reductions.
	 * @param databasePurchaseOrder The database version of the purchase order.
	 * @return The IDs and quantities of all materials that have their quantities reduced compared to the database state.
	 */
	private HashMap<Integer, Long> getMaterialReductions(final PurchaseOrder purchaseOrder, final PurchaseOrder databasePurchaseOrder) {
		HashMap<Integer, Long> reductions = new HashMap<Integer, Long>();	//<MaterialId, Quantity>
		boolean materialExistsInOrderItem;
		Long orderItemQuantity;
		
		for(PurchaseOrderItem tempDatabaseItem:databasePurchaseOrder.getItems()) {
			materialExistsInOrderItem = false;
			orderItemQuantity = Long.valueOf(0);
			
			for(PurchaseOrderItem tempOrderItem:purchaseOrder.getItems()) {
				if(tempDatabaseItem.getMaterial().getId().intValue() == tempOrderItem.getMaterial().getId().intValue()) {
					materialExistsInOrderItem = true;
					orderItemQuantity = orderItemQuantity + tempOrderItem.getQuantity();
				}
				
			}
			
			//Add quantities for a removed material.
			if(materialExistsInOrderItem == false) {
				reductions.put(tempDatabaseItem.getMaterial().getId(), tempDatabaseItem.getQuantity());
			}
			else {
				//Determine possible quantity reductions of an existing material.
				if(tempDatabaseItem.getQuantity() > orderItemQuantity)
					reductions.put(tempDatabaseItem.getMaterial().getId(), tempDatabaseItem.getQuantity()-orderItemQuantity);
			}
		}
		
		return reductions;
	}
}
