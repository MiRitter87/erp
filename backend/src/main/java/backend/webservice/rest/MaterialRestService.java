package backend.webservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import backend.model.Material;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.MaterialService;

/**
 * WebService for material access using REST technology.
 * 
 * @author Michael
 */
@Path("/materials")
public class MaterialRestService {
	/**
	 * Provides the material with the given ID.
	 * 
	 * @param id The ID of the material.
	 * @return The material with the given ID.
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult getMaterial(@PathParam("id") final Integer id) {
		MaterialService materialService = new MaterialService();
		return materialService.getMaterial(id);
	}
	
	
	/**
	 * Provides a list of all materials.
	 * 
	 * @return A list of all materials.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult getMaterials() {
		MaterialService materialService = new MaterialService();
		return materialService.getMaterials();
	}
	
	
	/**
	 * Adds a material.
	 * 
	 * @param material The material to be added.
	 * @return The result of the add function.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult addMaterial(final Material material) {
		MaterialService materialService = new MaterialService();
		return materialService.addMaterial(material);
	}
	
	
	/**
	 * Updates an existing material.
	 * 
	 * @param material The material to be updated.
	 * @return The result of the update function.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult updateMaterial(final Material material) {
		MaterialService materialService = new MaterialService();
		return materialService.updateMaterial(material);
	}
	
	
	/**
	 * Deletes the material with the given id.
	 * 
	 * @param id The id of the material to be deleted.
	 * @return The result of the delete function.
	 */
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult deleteMaterial(@PathParam("id") final Integer id) {
		MaterialService materialService = new MaterialService();
		return materialService.deleteMaterial(id);
	}
}
