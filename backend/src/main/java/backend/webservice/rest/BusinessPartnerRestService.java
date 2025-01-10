package backend.webservice.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import backend.model.businessPartner.BPTypeQueryParameter;
import backend.model.businessPartner.BusinessPartner;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.BusinessPartnerService;

/**
 * WebService for business partner access using REST technology.
 *
 * @author Michael
 */
@Path("/businessPartners")
public class BusinessPartnerRestService {
    /**
     * Provides the business partner with the given ID.
     *
     * @param id The ID of the business partner.
     * @return The business partner with the given ID.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getMaterial(@PathParam("id") final Integer id) {
        BusinessPartnerService businessPartnerService = new BusinessPartnerService();
        return businessPartnerService.getBusinessPartner(id);
    }

    /**
     * Provides a list of all business partners.
     *
     * @param bpTypeQuery The type of the business partners to be queried.
     * @return A list of all business partners.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getBusinessPartners(@QueryParam("bpTypeQuery") final BPTypeQueryParameter bpTypeQuery) {
        BusinessPartnerService businessPartnerService = new BusinessPartnerService();
        return businessPartnerService.getBusinessPartners(bpTypeQuery);
    }

    /**
     * Adds a business partner.
     *
     * @param businessPartner The business partner to be added.
     * @return The result of the add function.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult addBusinessPartner(final BusinessPartner businessPartner) {
        BusinessPartnerService businessPartnerService = new BusinessPartnerService();
        return businessPartnerService.addBusinessPartner(businessPartner);
    }

    /**
     * Updates an existing business partner.
     *
     * @param businessPartner The business partner to be updated.
     * @return The result of the update function.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult updateBusinessPartner(final BusinessPartner businessPartner) {
        BusinessPartnerService businessPartnerService = new BusinessPartnerService();
        return businessPartnerService.updateBusinessPartner(businessPartner);
    }

    /**
     * Deletes the business partner with the given id.
     *
     * @param id The id of the business partner to be deleted.
     * @return The result of the delete function.
     */
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult deleteBusinessPartner(@PathParam("id") final Integer id) {
        BusinessPartnerService businessPartnerService = new BusinessPartnerService();
        return businessPartnerService.deleteBusinessPartner(id);
    }
}
