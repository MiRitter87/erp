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

import backend.model.SalesOrder;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.SalesOrderService;

/**
 * WebService for sales order access using REST technology.
 * 
 * @author Michael
 */
@Path("/salesOrders")
public class SalesOrderRestService {
	/**
	 * Provides the sales order with the given ID.
	 * 
	 * @param id The ID of the sales order.
	 * @return The sales order with the given ID.
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult getSalesOrder(@PathParam("id") final Integer id) {
		SalesOrderService salesOrderService = new SalesOrderService();
		return salesOrderService.getSalesOrder(id);
	}
	
	
	/**
	 * Provides a list of all sales orders.
	 * 
	 * @return A list of all sales orders.
	 */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult getSalesOrders() {
		SalesOrderService salesOrderService = new SalesOrderService();
		return salesOrderService.getSalesOrders();
	}
	
	
	/**
	 * Adds a sales order.
	 * 
	 * @param salesOrder The sales order to be added.
	 * @return The result of the add function.
	 */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult addSalesOrder(final SalesOrder salesOrder) {
		SalesOrderService salesOrderService = new SalesOrderService();
		return salesOrderService.addSalesOrder(salesOrder);
	}
	
	
	/**
	 * Updates an existing sales order.
	 * 
	 * @param salesOrder The sales order to be updated.
	 * @return The result of the update function.
	 */
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult updateSalesOrder(final SalesOrder salesOrder) {
		SalesOrderService salesOrderService = new SalesOrderService();
		return salesOrderService.updateSalesOrder(salesOrder);
	}
	
	
	/**
	 * Deletes the sales order with the given id.
	 * 
	 * @param id The id of the sales order to be deleted.
	 * @return The result of the delete function.
	 */
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WebServiceResult deleteSalesOrder(@PathParam("id") final Integer id) {
		SalesOrderService salesOrderService = new SalesOrderService();
		return salesOrderService.deleteSalesOrder(id);
	}
}
