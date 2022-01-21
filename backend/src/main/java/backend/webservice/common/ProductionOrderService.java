package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.DAOManager;
import backend.dao.ProductionOrderDao;
import backend.model.productionOrder.ProductionOrder;
import backend.model.productionOrder.ProductionOrderArray;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the production order WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 *
 */
public class ProductionOrderService {
	/**
	 * DAO for production order access.
	 */
	private ProductionOrderDao productionOrderDAO;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(ProductionOrderService.class);
	
	
	/**
	 * Initializes the production order service.
	 */
	public ProductionOrderService() {
		this.productionOrderDAO = DAOManager.getInstance().getProductionOrderDAO();
	}
	
	
	/**
	 * Provides the production order with the given id.
	 * 
	 * @param id The id of the production order.
	 * @return The production order with the given id, if found.
	 */
	public WebServiceResult getProductionOrder(final Integer id) {
		ProductionOrder productionOrder = null;
		WebServiceResult getProductionOrderResult = new WebServiceResult(null);
		
		try {
			productionOrder = this.productionOrderDAO.getProductionOrder(id);
			
			if(productionOrder != null) {
				//Production order found
				getProductionOrderResult.setData(productionOrder);
			}
			else {
				//Production order not found
				getProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("productionOrder.notFound"), id)));
			}
		}
		catch (Exception e) {
			getProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("productionOrder.getError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("productionOrder.getError"), id), e);
		}
		
		return getProductionOrderResult;
	}
	
	
	/**
	 * Provides a list of all production orders.
	 * 
	 * @return A list of all production orders.
	 */
	public WebServiceResult getProductionOrders() {
		WebServiceResult getProductionOrdersResult = new WebServiceResult(null);
		ProductionOrderArray productionOrders = new ProductionOrderArray();
		
		try {
			productionOrders.setProductionOrders(this.productionOrderDAO.getProductionOrders());
			getProductionOrdersResult.setData(productionOrders);
		} catch (Exception e) {
			getProductionOrdersResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("productionOrder.getProductionOrdersError")));
			
			logger.error(this.resources.getString("productionOrder.getProductionOrdersError"), e);
		}
		
		return getProductionOrdersResult;
	}
	
	
	/**
	 * Deletes the production order with the given id.
	 * 
	 * @param id The id of the production order to be deleted.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deleteProductionOrder(final Integer id) {
		ProductionOrder productionOrder = null;
		WebServiceResult deleteProductionOrderResult = new WebServiceResult(null);
		
		//Check if a production order with the given id exists.
		try {
			productionOrder = this.productionOrderDAO.getProductionOrder(id);
			
			if(productionOrder != null) {
				//Delete production order if exists.
				this.productionOrderDAO.deleteProductionOrder(productionOrder);				
				deleteProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
						MessageFormat.format(this.resources.getString("productionOrder.deleteSuccess"), id)));
			}
			else {
				//Production order not found.
				deleteProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("productionOrder.notFound"), id)));
			}
		}
		catch (Exception e) {
			deleteProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("productionOrder.deleteError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("productionOrder.deleteError"), id), e);
		}
		
		return deleteProductionOrderResult;
	}
}
