package backend.controller;

import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.model.material.Material;
import backend.model.productionOrder.ProductionOrder;
import backend.model.productionOrder.ProductionOrderItem;
import backend.model.productionOrder.ProductionOrderStatus;

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
		if(databaseProductionOrder.getStatus() != ProductionOrderStatus.FINISHED && productionOrder.getStatus() == ProductionOrderStatus.FINISHED) {
			this.addMaterialInventoryForOrder(productionOrder);
			//this.removeMaterialInventoryForBom(productionOrder);
			return;
		}
	}
	
	
	/**
	 * Updates the material inventory on deletion of a production order.
	 * 
	 * @param productionOrder The production order that is being deleted.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	public void updateMaterialInventoryOnOrderDeletion(final ProductionOrder productionOrder) throws Exception {
		
	}
	
	
	/**
	 * Adds the material quantities of the whole production order to the material inventory.
	 * 
	 * @param productionOrder The production order of which the material quantities are added to the inventory.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	private void  addMaterialInventoryForOrder(final ProductionOrder productionOrder) throws Exception {
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		Material currentMaterial;
		
		for(ProductionOrderItem item:productionOrder.getItems()) {
			currentMaterial = item.getMaterial();
			currentMaterial.setInventory(currentMaterial.getInventory() + item.getQuantity());
			materialDAO.updateMaterial(currentMaterial);
		}			
	}
}
