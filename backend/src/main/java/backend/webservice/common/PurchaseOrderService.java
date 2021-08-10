package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.DAOManager;
import backend.dao.PurchaseOrderDao;
import backend.model.PurchaseOrder;
import backend.model.PurchaseOrderArray;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the purchase order WebService that is used by the SOAP as well as the REST service.
 * 
 * @author Michael
 */
public class PurchaseOrderService {
	/**
	 * DAO for purchase order access.
	 */
	private PurchaseOrderDao purchaseOrderDAO;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(PurchaseOrderService.class);
	
	
	/**
	 * Initializes the purchase order service.
	 */
	public PurchaseOrderService() {
		this.purchaseOrderDAO = DAOManager.getInstance().getPurchaseOrderDAO();
	}
	
	
	/**
	 * Provides the purchase order with the given id.
	 * 
	 * @param id The id of the purchase order.
	 * @return The purchase order with the given id, if found.
	 */
	public WebServiceResult getPurchaseOrder(final Integer id) {
		PurchaseOrder purchaseOrder = null;
		WebServiceResult getPurchaseOrderResult = new WebServiceResult(null);
		
		try {
			purchaseOrder = this.purchaseOrderDAO.getPurchaseOrder(id);
			
			if(purchaseOrder != null) {
				//Purchase order found
				getPurchaseOrderResult.setData(purchaseOrder);
			}
			else {
				//Purchase order not found
				getPurchaseOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("purchaseOrder.notFound"), id)));
			}
		}
		catch (Exception e) {
			getPurchaseOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("purchaseOrder.getError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("purchaseOrder.getError"), id), e);
		}
		
		return getPurchaseOrderResult;
	}
	
	
	/**
	 * Provides a list of all purchase orders.
	 * 
	 * @return A list of all purchase orders.
	 */
	public WebServiceResult getPurchaseOrders() {
		PurchaseOrderArray purchaseOrders = new PurchaseOrderArray();
		WebServiceResult getPurchaseOrdersResult = new WebServiceResult(null);
		
		try {
			purchaseOrders.setPurchaseOrders(this.purchaseOrderDAO.getPurchaseOrders());
			getPurchaseOrdersResult.setData(purchaseOrders);
		} catch (Exception e) {
			getPurchaseOrdersResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("purchaseOrder.getPurchaseOrdersError")));
			
			logger.error(this.resources.getString("purchaseOrder.getPurchaseOrdersError"), e);
		}
		
		return getPurchaseOrdersResult;
	}
}
