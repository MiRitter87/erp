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

import backend.model.productionOrder.ProductionOrderStatus;
import backend.model.productionOrder.ProductionOrderWS;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.ProductionOrderService;

/**
 * WebService for production order access using REST technology.
 *
 * @author Michael
 */
@Path("/productionOrders")
public class ProductionOrderRestService {
    /**
     * Provides the production order with the given ID.
     *
     * @param id The ID of the production order.
     * @return The production order with the given ID.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getProductionOrder(@PathParam("id") final Integer id) {
        ProductionOrderService productionOrderService = new ProductionOrderService();
        return productionOrderService.getProductionOrder(id);
    }

    /**
     * Provides a list of all production orders.
     *
     * @param orderStatusQuery Specifies the production orders to be selected based on the status.
     * @return A list of all production orders.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getProductionOrders(
            @QueryParam("orderStatusQuery") final ProductionOrderStatus orderStatusQuery) {
        ProductionOrderService productionOrderService = new ProductionOrderService();
        return productionOrderService.getProductionOrders(orderStatusQuery);
    }

    /**
     * Adds a production order.
     *
     * @param productionOrder The production order to be added.
     * @return The result of the add function.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult addProductionOrder(final ProductionOrderWS productionOrder) {
        ProductionOrderService productionOrderService = new ProductionOrderService();
        return productionOrderService.addProductionOrder(productionOrder);
    }

    /**
     * Updates an existing production order.
     *
     * @param productionOrder The production order to be updated.
     * @return The result of the update function.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult updateProductionOrder(final ProductionOrderWS productionOrder) {
        ProductionOrderService productionOrderService = new ProductionOrderService();
        return productionOrderService.updateProductionOrder(productionOrder);
    }

    /**
     * Deletes the production order with the given id.
     *
     * @param id The id of the production order to be deleted.
     * @return The result of the delete function.
     */
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult deleteProductionOrder(@PathParam("id") final Integer id) {
        ProductionOrderService productionOrderService = new ProductionOrderService();
        return productionOrderService.deleteProductionOrder(id);
    }
}
