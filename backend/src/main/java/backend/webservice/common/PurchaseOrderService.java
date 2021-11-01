package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.AccountDao;
import backend.dao.BusinessPartnerDao;
import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.dao.PurchaseOrderDao;
import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;
import backend.exception.ObjectUnchangedException;
import backend.model.purchaseOrder.PurchaseOrder;
import backend.model.purchaseOrder.PurchaseOrderArray;
import backend.model.purchaseOrder.PurchaseOrderItem;
import backend.model.purchaseOrder.PurchaseOrderItemWS;
import backend.model.purchaseOrder.PurchaseOrderStatus;
import backend.model.purchaseOrder.PurchaseOrderWS;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;

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
	 * Manager for material inventory.
	 */
	private PurchaseOrderInventoryManager inventoryManager;
	
	/**
	 * Manager for purchase order payments.
	 */
	private PurchaseOrderPaymentManager paymentManager;
	
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
		this.inventoryManager = new PurchaseOrderInventoryManager();
		this.paymentManager = new PurchaseOrderPaymentManager();
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
	 * @param orderStatusQuery Specifies the purchase orders to be selected based on the status.
	 * @return A list of all purchase orders.
	 */
	public WebServiceResult getPurchaseOrders(final PurchaseOrderStatus orderStatusQuery) {
		PurchaseOrderArray purchaseOrders = new PurchaseOrderArray();
		WebServiceResult getPurchaseOrdersResult = new WebServiceResult(null);
		
		try {
			purchaseOrders.setPurchaseOrders(this.purchaseOrderDAO.getPurchaseOrders(orderStatusQuery));
			getPurchaseOrdersResult.setData(purchaseOrders);
		} catch (Exception e) {
			getPurchaseOrdersResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("purchaseOrder.getPurchaseOrdersError")));
			
			logger.error(this.resources.getString("purchaseOrder.getPurchaseOrdersError"), e);
		}
		
		return getPurchaseOrdersResult;
	}
	
	
	/**
	 * Adds a purchase order.
	 * 
	 * @param purchaseOrder The purchase order to be added.
	 * @return The result of the add function.
	 */
	public WebServiceResult addPurchaseOrder(final PurchaseOrderWS purchaseOrder) {
		PurchaseOrder convertedPurchaseOrder = new PurchaseOrder();
		WebServiceResult addPurchaseOrderResult = new WebServiceResult();
		
		try {
			convertedPurchaseOrder = this.convertPurchaseOrder(purchaseOrder);
		}
		catch(Exception exception) {
			addPurchaseOrderResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("purchaseOrder.addError")));	
			logger.error(this.resources.getString("purchaseOrder.addError"), exception);
			return addPurchaseOrderResult;
		}
			
		addPurchaseOrderResult = this.validate(convertedPurchaseOrder);
		if(WebServiceTools.resultContainsErrorMessage(addPurchaseOrderResult)) {
			return addPurchaseOrderResult;
		}
	
		addPurchaseOrderResult = this.add(convertedPurchaseOrder, addPurchaseOrderResult);
		addPurchaseOrderResult.setData(convertedPurchaseOrder.getId());
		
		return addPurchaseOrderResult;
	}
	
	
	/**
	 * Deletes the purchase order with the given id.
	 * 
	 * @param id The id of the purchase order to be deleted.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deletePurchaseOrder(final Integer id) {
		PurchaseOrder purchaseOrder = null;
		WebServiceResult deletePurchaseOrderResult = new WebServiceResult(null);
		
		//Check if a purchase order with the given id exists.
		try {
			purchaseOrder = this.purchaseOrderDAO.getPurchaseOrder(id);
			
			if(purchaseOrder != null) {
				//Delete purchase order if exists.
				this.purchaseOrderDAO.deletePurchaseOrder(purchaseOrder);
				
				this.inventoryManager.updateMaterialInventoryOnOrderDeletion(purchaseOrder);
				
				if(purchaseOrder.isStatusActive(PurchaseOrderStatus.INVOICE_SETTLED))
					this.paymentManager.increaseAccountBalance(purchaseOrder);
				
				deletePurchaseOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
						MessageFormat.format(this.resources.getString("purchaseOrder.deleteSuccess"), id)));
			}
			else {
				//Purchase order not found.
				deletePurchaseOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
						MessageFormat.format(this.resources.getString("purchaseOrder.notFound"), id)));
			}
		}
		catch (Exception e) {
			deletePurchaseOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
					MessageFormat.format(this.resources.getString("purchaseOrder.deleteError"), id)));
			
			logger.error(MessageFormat.format(this.resources.getString("purchaseOrder.deleteError"), id), e);
		}
		
		return deletePurchaseOrderResult;
	}
	
	
	/**
	 * Updates an existing purchase order.
	 * 
	 * @param purchaseOrder The purchase order to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updatePurchaseOrder(final PurchaseOrderWS purchaseOrder) {
		PurchaseOrder convertedPurchaseOrder = new PurchaseOrder();
		WebServiceResult updatePurchaseOrderResult = new WebServiceResult(null);
		
		try {
			convertedPurchaseOrder = this.convertPurchaseOrder(purchaseOrder);
		}
		catch(Exception exception) {
			updatePurchaseOrderResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, MessageFormat.format(this.resources.getString("purchaseOrder.updateError"), convertedPurchaseOrder.getId())));	
			logger.error(MessageFormat.format(this.resources.getString("purchaseOrder.updateError"), convertedPurchaseOrder.getId()), exception);
			return updatePurchaseOrderResult;
		}
		
		updatePurchaseOrderResult = this.validate(convertedPurchaseOrder);
		if(WebServiceTools.resultContainsErrorMessage(updatePurchaseOrderResult)) {
			return updatePurchaseOrderResult;
		}
		
		updatePurchaseOrderResult = this.update(convertedPurchaseOrder, updatePurchaseOrderResult);
		
		return updatePurchaseOrderResult;
	}
	
	
	/**
	 * Converts the lean PurchaseOrder representation that is provided by the WebService to the internal data model for further processing.
	 * 
	 * @param purchaseOrderWS The lean purchase order representation provided by the WebService.
	 * @return The PurchaseOrder model that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private PurchaseOrder convertPurchaseOrder(final PurchaseOrderWS purchaseOrderWS) throws Exception {
		PurchaseOrder convertedPurchaseOrder;
		
		convertedPurchaseOrder = this.convertPurchaseOrderHead(purchaseOrderWS);
		convertedPurchaseOrder.setItems(this.convertPurchaseOrderItems(purchaseOrderWS, convertedPurchaseOrder));
		
		return convertedPurchaseOrder;
	}
	
	
	/**
	 * Converts the head data of the purchase order from the lean WebService representation to the internal data model of the backend.
	 * 
	 * @param purchaseOrderWS The lean purchase order representation provided by the WebService.
	 * @return The PurchaseOrder model that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private PurchaseOrder convertPurchaseOrderHead(final PurchaseOrderWS purchaseOrderWS) throws Exception {
		BusinessPartnerDao partnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();
		AccountDao accountDAO = DAOManager.getInstance().getAccountDAO();
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		
		//Basic object data that are copied as-is.
		purchaseOrder.setId(purchaseOrderWS.getPurchaseOrderId());
		purchaseOrder.setOrderDate(purchaseOrderWS.getOrderDate());
		purchaseOrder.setRequestedDeliveryDate(purchaseOrderWS.getRequestedDeliveryDate());
		purchaseOrder.setStatus(purchaseOrderWS.getStatus());
		
		//Object references. Only the ID is given and the whole backend object has to be loaded and referenced.
		if(purchaseOrderWS.getVendorId() != null)
			purchaseOrder.setVendor(partnerDAO.getBusinessPartner(purchaseOrderWS.getVendorId()));
		
		if(purchaseOrderWS.getPaymentAccountId() != null)
			purchaseOrder.setPaymentAccount(accountDAO.getAccount(purchaseOrderWS.getPaymentAccountId()));
		
		return purchaseOrder;
	}
	
	
	/**
	 * Converts the item data of the purchase order from the lean WebService representation to the internal data model of the backend.
	 * 
	 * @param purchaseOrderWS The lean purchase order representation provided by the WebService.
	 * @param purchaseOrder The converted purchase order that is build based on the WebService representation.
	 * @return A list of item models that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private List<PurchaseOrderItem> convertPurchaseOrderItems(final PurchaseOrderWS purchaseOrderWS, final PurchaseOrder purchaseOrder) throws Exception {
		List<PurchaseOrderItem> orderItems = new ArrayList<PurchaseOrderItem>();
		MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();
		
		for(PurchaseOrderItemWS itemWS:purchaseOrderWS.getItems()) {
			PurchaseOrderItem orderItem = new PurchaseOrderItem();

			orderItem.setId(itemWS.getItemId());
			orderItem.setMaterial(materialDAO.getMaterial(itemWS.getMaterialId()));
			orderItem.setQuantity(itemWS.getQuantity());
			orderItem.setPurchaseOrder(purchaseOrder);
			orderItems.add(orderItem);
		}
		
		return orderItems;
	}
	
	
	/**
	 * Validates the purchase order.
	 * 
	 * @param purchaseOrder The purchase order to be validated.
	 * @return The result of the validation.
	 */
	private WebServiceResult validate(final PurchaseOrder purchaseOrder) {
		WebServiceResult webServiceResult = new WebServiceResult(null);
		
		try {
			purchaseOrder.validate();
		} 
		catch(NoItemsException noItemsException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, this.resources.getString("purchaseOrder.noItemsGiven")));
			return webServiceResult;
		}
		catch(DuplicateIdentifierException duplicateIdentifierException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("purchaseOrder.duplicateItemKey"), purchaseOrder.getId(), 
							duplicateIdentifierException.getDuplicateIdentifier())));
			return webServiceResult;
		}
		catch (Exception validationException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
			return webServiceResult;
		}
		
		return webServiceResult;
	}
	
	
	/**
	 * Updates the given purchase order.
	 * 
	 * @param purchaseOrder The purchase order to be updated.
	 * @return The result of the update function.
	 */
	private WebServiceResult update(final PurchaseOrder purchaseOrder, WebServiceResult webServiceResult) {
		PurchaseOrder databasePurchaseOrder;
		
		try {
			databasePurchaseOrder = this.purchaseOrderDAO.getPurchaseOrder(purchaseOrder.getId());
			this.purchaseOrderDAO.updatePurchaseOrder(purchaseOrder);
			this.inventoryManager.updateMaterialInventoryOnOrderUpdate(purchaseOrder, databasePurchaseOrder);
			this.paymentManager.updatePurchaseOrderPayment(purchaseOrder, databasePurchaseOrder);
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
					MessageFormat.format(this.resources.getString("purchaseOrder.updateSuccess"), purchaseOrder.getId())));
		} 
		catch(ObjectUnchangedException objectUnchangedException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.I, 
					MessageFormat.format(this.resources.getString("purchaseOrder.updateUnchanged"), purchaseOrder.getId())));
		}
		catch (Exception e) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("purchaseOrder.updateError"), purchaseOrder.getId())));
			
			logger.error(MessageFormat.format(this.resources.getString("purchaseOrder.updateError"), purchaseOrder.getId()), e);
		}
		
		return webServiceResult;
	}
	
	
	/**
	 * Inserts the given purchase order.
	 * 
	 * @param purchaseOrder The purchase order to be inserted.
	 * @return The result of the insert function.
	 */
	private WebServiceResult add(final PurchaseOrder purchaseOrder, WebServiceResult webServiceResult) {		
		try {
			this.purchaseOrderDAO.insertPurchaseOrder(purchaseOrder);
			webServiceResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.S, this.resources.getString("purchaseOrder.addSuccess")));			
		} catch (Exception e) {
			webServiceResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("purchaseOrder.addError")));
			
			logger.error(this.resources.getString("purchaseOrder.addError"), e);
		}
		
		return webServiceResult;
	}
}
