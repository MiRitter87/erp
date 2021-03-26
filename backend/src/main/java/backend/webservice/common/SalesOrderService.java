package backend.webservice.common;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.SalesOrderHibernateDao;
import backend.exception.ObjectUnchangedException;
import backend.model.SalesOrder;
import backend.model.SalesOrderArray;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
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
		SalesOrder salesOrder = null;
		WebServiceResult getSalesOrderResult = new WebServiceResult(null);
		
		try {
			this.salesOrderDAO = new SalesOrderHibernateDao();
			salesOrder = this.salesOrderDAO.getSalesOrder(id);
			
			if(salesOrder != null) {
				//Sales order found
				getSalesOrderResult.setData(salesOrder);
			}
			else {
				//Sales order not found
				getSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("salesOrder.notFound"), id)));
			}
		}
		catch (Exception e) {
			getSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("salesOrder.getError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("salesOrder.getError"), id), e);
		}
		finally {
			this.closeSalesOrderDAO();
		}
		
		return getSalesOrderResult;
	}
	
	
	/**
	 * Provides a list of all sales orders.
	 * 
	 * @return A list of all sales orders.
	 */
	public WebServiceResult getSalesOrders() {
		SalesOrderArray salesOrders = new SalesOrderArray();
		WebServiceResult getSalesOrdersResult = new WebServiceResult(null);
		
		try {
			this.salesOrderDAO = new SalesOrderHibernateDao();
			salesOrders.setSalesOrders(this.salesOrderDAO.getSalesOrders());
			getSalesOrdersResult.setData(salesOrders);
		} catch (Exception e) {
			getSalesOrdersResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("salesOrder.getSalesOrdersError")));
			
			logger.error(this.resources.getString("salesOrder.getSalesOrdersError"), e);
		}
		finally {
			this.closeSalesOrderDAO();
		}
		
		return getSalesOrdersResult;
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
		SalesOrder salesOrder = null;
		WebServiceResult deleteSalesOrderResult = new WebServiceResult(null);
		
		//Check if a sales order with the given id exists.
		try {
			this.salesOrderDAO = new SalesOrderHibernateDao();
			salesOrder = this.salesOrderDAO.getSalesOrder(id);
			
			if(salesOrder != null) {
				//Delete sales order if exists.
				this.salesOrderDAO.deleteSalesOrder(salesOrder);
				deleteSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
						MessageFormat.format(this.resources.getString("salesOrder.deleteSuccess"), id)));
			}
			else {
				//Sales order not found.
				deleteSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("salesOrder.notFound"), id)));
			}
		}
		catch (Exception e) {
			deleteSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("salesOrder.deleteError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("salesOrder.deleteError"), id), e);
		}
		finally {
			this.closeSalesOrderDAO();
		}
		
		return deleteSalesOrderResult;
	}
	
	
	/**
	 * Updates an existing sales order.
	 * 
	 * @param salesOrder The sales order to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updateSalesOrder(final SalesOrder salesOrder) {
		WebServiceResult updateSalesOrderResult = new WebServiceResult(null);
		this.salesOrderDAO = new SalesOrderHibernateDao();
		
		//Validation of the given sales order.
		try {
			salesOrder.validate();
		} catch (Exception validationException) {
			updateSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
			this.closeSalesOrderDAO();
			return updateSalesOrderResult;
		}
		
		//Update sales order if validation is successful.
		try {
			this.salesOrderDAO.updateSalesOrder(salesOrder);
			updateSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
					MessageFormat.format(this.resources.getString("salesOrder.updateSuccess"), salesOrder.getId())));
		} 
		catch(ObjectUnchangedException objectUnchangedException) {
			updateSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.I, 
					MessageFormat.format(this.resources.getString("salesOrder.updateUnchanged"), salesOrder.getId())));
		}
		catch (Exception e) {
			updateSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("salesOrder.updateError"), salesOrder.getId())));
			
			logger.error(MessageFormat.format(this.resources.getString("salesOrder.updateError"), salesOrder.getId()), e);
		}
		finally {
			this.closeSalesOrderDAO();
		}
		
		return updateSalesOrderResult;
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
