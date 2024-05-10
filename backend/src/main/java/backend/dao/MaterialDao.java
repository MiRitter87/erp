package backend.dao;

import java.util.List;
import java.util.Set;

import backend.exception.ObjectInUseException;
import backend.exception.ObjectUnchangedException;
import backend.model.material.Material;

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
    void insertMaterial(Material material) throws Exception;

    /**
     * Deletes a material.
     *
     * @param material The material to be deleted.
     * @throws ObjectInUseException The material is being used by another object.
     * @throws Exception            Deletion failed.
     */
    void deleteMaterial(Material material) throws ObjectInUseException, Exception;

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
    Material getMaterial(Integer id) throws Exception;

    /**
     * Updates the given material.
     *
     * @param material The material to be updated.
     * @throws ObjectUnchangedException The material has not been changed.
     * @throws Exception                Material update failed.
     */
    void updateMaterial(Material material) throws ObjectUnchangedException, Exception;

    /**
     * Gets the IDs of all images that are referenced by the materials.
     *
     * @return The IDs of all images that are referenced by the materials.
     * @throws Exception ID determination failed.
     */
    Set<Integer> getAllImageIds() throws Exception;
}
