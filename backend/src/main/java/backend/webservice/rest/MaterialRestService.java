package backend.webservice.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import backend.model.material.MaterialWS;
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
    public WebServiceResult addMaterial(final MaterialWS material) {
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
    public WebServiceResult updateMaterial(final MaterialWS material) {
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
