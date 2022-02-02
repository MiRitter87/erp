package backend.controller;

import java.util.List;

import backend.dao.BillOfMaterialDao;
import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.model.billOfMaterial.BillOfMaterial;
import backend.model.billOfMaterial.BillOfMaterialItem;
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
			this.updateMaterialInventoryForOrder(productionOrder, true);
			this.updateMaterialInventoryForBom(productionOrder, false);
			return;
		}
		
		if(databaseProductionOrder.getStatus() == ProductionOrderStatus.FINISHED && productionOrder.getStatus() != ProductionOrderStatus.FINISHED) {
			this.updateMaterialInventoryForOrder(productionOrder, false);
			this.updateMaterialInventoryForBom(productionOrder, true);
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
	 * Updates the material inventory quantities for the whole production order.
	 * The quantities of the production order items are updated.
	 * 
	 * @param productionOrder The production order of which the material quantities are updated.
	 * @param add Add order quantities to inventory if true; remove order quantities from inventory if false.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	private void updateMaterialInventoryForOrder(final ProductionOrder productionOrder, final boolean add) throws Exception {
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		Material currentMaterial;
		
		for(ProductionOrderItem item:productionOrder.getItems()) {
			currentMaterial = item.getMaterial();
			
			if(add)
				currentMaterial.setInventory(currentMaterial.getInventory() + item.getQuantity());
			else
				currentMaterial.setInventory(currentMaterial.getInventory() - item.getQuantity());
				
			materialDAO.updateMaterial(currentMaterial);
		}			
	}
	
	
	/**
	 * Updates the inventory of the materials that are used for production.
	 * The quantities defined in the bill of material of the production order items are updated.
	 * 
	 * @param productionOrder The production order of which the material quantities are updated.
	 * @param add Add BOM quantities to inventory if true; remove BOM quantities from inventory if false.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	private void updateMaterialInventoryForBom(final ProductionOrder productionOrder, final boolean add) throws Exception {
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		BillOfMaterialDao billOfMaterialDAO = DAOManager.getInstance().getBillOfMaterialDAO();
		Material currentMaterial;
		List<BillOfMaterial> billOfMaterials;
		BillOfMaterial billOfMaterial;
		
		for(ProductionOrderItem item:productionOrder.getItems()) {
			//1. Get the bill of material of the produced order item. The bill of material defines which materials are used for production.
			billOfMaterials = billOfMaterialDAO.getBillOfMaterials(item.getMaterial());
			
			if(billOfMaterials.size() == 0)
				continue;	//Prevents null pointer errors; just in case. ProductionOrderService assures that a BOM exists when updating.
			
			billOfMaterial = billOfMaterials.get(0);
			
			//2. Update the quantities of all materials that are used to produce the production order item.
			for(BillOfMaterialItem bomItem:billOfMaterial.getItems()) {
				currentMaterial = bomItem.getMaterial();
				
				if(add)
					currentMaterial.setInventory(currentMaterial.getInventory() + bomItem.getQuantity());
				else
					currentMaterial.setInventory(currentMaterial.getInventory() - bomItem.getQuantity());
					
				materialDAO.updateMaterial(currentMaterial);
			}
		}
	}
}
