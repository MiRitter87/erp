package backend.webservice.common;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.BusinessPartnerHibernateDao;
import backend.dao.MaterialHibernateDao;
import backend.dao.SalesOrderHibernateDao;
import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;
import backend.exception.ObjectUnchangedException;
import backend.exception.QuantityExceedsInventoryException;
import backend.model.Material;
import backend.model.SalesOrder;
import backend.model.SalesOrderArray;
import backend.model.SalesOrderItem;
import backend.model.SalesOrderStatus;
import backend.model.webservice.SalesOrderItemWS;
import backend.model.webservice.SalesOrderWS;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;

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
	public WebServiceResult addSalesOrder(final SalesOrderWS salesOrder) {
		SalesOrder convertedSalesOrder = new SalesOrder();
		WebServiceResult addSalesOrderResult = new WebServiceResult();
		
		try {
			convertedSalesOrder = this.convertSalesOrder(salesOrder);
		}
		catch(Exception exception) {
			addSalesOrderResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("salesOrder.addError")));	
			logger.error(this.resources.getString("salesOrder.addError"), exception);
			return addSalesOrderResult;
		}
			
		addSalesOrderResult = this.validate(convertedSalesOrder);
		if(WebServiceTools.resultContainsErrorMessage(addSalesOrderResult))
			return addSalesOrderResult;
	
		addSalesOrderResult = this.add(convertedSalesOrder, addSalesOrderResult);
		addSalesOrderResult.setData(convertedSalesOrder.getId());
		
		return addSalesOrderResult;
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
				this.addMaterialInventoryForOrder(salesOrder);
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
	public WebServiceResult updateSalesOrder(final SalesOrderWS salesOrder) {
		SalesOrder convertedSalesOrder = new SalesOrder();
		WebServiceResult updateSalesOrderResult = new WebServiceResult(null);
		
		try {
			convertedSalesOrder = this.convertSalesOrder(salesOrder);
		}
		catch(Exception exception) {
			updateSalesOrderResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("salesOrder.updateError")));	
			logger.error(this.resources.getString("salesOrder.updateError"), exception);
			return updateSalesOrderResult;
		}
		
		updateSalesOrderResult = this.validate(convertedSalesOrder);
		if(WebServiceTools.resultContainsErrorMessage(updateSalesOrderResult))
			return updateSalesOrderResult;
		
		updateSalesOrderResult = this.update(convertedSalesOrder, updateSalesOrderResult);
		
		return updateSalesOrderResult;
	}
	
	
	/**
	 * Validates the sales order.
	 * 
	 * @param salesOrder The sales order to be validated.
	 * @return The result of the validation.
	 */
	private WebServiceResult validate(final SalesOrder salesOrder) {
		WebServiceResult webServiceResult = new WebServiceResult(null);
		
		try {
			salesOrder.validate();
		} 
		catch(NoItemsException noItemsException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, this.resources.getString("salesOrder.noItemsGiven")));
			return webServiceResult;
		}
		catch(DuplicateIdentifierException duplicateIdentifierException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("salesOrder.duplicateItemKey"), salesOrder.getId(), 
							duplicateIdentifierException.getDuplicateIdentifier())));
			return webServiceResult;
		}
		catch(QuantityExceedsInventoryException quantityException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("salesOrder.QuantityExceedsInventory"), 
							quantityException.getSalesOrderItem().getMaterial().getId(),
							quantityException.getSalesOrderItem().getMaterial().getInventory(),
							quantityException.getSalesOrderItem().getMaterial().getUnit())));
			return webServiceResult;
		}
		catch (Exception validationException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
			return webServiceResult;
		}
		
		return webServiceResult;
	}
	
	
	/**
	 * Updates the given sales order.
	 * 
	 * @param salesOrder The sales order to be updated.
	 * @return The result of the update function.
	 */
	private WebServiceResult update(final SalesOrder salesOrder, WebServiceResult webServiceResult) {	
		SalesOrder databaseSalesOrder;
		
		try {
			this.salesOrderDAO = new SalesOrderHibernateDao();
			databaseSalesOrder = this.salesOrderDAO.getSalesOrder(salesOrder.getId());
			this.salesOrderDAO.updateSalesOrder(salesOrder);
			this.updateMaterialInventory(salesOrder, databaseSalesOrder);
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, 
					MessageFormat.format(this.resources.getString("salesOrder.updateSuccess"), salesOrder.getId())));
		} 
		catch(ObjectUnchangedException objectUnchangedException) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.I, 
					MessageFormat.format(this.resources.getString("salesOrder.updateUnchanged"), salesOrder.getId())));
		}
		catch (Exception e) {
			webServiceResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, 
					MessageFormat.format(this.resources.getString("salesOrder.updateError"), salesOrder.getId())));
			
			logger.error(MessageFormat.format(this.resources.getString("salesOrder.updateError"), salesOrder.getId()), e);
		}
		finally {
			this.closeSalesOrderDAO();
		}
		
		return webServiceResult;
	}
	
	
	/**
	 * Inserts the given sales order.
	 * 
	 * @param salesOrder The sales order to be inserted.
	 * @return The result of the isnert function.
	 */
	private WebServiceResult add(final SalesOrder salesOrder, WebServiceResult webServiceResult) {		
		try {
			this.salesOrderDAO = new SalesOrderHibernateDao();
			this.salesOrderDAO.insertSalesOrder(salesOrder);
			this.reduceMaterialInventory(salesOrder);
			webServiceResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.S, this.resources.getString("salesOrder.addSuccess")));			
		} catch (Exception e) {
			webServiceResult.addMessage(new WebServiceMessage(
					WebServiceMessageType.E, this.resources.getString("salesOrder.addError")));
			
			logger.error(this.resources.getString("salesOrder.addError"), e);
		}
		finally {
			this.closeSalesOrderDAO();
		}
		
		return webServiceResult;
	}
	
	
	/**
	 * Converts the lean SalesOrder representation that is provided by the WebService to the internal data model for further processing.
	 * 
	 * @param salesOrderWS The lean sales order representation provided by the WebService.
	 * @return The SalesOrder model that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private SalesOrder convertSalesOrder(final SalesOrderWS salesOrderWS) throws Exception {
		SalesOrder convertedSalesOrder;
		
		convertedSalesOrder = this.convertSalesOrderHead(salesOrderWS);
		convertedSalesOrder.setItems(this.convertSalesOrderItems(salesOrderWS, convertedSalesOrder));
		
		return convertedSalesOrder;
	}
	
	
	/**
	 * Converts the head data of the sales order from the lean WebService representation to the internal data model of the backend.
	 * 
	 * @param salesOrderWS The lean sales order representation provided by the WebService.
	 * @return The SalesOrder model that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private SalesOrder convertSalesOrderHead(final SalesOrderWS salesOrderWS) throws Exception {
		BusinessPartnerHibernateDao partnerDAO = new BusinessPartnerHibernateDao();
		SalesOrder salesOrder = new SalesOrder();
		
		//Basic object data that are copied as-is.
		salesOrder.setId(salesOrderWS.getSalesOrderId());
		salesOrder.setOrderDate(salesOrderWS.getOrderDate());
		salesOrder.setRequestedDeliveryDate(salesOrderWS.getRequestedDeliveryDate());
		salesOrder.setStatus(salesOrderWS.getStatus());
		
		//Object references. Only the ID is given and the whole backend object has to be loaded and referenced.
		try {
			if(salesOrderWS.getSoldToId() != null)
				salesOrder.setSoldToParty(partnerDAO.getBusinessPartner(salesOrderWS.getSoldToId()));
			
			if(salesOrderWS.getShipToId() != null)
				salesOrder.setShipToParty(partnerDAO.getBusinessPartner(salesOrderWS.getShipToId()));
			
			if(salesOrderWS.getBillToId() != null)
				salesOrder.setBillToParty(partnerDAO.getBusinessPartner(salesOrderWS.getBillToId()));
		}
		finally {
			partnerDAO.close();
		}
		
		return salesOrder;
	}
	
	
	/**
	 * Converts the item data of the sales order from the lean WebService representation to the internal data model of the backend.
	 * 
	 * @param salesOrderWS The lean sales order representation provided by the WebService.
	 * @param salesOrder The converted sales order that is build based on the WebService representation.
	 * @return A list of item models that is used by the backend internally.
	 * @throws Exception In case the conversion fails.
	 */
	private List<SalesOrderItem> convertSalesOrderItems(final SalesOrderWS salesOrderWS, final SalesOrder salesOrder) throws Exception {
		List<SalesOrderItem> orderItems = new ArrayList<SalesOrderItem>();
		MaterialHibernateDao materialDAO = new MaterialHibernateDao();
		
		try {
			for(SalesOrderItemWS itemWS:salesOrderWS.getItems()) {
				SalesOrderItem orderItem = new SalesOrderItem();
				orderItem.setId(itemWS.getItemId());
				orderItem.setMaterial(materialDAO.getMaterial(itemWS.getMaterialId()));
				orderItem.setQuantity(itemWS.getQuantity());
				orderItem.setSalesOrder(salesOrder);
				orderItems.add(orderItem);
			}
		}
		finally {
			materialDAO.close();
		}
		
		return orderItems;
	}
	
	
	/**
	 * Reduces the inventory of the materials that are ordered.
	 * 
	 * @param salesOrder The sales order whose material inventories have to be reduced.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	private void reduceMaterialInventory(final SalesOrder salesOrder) throws Exception {
		MaterialHibernateDao materialDAO = new MaterialHibernateDao();
		Material currentMaterial;
		
		try {
			for(SalesOrderItem item:salesOrder.getItems()) {
				currentMaterial = item.getMaterial();
				currentMaterial.setInventory(currentMaterial.getInventory() - item.getQuantity());
				materialDAO.updateMaterial(currentMaterial);
			}			
		}
		finally {
			materialDAO.close();
		}
	}
	
	
	/**
	 * Adds the material quantities of the whole order to the material inventory.
	 * 
	 * @param salesOrder The sales order of which the material quantities are added to the inventory.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	private void  addMaterialInventoryForOrder(final SalesOrder salesOrder) throws Exception {
		MaterialHibernateDao materialDAO = new MaterialHibernateDao();
		Material currentMaterial;
		
		try {
			for(SalesOrderItem item:salesOrder.getItems()) {
				currentMaterial = item.getMaterial();
				currentMaterial.setInventory(currentMaterial.getInventory() + item.getQuantity());
				materialDAO.updateMaterial(currentMaterial);
			}			
		}
		finally {
			materialDAO.close();
		}
	}
	
	
	/**
	 * Updates the material inventories according to the changes of the order items.
	 * To determine the changes, the database state is compared with the order to be updated.
	 * 
	 * @throws Exception In case the update of the material inventory fails.
	 */
	private void updateMaterialInventoryForItems(final SalesOrder salesOrder, final SalesOrder databaseSalesOrder) throws Exception {
		HashMap<Integer, Long> additions = this.getMaterialAdditions(salesOrder, databaseSalesOrder);
		MaterialHibernateDao materialDAO = new MaterialHibernateDao();
		Material currentMaterial;
		
		try {
			//Reduce the material inventory by the additionally ordered quantities.
			for (Map.Entry<Integer, Long> entry : additions.entrySet()) {
				currentMaterial = materialDAO.getMaterial(entry.getKey());
				currentMaterial.setInventory(currentMaterial.getInventory() - entry.getValue());
				materialDAO.updateMaterial(currentMaterial);
			}
		}
		finally {
			materialDAO.close();
		}
	}
	
	
	/**
	 * Compares the database state of the sales order with the given sales order. All material additions of the sales order are returned.
	 * 
	 * @param salesOrder The sales order that is being checked for additions.
	 * @param databaseSalesOrder The database version of the sales order.
	 * @return All
	 */
	private HashMap<Integer, Long> getMaterialAdditions(final SalesOrder salesOrder, final SalesOrder databaseSalesOrder) {
		HashMap<Integer, Long> additions = new HashMap<Integer, Long>();	//<MaterialId, Quantity>
		boolean materialExistsInDatabaseItem;
		Long databaseItemQuantity = Long.valueOf(0);
		
		for(SalesOrderItem tempOrderItem:salesOrder.getItems()) {
			materialExistsInDatabaseItem = false;
			
			for(SalesOrderItem tempDatabaseItem:databaseSalesOrder.getItems()) {
				if(tempDatabaseItem.getMaterial().getId().intValue() == tempOrderItem.getMaterial().getId().intValue()) {
					materialExistsInDatabaseItem = true;
					databaseItemQuantity = databaseItemQuantity + tempDatabaseItem.getQuantity();
				}
			}
			
			//Add quantities for a new material.
			if(materialExistsInDatabaseItem == false) {
				additions.put(tempOrderItem.getMaterial().getId(), tempOrderItem.getQuantity());
			}
			else {
				//Determine possible additional quantities of an existing material.
				if(tempOrderItem.getQuantity() > databaseItemQuantity)
					additions.put(tempOrderItem.getMaterial().getId(), tempOrderItem.getQuantity()-databaseItemQuantity);
			}
		}
		
		return additions;
	}
	
	
	/**
	 * Updates the inventory of the materials when the order is being updated.
	 * 
	 * @param salesOrder The sales order being updated.
	 * @param databaseSalesOrder The database state of the sales order before the update has been performed.
	 * @throws Exception In case the update of the material inventory fails.
	 */
	private void updateMaterialInventory(final SalesOrder salesOrder, final SalesOrder databaseSalesOrder) throws Exception {
		//If the sales order status changes to "Canceled", the ordered quantities are added back to the inventory.
		if(databaseSalesOrder.getStatus() != SalesOrderStatus.CANCELED && salesOrder.getStatus() == SalesOrderStatus.CANCELED) {
			this.addMaterialInventoryForOrder(salesOrder);
			return;
		}
		
		this.updateMaterialInventoryForItems(salesOrder, databaseSalesOrder);
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
