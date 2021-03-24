package backend.webservice.common;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.SalesOrderHibernateDao;
import backend.model.SalesOrder;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the sales order WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 *
 */
public class SalesOrderService {
	/**
	 * DAO for sales order access.
	 */
	private SalesOrderHibernateDao salesOrderDAO;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(SalesOrderService.class);
	
	
	/**
	 * Provides the sales order with the given id.
	 * 
	 * @param id The id of the sales order.
	 * @return The sales order with the given id, if found.
	 */
	public WebServiceResult getSalesOrder(final Integer id) {
		return null;
	}
	
	
	/**
	 * Provides a list of all sales orders.
	 * 
	 * @return A list of all sales orders.
	 */
	public WebServiceResult getSalesOrders() {
		return null;
	}
	
	
	/**
	 * Adds a sales order.
	 * 
	 * @param salesOrder The sales order to be added.
	 * @return The result of the add function.
	 */
	public WebServiceResult addSalesOrder(final SalesOrder salesOrder) {
		return null;
	}
	
	
	/**
	 * Deletes the sales order with the given id.
	 * 
	 * @param id The id of the sales order to be deleted.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deleteSalesOrder(final Integer id) {
		return null;
	}
	
	
	/**
	 * Updates an existing sales order.
	 * 
	 * @param salesOrder The sales order to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updateSalesOrder(final SalesOrder salesOrder) {
		return null;
	}
	
	
	/**
	 * Closes the sales order DAO.
	 */
	private void closeSalesOrderDAO() {
		try {
			this.salesOrderDAO.close();
		} catch (IOException e) {
			logger.error(this.resources.getString("salesOrder.closeFailed"), e);
		}
	}
}
