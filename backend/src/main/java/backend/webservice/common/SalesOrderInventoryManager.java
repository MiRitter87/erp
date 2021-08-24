package backend.webservice.common;

import java.util.HashMap;
import java.util.Map;

import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.model.Material;
import backend.model.SalesOrder;
import backend.model.SalesOrderItem;
import backend.model.SalesOrderStatus;

/**
 * Manages the material inventory handling of the sales order service.
 * 
 * Creating, updating and deleting of sales orders can influence the material inventory.
 * This manager increases or reduces the material inventory based on the ordered material quantities.
 * 
 * @author Michael
 */
public class SalesOrderInventoryManager {
	/**
	 * Reduces the inventory of the materials that are ordered.
	 * 
	 * @param salesOrder The sales order whose material inventories have to be reduced.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	public void reduceMaterialInventory(final SalesOrder salesOrder) throws Exception {
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		Material currentMaterial;
		
		for(SalesOrderItem item:salesOrder.getItems()) {
			currentMaterial = item.getMaterial();
			currentMaterial.setInventory(currentMaterial.getInventory() - item.getQuantity());
			materialDAO.updateMaterial(currentMaterial);
		}			
	}
	
	
	/**
	 * Adds the material quantities of the whole order to the material inventory.
	 * 
	 * @param salesOrder The sales order of which the material quantities are added to the inventory.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	public void  addMaterialInventoryForOrder(final SalesOrder salesOrder) throws Exception {
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		Material currentMaterial;
		
		for(SalesOrderItem item:salesOrder.getItems()) {
			currentMaterial = item.getMaterial();
			currentMaterial.setInventory(currentMaterial.getInventory() + item.getQuantity());
			materialDAO.updateMaterial(currentMaterial);
		}			
	}
	
	
	/**
	 * Updates the inventory of the materials when the order is being updated.
	 * 
	 * @param salesOrder The sales order being updated.
	 * @param databaseSalesOrder The database state of the sales order before the update has been performed.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	public void updateMaterialInventory(final SalesOrder salesOrder, final SalesOrder databaseSalesOrder) throws Exception {
		//If the sales order status changes to "Canceled", the ordered quantities are added back to the inventory.
		if(databaseSalesOrder.getStatus() != SalesOrderStatus.CANCELED && salesOrder.getStatus() == SalesOrderStatus.CANCELED) {
			this.addMaterialInventoryForOrder(salesOrder);
			return;
		}
		
		this.updateMaterialInventoryForItems(salesOrder, databaseSalesOrder);
	}
	
	
	/**
	 * Updates the material inventories according to the changes of the order items.
	 * To determine the changes, the database state is compared with the order to be updated.
	 * 
	 * @throws Exception In case the update of the material inventory fails.
	 */
	private void updateMaterialInventoryForItems(final SalesOrder salesOrder, final SalesOrder databaseSalesOrder) throws Exception {
		HashMap<Integer, Long> additions = this.getMaterialAdditions(salesOrder, databaseSalesOrder);
		HashMap<Integer, Long> reductions = this.getMaterialReductions(salesOrder, databaseSalesOrder);
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		Material currentMaterial;
		
		//Reduce the material inventory by the additionally ordered quantities.
		for (Map.Entry<Integer, Long> entry : additions.entrySet()) {
			currentMaterial = materialDAO.getMaterial(entry.getKey());
			currentMaterial.setInventory(currentMaterial.getInventory() - entry.getValue());
			materialDAO.updateMaterial(currentMaterial);
		}
		
		//Increase the material inventory by the reduced ordered quantities.
		for (Map.Entry<Integer, Long> entry : reductions.entrySet()) {
			currentMaterial = materialDAO.getMaterial(entry.getKey());
			currentMaterial.setInventory(currentMaterial.getInventory() + entry.getValue());
			materialDAO.updateMaterial(currentMaterial);
		}
	}
	
	
	/**
	 * Compares the database state of the sales order with the given sales order. All material additions of the sales order are returned.
	 * 
	 * @param salesOrder The sales order that is being checked for additions.
	 * @param databaseSalesOrder The database version of the sales order.
	 * @return The IDs and quantities of all materials that have their quantities increased compared to the database state.
	 */
	private HashMap<Integer, Long> getMaterialAdditions(final SalesOrder salesOrder, final SalesOrder databaseSalesOrder) {
		HashMap<Integer, Long> additions = new HashMap<Integer, Long>();	//<MaterialId, Quantity>
		boolean materialExistsInDatabaseItem;
		Long databaseItemQuantity;
		
		for(SalesOrderItem tempOrderItem:salesOrder.getItems()) {
			materialExistsInDatabaseItem = false;
			databaseItemQuantity = Long.valueOf(0);
			
			for(SalesOrderItem tempDatabaseItem:databaseSalesOrder.getItems()) {
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
	 * Compares the database state of the sales order with the given sales order. All material reductions of the sales order are returned.
	 * 
	 * @param salesOrder The sales order that is being checked for reductions.
	 * @param databaseSalesOrder The database version of the sales order.
	 * @return The IDs and quantities of all materials that have their quantities reduced compared to the database state.
	 */
	private HashMap<Integer, Long> getMaterialReductions(final SalesOrder salesOrder, final SalesOrder databaseSalesOrder) {
		HashMap<Integer, Long> reductions = new HashMap<Integer, Long>();	//<MaterialId, Quantity>
		boolean materialExistsInOrderItem;
		Long orderItemQuantity;
		
		for(SalesOrderItem tempDatabaseItem:databaseSalesOrder.getItems()) {
			materialExistsInOrderItem = false;
			orderItemQuantity = Long.valueOf(0);
			
			for(SalesOrderItem tempOrderItem:salesOrder.getItems()) {
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
