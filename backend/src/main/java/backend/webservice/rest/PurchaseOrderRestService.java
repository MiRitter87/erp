package backend.webservice.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import backend.model.purchaseOrder.PurchaseOrderStatus;
import backend.model.purchaseOrder.PurchaseOrderWS;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.PurchaseOrderService;

/**
 * WebService for purchase order access using REST technology.
 *
 * @author Michael
 */
@Path("/purchaseOrders")
public class PurchaseOrderRestService {
    /**
     * Provides the purchase order with the given ID.
     *
     * @param id The ID of the purchase order.
     * @return The purchase order with the given ID.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getPurchaseOrder(@PathParam("id") final Integer id) {
        PurchaseOrderService purchaseOrderService = new PurchaseOrderService();
        return purchaseOrderService.getPurchaseOrder(id);
    }

    /**
     * Provides a list of all purchase orders.
     *
     * @param orderStatusQuery Specifies the purchase orders to be selected based on the status.
     * @return A list of all purchase orders.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getPurchaseOrders(
            @QueryParam("orderStatusQuery") final PurchaseOrderStatus orderStatusQuery) {
        PurchaseOrderService purchaseOrderService = new PurchaseOrderService();
        return purchaseOrderService.getPurchaseOrders(orderStatusQuery);
    }

    /**
     * Adds a purchase order.
     *
     * @param purchaseOrder The purchase order to be added.
     * @return The result of the add function.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult addPurchaseOrder(final PurchaseOrderWS purchaseOrder) {
        PurchaseOrderService purchaseOrderService = new PurchaseOrderService();
        return purchaseOrderService.addPurchaseOrder(purchaseOrder);
    }

    /**
     * Updates an existing purchase order.
     *
     * @param purchaseOrder The purchase order to be updated.
     * @return The result of the update function.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult updatePurchaseOrder(final PurchaseOrderWS purchaseOrder) {
        PurchaseOrderService purchaseOrderService = new PurchaseOrderService();
        return purchaseOrderService.updatePurchaseOrder(purchaseOrder);
    }

    /**
     * Deletes the purchase order with the given id.
     *
     * @param id The id of the purchase order to be deleted.
     * @return The result of the delete function.
     */
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult deletePurchaseOrder(@PathParam("id") final Integer id) {
        PurchaseOrderService purchaseOrderService = new PurchaseOrderService();
        return purchaseOrderService.deletePurchaseOrder(id);
    }
}
