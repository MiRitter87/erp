package backend.controller;

import backend.model.productionOrder.ProductionOrder;

/**
 * Manages the material inventory handling of the production order service.
 * 
 * Updating and deleting of production orders can influence the material inventory.
 * This manager increases and reduces the material inventory based on the produced material quantities.
 * 
 * @author Michael
 */
public class ProductionOrderInventoryController {
	/**
	 * Updates the inventory of the materials when the production order is being updated.
	 * 
	 * @param productionOrder The production order being updated.
	 * @param databaseProductionOrder The database state of the production order before the update has been performed.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	public void updateMaterialInventoryOnOrderUpdate(final ProductionOrder productionOrder, final ProductionOrder databaseProductionOrder) throws Exception {
		
	}
	
	
	/**
	 * Updates the material inventory on deletion of a production order.
	 * 
	 * @param productionOrder The production order that is being deleted.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	public void updateMaterialInventoryOnOrderDeletion(final ProductionOrder productionOrder) throws Exception {
		
	}
}
