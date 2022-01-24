package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.dao.ProductionOrderDao;
import backend.model.productionOrder.ProductionOrder;
import backend.model.productionOrder.ProductionOrderArray;
import backend.model.productionOrder.ProductionOrderItem;
import backend.model.productionOrder.ProductionOrderItemWS;
import backend.model.productionOrder.ProductionOrderWS;
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
	
	
	/**
	 * Updates an existing production order.
	 * 
	 * @param productionOrder The production order to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updateProductionOrder(final ProductionOrderWS productionOrder) {
		ProductionOrder convertedProductionOrder = new ProductionOrder();
		WebServiceResult updateProductionOrderResult = new WebServiceResult(null);
		
		try {
			convertedProductionOrder = this.convertProductionOrder(productionOrder);
		}
		catch(Exception exception) {
			updateProductionOrderResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, MessageFormat.format(this.resources.getString("productionOrder.updateError"), convertedProductionOrder.getId())));	
			logger.error(MessageFormat.format(this.resources.getString("productionOrder.updateError"), convertedProductionOrder.getId()), exception);
			return updateProductionOrderResult;
		}
		
//		updateSalesOrderResult = this.validate(convertedSalesOrder);
//		if(WebServiceTools.resultContainsErrorMessage(updateSalesOrderResult)) {
//			return updateSalesOrderResult;
//		}
		
//		updateSalesOrderResult = this.update(convertedSalesOrder, updateSalesOrderResult);

		return updateProductionOrderResult;
	}
	
	
	/**
	 * Converts the lean production order representation that is provided by the WebService to the internal data model for further processing.
	 * 
	 * @param productionOrderWS The lean production order representation provided by the WebService.
	 * @return The ProductionOrder model that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private ProductionOrder convertProductionOrder(final ProductionOrderWS productionOrderWS) throws Exception {
		ProductionOrder convertedProductionOrder;
		
		convertedProductionOrder = this.convertProductionOrderHead(productionOrderWS);
		convertedProductionOrder.setItems(this.convertProductionOrderItems(productionOrderWS, convertedProductionOrder));
		
		return convertedProductionOrder;
	}
	
	
	/**
	 * Converts the head data of the production order from the lean WebService representation to the internal data model of the backend.
	 * 
	 * @param productionOrderWS The lean production order representation provided by the WebService.
	 * @return The ProductionOrder model that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private ProductionOrder convertProductionOrderHead(final ProductionOrderWS productionOrderWS) throws Exception {
		ProductionOrder productionOrder = new ProductionOrder();
		
		//Basic object data that are copied as-is.
		productionOrder.setId(productionOrderWS.getProductionOrderId());
		productionOrder.setOrderDate(productionOrderWS.getOrderDate());
		productionOrder.setPlannedExecutionDate(productionOrderWS.getPlannedExecutionDate());
		productionOrder.setExecutionDate(productionOrderWS.getExecutionDate());
		productionOrder.setStatus(productionOrderWS.getStatus());
		
		return productionOrder;
	}
	
	
	/**
	 * Converts the item data of the production order from the lean WebService representation to the internal data model of the backend.
	 * 
	 * @param productionOrderWS The lean production order representation provided by the WebService.
	 * @param productionOrder The converted production order that is build based on the WebService representation.
	 * @return A list of item models that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private List<ProductionOrderItem> convertProductionOrderItems(final ProductionOrderWS productionOrderWS, final ProductionOrder productionOrder) throws Exception {
		List<ProductionOrderItem> orderItems = new ArrayList<ProductionOrderItem>();
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		
		for(ProductionOrderItemWS itemWS:productionOrderWS.getItems()) {
			ProductionOrderItem orderItem = new ProductionOrderItem();
			orderItem.setId(itemWS.getItemId());
			orderItem.setMaterial(materialDAO.getMaterial(itemWS.getMaterialId()));
			orderItem.setQuantity(itemWS.getQuantity());
			orderItem.setProductionOrder(productionOrder);
			orderItems.add(orderItem);
		}
		
		return orderItems;
	}
}
