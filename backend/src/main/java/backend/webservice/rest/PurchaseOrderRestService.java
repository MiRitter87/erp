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

import backend.model.webservice.PurchaseOrderWS;
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
	 * @return A list of all purchase orders.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult getPurchaseOrders() {
		PurchaseOrderService purchaseOrderService = new PurchaseOrderService();
		return purchaseOrderService.getPurchaseOrders();
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
