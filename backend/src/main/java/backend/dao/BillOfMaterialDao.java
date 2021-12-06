package backend.dao;

import java.util.List;

import backend.exception.EntityExistsException;
import backend.exception.ObjectUnchangedException;
import backend.model.billOfMaterial.BillOfMaterial;
import backend.model.material.Material;

/**
 * Interface for BillOfMaterial persistence.
 * 
 * @author Michael
 */
public interface BillOfMaterialDao {
	/**
	 * Inserts a BillOfMaterial.
	 * 
	 * @param billOfMaterial The BillOfMaterial to be inserted.
	 * @throws EntityExistsException Another BillOfMaterial with the same material exists.
	 * @throws Exception Insertion failed.
	 */
	void insertBillOfMaterial(final BillOfMaterial billOfMaterial) throws EntityExistsException, Exception;
	
	
	/**
	 * Deletes a BillOfMaterial.
	 * 
	 * @param billOfMaterial The BillOfMaterial to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deleteBillOfMaterial(final BillOfMaterial billOfMaterial) throws Exception;
	
	
	/**
	 * Gets all BillOfMaterials.
	 * 
	 * @param material The material on which the BillOfMaterial is based.
	 * @return All BillOfMaterials.
	 * @throws Exception BillOfMaterial retrieval failed.
	 */
	List<BillOfMaterial> getBillOfMaterials(final Material material) throws Exception;
	
	
	/**
	 * Gets the BillOfMaterial with the given id.
	 * 
	 * @param id The id of the BillOfMaterial.
	 * @return The BillOfMaterial with the given id.
	 * @throws Exception BillOfMaterial retrieval failed.
	 */
	BillOfMaterial getBillOfMaterial(final Integer id) throws Exception;
	
	
	/**
	 * Updates the given BillOfMaterial.
	 * 
	 * @param billOfMaterial The BillOfMaterial to be updated.
	 * @throws ObjectUnchangedException The BillOfMaterial has not been changed.
	 * @throws Exception BillOfMaterial update failed.
	 */
	void updateBillOfMaterial(final BillOfMaterial billOfMaterial) throws ObjectUnchangedException, Exception;
}
