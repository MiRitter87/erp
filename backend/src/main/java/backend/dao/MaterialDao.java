package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.Material;

/**
 * Interface for material persistence.
 * 
 * @author Michael
 */
public interface MaterialDao {
	/**
	 * Inserts a material.
	 * 
	 * @param material The material to be inserted.
	 * @throws Exception Insertion failed.
	 */
	void insertMaterial(final Material material) throws Exception;
	
	
	/**
	 * Deletes a material.
	 * 
	 * @param material The material to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deleteMaterial(final Material material) throws Exception;
	
	
	/**
	 * Gets all materials.
	 * 
	 * @return All materials.
	 * @throws Exception Material retrieval failed.
	 */
	List<Material> getMaterials() throws Exception;
	
	
	/**
	 * Gets the material with the given id.
	 * 
	 * @param id The id of the material.
	 * @return The material with the given id.
	 * @throws Exception Material retrieval failed.
	 */
	Material getMaterial(final Integer id) throws Exception;
	
	
	/**
	 * Updates the given material.
	 * 
	 * @param material The material to be updated.
	 * @throws ObjectUnchangedException The material has not been changed.
	 * @throws Exception Material update failed.
	 */
	void updateMaterial(final Material material) throws ObjectUnchangedException, Exception;
}
