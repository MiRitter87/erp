package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.controller.SalesOrderInventoryController;
import backend.controller.SalesOrderPaymentController;
import backend.dao.AccountDao;
import backend.dao.BusinessPartnerDao;
import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.dao.SalesOrderDao;
import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;
import backend.exception.ObjectUnchangedException;
import backend.exception.QuantityExceedsInventoryException;
import backend.model.salesOrder.SalesOrder;
import backend.model.salesOrder.SalesOrderArray;
import backend.model.salesOrder.SalesOrderItem;
import backend.model.salesOrder.SalesOrderItemWS;
import backend.model.salesOrder.SalesOrderStatus;
import backend.model.salesOrder.SalesOrderWS;
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
    private SalesOrderDao salesOrderDAO;

    /**
     * Controller for material inventory.
     */
    private SalesOrderInventoryController inventoryController;

    /**
     * Controller for sales order payments.
     */
    private SalesOrderPaymentController paymentController;

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources = ResourceBundle.getBundle("backend");

    /**
     * Application logging.
     */
    public static final Logger LOGGER = LogManager.getLogger(SalesOrderService.class);

    /**
     * Initializes the sales order service.
     */
    public SalesOrderService() {
        this.salesOrderDAO = DAOManager.getInstance().getSalesOrderDAO();
        this.inventoryController = new SalesOrderInventoryController();
        this.paymentController = new SalesOrderPaymentController();
    }

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
            salesOrder = this.salesOrderDAO.getSalesOrder(id);

            if (salesOrder != null) {
                // Sales order found
                getSalesOrderResult.setData(salesOrder);
            } else {
                // Sales order not found
                getSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                        MessageFormat.format(this.resources.getString("salesOrder.notFound"), id)));
            }
        } catch (Exception e) {
            getSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("salesOrder.getError"), id)));

            LOGGER.error(MessageFormat.format(this.resources.getString("salesOrder.getError"), id), e);
        }

        return getSalesOrderResult;
    }

    /**
     * Provides a list of all sales orders.
     *
     * @param orderStatusQuery Specifies the sales orders to be selected based on the status.
     * @return A list of all sales orders.
     */
    public WebServiceResult getSalesOrders(final SalesOrderStatus orderStatusQuery) {
        SalesOrderArray salesOrders = new SalesOrderArray();
        WebServiceResult getSalesOrdersResult = new WebServiceResult(null);

        try {
            salesOrders.setSalesOrders(this.salesOrderDAO.getSalesOrders(orderStatusQuery));
            getSalesOrdersResult.setData(salesOrders);
        } catch (Exception e) {
            getSalesOrdersResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("salesOrder.getSalesOrdersError")));

            LOGGER.error(this.resources.getString("salesOrder.getSalesOrdersError"), e);
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
        } catch (Exception exception) {
            addSalesOrderResult.addMessage(
                    new WebServiceMessage(WebServiceMessageType.E, this.resources.getString("salesOrder.addError")));
            LOGGER.error(this.resources.getString("salesOrder.addError"), exception);
            return addSalesOrderResult;
        }

        addSalesOrderResult.addMessages(this.validate(convertedSalesOrder));
        if (WebServiceTools.resultContainsErrorMessage(addSalesOrderResult)) {
            return addSalesOrderResult;
        }

        addSalesOrderResult.addMessages(this.add(convertedSalesOrder));
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

        // Check if a sales order with the given id exists.
        try {
            salesOrder = this.salesOrderDAO.getSalesOrder(id);

            if (salesOrder != null) {
                // Delete sales order if exists.
                this.salesOrderDAO.deleteSalesOrder(salesOrder);

                this.inventoryController.addMaterialInventoryForOrder(salesOrder);

                if (salesOrder.getStatus() == SalesOrderStatus.FINISHED) {
                    this.paymentController.removePaymentFromAccount(salesOrder);
                }

                deleteSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.S,
                        MessageFormat.format(this.resources.getString("salesOrder.deleteSuccess"), id)));
            } else {
                // Sales order not found.
                deleteSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                        MessageFormat.format(this.resources.getString("salesOrder.notFound"), id)));
            }
        } catch (Exception e) {
            deleteSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("salesOrder.deleteError"), id)));

            LOGGER.error(MessageFormat.format(this.resources.getString("salesOrder.deleteError"), id), e);
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
        } catch (Exception exception) {
            updateSalesOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, MessageFormat
                    .format(this.resources.getString("salesOrder.updateError"), convertedSalesOrder.getId())));
            LOGGER.error(MessageFormat.format(this.resources.getString("salesOrder.updateError"),
                    convertedSalesOrder.getId()), exception);
            return updateSalesOrderResult;
        }

        updateSalesOrderResult.addMessages(this.validate(convertedSalesOrder));
        if (WebServiceTools.resultContainsErrorMessage(updateSalesOrderResult)) {
            return updateSalesOrderResult;
        }

        updateSalesOrderResult.addMessages(this.update(convertedSalesOrder));

        return updateSalesOrderResult;
    }

    /**
     * Validates the sales order.
     *
     * @param salesOrder The sales order to be validated.
     * @return A list of potential messages that occurred during validation.
     */
    private List<WebServiceMessage> validate(final SalesOrder salesOrder) {
        List<WebServiceMessage> messages = new ArrayList<WebServiceMessage>();

        try {
            salesOrder.validate();
        } catch (NoItemsException noItemsException) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("salesOrder.noItemsGiven")));
        } catch (DuplicateIdentifierException duplicateIdentifierException) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("salesOrder.duplicateItemKey"), salesOrder.getId(),
                            duplicateIdentifierException.getDuplicateIdentifier())));
        } catch (QuantityExceedsInventoryException quantityException) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("salesOrder.QuantityExceedsInventory"),
                            quantityException.getSalesOrderItem().getMaterial().getId(),
                            quantityException.getSalesOrderItem().getMaterial().getInventory(),
                            quantityException.getSalesOrderItem().getMaterial().getUnit())));
        } catch (Exception validationException) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
        }

        return messages;
    }

    /**
     * Updates the given sales order.
     *
     * @param salesOrder The sales order to be updated.
     * @return A list of potential messages that occurred during updating.
     */
    private List<WebServiceMessage> update(final SalesOrder salesOrder) {
        List<WebServiceMessage> messages = new ArrayList<WebServiceMessage>();
        SalesOrder databaseSalesOrder;

        try {
            databaseSalesOrder = this.salesOrderDAO.getSalesOrder(salesOrder.getId());
            this.salesOrderDAO.updateSalesOrder(salesOrder);
            this.inventoryController.updateMaterialInventory(salesOrder, databaseSalesOrder);
            this.paymentController.updateSalesOrderPayment(salesOrder, databaseSalesOrder);
            messages.add(new WebServiceMessage(WebServiceMessageType.S,
                    MessageFormat.format(this.resources.getString("salesOrder.updateSuccess"), salesOrder.getId())));
        } catch (ObjectUnchangedException objectUnchangedException) {
            messages.add(new WebServiceMessage(WebServiceMessageType.I,
                    MessageFormat.format(this.resources.getString("salesOrder.updateUnchanged"), salesOrder.getId())));
        } catch (Exception e) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("salesOrder.updateError"), salesOrder.getId())));

            LOGGER.error(MessageFormat.format(this.resources.getString("salesOrder.updateError"), salesOrder.getId()),
                    e);
        }

        return messages;
    }

    /**
     * Inserts the given sales order.
     *
     * @param salesOrder The sales order to be inserted.
     * @return A list of potential messages that occurred during adding.
     */
    private List<WebServiceMessage> add(final SalesOrder salesOrder) {
        List<WebServiceMessage> messages = new ArrayList<WebServiceMessage>();

        try {
            this.salesOrderDAO.insertSalesOrder(salesOrder);
            this.inventoryController.reduceMaterialInventory(salesOrder);
            messages.add(
                    new WebServiceMessage(WebServiceMessageType.S, this.resources.getString("salesOrder.addSuccess")));
        } catch (Exception e) {
            messages.add(
                    new WebServiceMessage(WebServiceMessageType.E, this.resources.getString("salesOrder.addError")));

            LOGGER.error(this.resources.getString("salesOrder.addError"), e);
        }

        return messages;
    }

    /**
     * Converts the lean SalesOrder representation that is provided by the WebService to the internal data model for
     * further processing.
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
     * Converts the head data of the sales order from the lean WebService representation to the internal data model of
     * the backend.
     *
     * @param salesOrderWS The lean sales order representation provided by the WebService.
     * @return The SalesOrder model that is used by the backend internally.
     * @throws Exception In case the conversion fails.
     */
    private SalesOrder convertSalesOrderHead(final SalesOrderWS salesOrderWS) throws Exception {
        BusinessPartnerDao partnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();
        AccountDao accountDAO = DAOManager.getInstance().getAccountDAO();
        SalesOrder salesOrder = new SalesOrder();

        // Basic object data that are copied as-is.
        salesOrder.setId(salesOrderWS.getSalesOrderId());
        salesOrder.setOrderDate(salesOrderWS.getOrderDate());
        salesOrder.setRequestedDeliveryDate(salesOrderWS.getRequestedDeliveryDate());
        salesOrder.setStatus(salesOrderWS.getStatus());

        // Object references. Only the ID is given and the whole backend object has to be loaded and referenced.
        if (salesOrderWS.getSoldToId() != null) {
            salesOrder.setSoldToParty(partnerDAO.getBusinessPartner(salesOrderWS.getSoldToId()));
        }

        if (salesOrderWS.getShipToId() != null) {
            salesOrder.setShipToParty(partnerDAO.getBusinessPartner(salesOrderWS.getShipToId()));
        }

        if (salesOrderWS.getBillToId() != null) {
            salesOrder.setBillToParty(partnerDAO.getBusinessPartner(salesOrderWS.getBillToId()));
        }

        if (salesOrderWS.getPaymentAccountId() != null) {
            salesOrder.setPaymentAccount(accountDAO.getAccount(salesOrderWS.getPaymentAccountId()));
        }

        return salesOrder;
    }

    /**
     * Converts the item data of the sales order from the lean WebService representation to the internal data model of
     * the backend.
     *
     * @param salesOrderWS The lean sales order representation provided by the WebService.
     * @param salesOrder   The converted sales order that is build based on the WebService representation.
     * @return A set of item models that is used by the backend internally.
     * @throws Exception In case the conversion fails.
     */
    private Set<SalesOrderItem> convertSalesOrderItems(final SalesOrderWS salesOrderWS, final SalesOrder salesOrder)
            throws Exception {
        Set<SalesOrderItem> orderItems = new HashSet<SalesOrderItem>();
        MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();

        for (SalesOrderItemWS itemWS : salesOrderWS.getItems()) {
            SalesOrderItem orderItem = new SalesOrderItem();
            orderItem.setId(itemWS.getItemId());
            orderItem.setMaterial(materialDAO.getMaterial(itemWS.getMaterialId()));
            orderItem.setQuantity(itemWS.getQuantity());
            orderItem.setSalesOrder(salesOrder);
            orderItems.add(orderItem);
        }

        return orderItems;
    }
}
