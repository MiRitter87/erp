package backend.webservice.common;

import backend.model.Material;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the Material WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 */
public class MaterialService {
	/**
	 * Provides the material with the given id.
	 * 
	 * @param id The id of the material.
	 * @return The material with the given id, if found.
	 */
	public WebServiceResult getMaterial(final Integer id) {
		return new WebServiceResult();
	}
	
	
	/**
	 * Provides a list of all materials.
	 * 
	 * @return A list of all materials.
	 */
	public WebServiceResult getMaterials() {
		return new WebServiceResult();
	}
	
	
	/**
	 * Adds a material.
	 * 
	 * @param material The material to be added.
	 * @return The result of the add function.
	 */
	public WebServiceResult addMaterial(final Material material) {
		return new WebServiceResult();
	}
	
	
	/**
	 * Deletes the material with the given id.
	 * 
	 * @param id The id of the material to be deleted.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deleteMaterial(final Integer id) {
		return new WebServiceResult();
	}
	
	
	/**
	 * Updates an existing material.
	 * 
	 * @param mateiral The material to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updateMaterial(final Material mateiral) {
		return new WebServiceResult();
	}
}
