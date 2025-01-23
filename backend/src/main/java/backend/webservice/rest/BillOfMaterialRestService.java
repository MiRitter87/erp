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

import backend.model.billOfMaterial.BillOfMaterialWS;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.BillOfMaterialService;

/**
 * WebService for BillOfMaterial access using REST technology.
 *
 * @author Michael
 */
@Path("/billOfMaterials")
public class BillOfMaterialRestService {
    /**
     * Provides the BillOfMaterial with the given ID.
     *
     * @param id The ID of the BillOfMaterial.
     * @return The BillOfMaterial with the given ID.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getBillOfMaterial(@PathParam("id") final Integer id) {
        BillOfMaterialService billOfMaterialService = new BillOfMaterialService();
        return billOfMaterialService.getBillOfMaterial(id);
    }

    /**
     * Provides a list of all BillOfMaterials.
     *
     * @return A list of all BillOfMaterials.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getBillOfMaterials() {
        BillOfMaterialService billOfMaterialService = new BillOfMaterialService();
        return billOfMaterialService.getBillOfMaterials(null);
    }

    /**
     * Adds a BillOfMaterial.
     *
     * @param billOfMaterial The BillOfMaterial to be added.
     * @return The result of the add function.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult addBillOfMaterial(final BillOfMaterialWS billOfMaterial) {
        BillOfMaterialService billOfMaterialService = new BillOfMaterialService();
        return billOfMaterialService.addBillOfMaterial(billOfMaterial);
    }

    /**
     * Updates an existing BillOfMaterial.
     *
     * @param billOfMaterial The BillOfMaterial to be updated.
     * @return The result of the update function.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult updateBillOfMaterial(final BillOfMaterialWS billOfMaterial) {
        BillOfMaterialService billOfMaterialService = new BillOfMaterialService();
        return billOfMaterialService.updateBillOfMaterial(billOfMaterial);
    }

    /**
     * Deletes the BillOfMaterial with the given id.
     *
     * @param id The id of the BillOfMaterial to be deleted.
     * @return The result of the delete function.
     */
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult deleteBillOfMaterial(@PathParam("id") final Integer id) {
        BillOfMaterialService billOfMaterialService = new BillOfMaterialService();
        return billOfMaterialService.deleteBillOfMaterial(id);
    }
}
