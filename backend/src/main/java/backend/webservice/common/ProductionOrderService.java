package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.controller.ProductionOrderInventoryController;
import backend.dao.BillOfMaterialDao;
import backend.dao.DAOManager;
import backend.dao.MaterialDao;
import backend.dao.ProductionOrderDao;
import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;
import backend.exception.ObjectUnchangedException;
import backend.model.billOfMaterial.BillOfMaterial;
import backend.model.productionOrder.ProductionOrder;
import backend.model.productionOrder.ProductionOrderArray;
import backend.model.productionOrder.ProductionOrderItem;
import backend.model.productionOrder.ProductionOrderItemWS;
import backend.model.productionOrder.ProductionOrderStatus;
import backend.model.productionOrder.ProductionOrderWS;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;

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
     * Controller for material inventory.
     */
    private ProductionOrderInventoryController inventoryController;

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources = ResourceBundle.getBundle("backend");

    /**
     * Application logging.
     */
    public static final Logger LOGGER = LogManager.getLogger(ProductionOrderService.class);

    /**
     * Initializes the production order service.
     */
    public ProductionOrderService() {
        this.productionOrderDAO = DAOManager.getInstance().getProductionOrderDAO();
        this.inventoryController = new ProductionOrderInventoryController();
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

            if (productionOrder != null) {
                // Production order found
                getProductionOrderResult.setData(productionOrder);
            } else {
                // Production order not found
                getProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                        MessageFormat.format(this.resources.getString("productionOrder.notFound"), id)));
            }
        } catch (Exception e) {
            getProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("productionOrder.getError"), id)));

            LOGGER.error(MessageFormat.format(this.resources.getString("productionOrder.getError"), id), e);
        }

        return getProductionOrderResult;
    }

    /**
     * Provides a list of all production orders.
     *
     * orderStatusQuery Specifies the production orders to be selected based on the status.
     *
     * @param orderStatusQuery The ProductionOrderStatus of the requested production orders.
     * @return A list of all production orders.
     */
    public WebServiceResult getProductionOrders(final ProductionOrderStatus orderStatusQuery) {
        WebServiceResult getProductionOrdersResult = new WebServiceResult(null);
        ProductionOrderArray productionOrders = new ProductionOrderArray();

        try {
            productionOrders.setProductionOrders(this.productionOrderDAO.getProductionOrders(orderStatusQuery));
            getProductionOrdersResult.setData(productionOrders);
        } catch (Exception e) {
            getProductionOrdersResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("productionOrder.getProductionOrdersError")));

            LOGGER.error(this.resources.getString("productionOrder.getProductionOrdersError"), e);
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

        // Check if a production order with the given id exists.
        try {
            productionOrder = this.productionOrderDAO.getProductionOrder(id);

            if (productionOrder != null) {
                // Delete production order if exists.
                this.productionOrderDAO.deleteProductionOrder(productionOrder);

                this.inventoryController.updateMaterialInventoryOnOrderDeletion(productionOrder);

                deleteProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.S,
                        MessageFormat.format(this.resources.getString("productionOrder.deleteSuccess"), id)));
            } else {
                // Production order not found.
                deleteProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                        MessageFormat.format(this.resources.getString("productionOrder.notFound"), id)));
            }
        } catch (Exception e) {
            deleteProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("productionOrder.deleteError"), id)));

            LOGGER.error(MessageFormat.format(this.resources.getString("productionOrder.deleteError"), id), e);
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
        } catch (Exception exception) {
            updateProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, MessageFormat.format(
                    this.resources.getString("productionOrder.updateError"), convertedProductionOrder.getId())));
            LOGGER.error(MessageFormat.format(this.resources.getString("productionOrder.updateError"),
                    convertedProductionOrder.getId()), exception);
            return updateProductionOrderResult;
        }

        updateProductionOrderResult.addMessages(this.validate(convertedProductionOrder));
        updateProductionOrderResult.addMessages(this.validateUpdate(convertedProductionOrder));
        if (WebServiceTools.resultContainsErrorMessage(updateProductionOrderResult)) {
            return updateProductionOrderResult;
        }

        updateProductionOrderResult.addMessages(this.update(convertedProductionOrder));

        return updateProductionOrderResult;
    }

    /**
     * Adds a production order.
     *
     * @param productionOrder The production order to be added.
     * @return The result of the add function.
     */
    public WebServiceResult addProductionOrder(final ProductionOrderWS productionOrder) {
        ProductionOrder convertedProductionOrder = new ProductionOrder();
        WebServiceResult addProductionOrderResult = new WebServiceResult();

        try {
            convertedProductionOrder = this.convertProductionOrder(productionOrder);
        } catch (Exception exception) {
            addProductionOrderResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("productionOrder.addError")));
            LOGGER.error(this.resources.getString("productionOrder.addError"), exception);
            return addProductionOrderResult;
        }

        addProductionOrderResult.addMessages(this.validate(convertedProductionOrder));
        if (WebServiceTools.resultContainsErrorMessage(addProductionOrderResult)) {
            return addProductionOrderResult;
        }

        addProductionOrderResult.addMessages(this.add(convertedProductionOrder));
        addProductionOrderResult.setData(convertedProductionOrder.getId());

        return addProductionOrderResult;
    }

    /**
     * Converts the lean production order representation that is provided by the WebService to the internal data model
     * for further processing.
     *
     * @param productionOrderWS The lean production order representation provided by the WebService.
     * @return The ProductionOrder model that is used by the backend internally.
     * @throws Exception In case the conversion fails.
     */
    private ProductionOrder convertProductionOrder(final ProductionOrderWS productionOrderWS) throws Exception {
        ProductionOrder convertedProductionOrder;

        convertedProductionOrder = this.convertProductionOrderHead(productionOrderWS);
        convertedProductionOrder
                .setItems(this.convertProductionOrderItems(productionOrderWS, convertedProductionOrder));

        return convertedProductionOrder;
    }

    /**
     * Converts the head data of the production order from the lean WebService representation to the internal data model
     * of the backend.
     *
     * @param productionOrderWS The lean production order representation provided by the WebService.
     * @return The ProductionOrder model that is used by the backend internally.
     * @throws Exception In case the conversion fails.
     */
    private ProductionOrder convertProductionOrderHead(final ProductionOrderWS productionOrderWS) throws Exception {
        ProductionOrder productionOrder = new ProductionOrder();

        // Basic object data that are copied as-is.
        productionOrder.setId(productionOrderWS.getProductionOrderId());
        productionOrder.setOrderDate(productionOrderWS.getOrderDate());
        productionOrder.setPlannedExecutionDate(productionOrderWS.getPlannedExecutionDate());
        productionOrder.setExecutionDate(productionOrderWS.getExecutionDate());
        productionOrder.setStatus(productionOrderWS.getStatus());

        return productionOrder;
    }

    /**
     * Converts the item data of the production order from the lean WebService representation to the internal data model
     * of the backend.
     *
     * @param productionOrderWS The lean production order representation provided by the WebService.
     * @param productionOrder   The converted production order that is build based on the WebService representation.
     * @return A list of item models that is used by the backend internally.
     * @throws Exception In case the conversion fails.
     */
    private List<ProductionOrderItem> convertProductionOrderItems(final ProductionOrderWS productionOrderWS,
            final ProductionOrder productionOrder) throws Exception {
        List<ProductionOrderItem> orderItems = new ArrayList<ProductionOrderItem>();
        MaterialDao materialDAO = DAOManager.getInstance().getMaterialDAO();

        for (ProductionOrderItemWS itemWS : productionOrderWS.getItems()) {
            ProductionOrderItem orderItem = new ProductionOrderItem();
            orderItem.setId(itemWS.getItemId());
            orderItem.setMaterial(materialDAO.getMaterial(itemWS.getMaterialId()));
            orderItem.setQuantity(itemWS.getQuantity());
            orderItem.setProductionOrder(productionOrder);
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    /**
     * Validates the production order.
     *
     * @param productionOrder The production order to be validated.
     * @return A list of potential messages that occurred during validation.
     */
    private List<WebServiceMessage> validate(final ProductionOrder productionOrder) {
        List<WebServiceMessage> messages = new ArrayList<WebServiceMessage>();

        try {
            productionOrder.validate();
        } catch (NoItemsException noItemsException) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("productionOrder.noItemsGiven")));
        } catch (DuplicateIdentifierException duplicateIdentifierException) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("productionOrder.duplicateItemKey"),
                            productionOrder.getId(), duplicateIdentifierException.getDuplicateIdentifier())));
        } catch (Exception validationException) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
        }

        return messages;
    }

    /**
     * Validations of the production order only relevant during update method.
     *
     * @param productionOrder The production order to be validated.
     * @return A list of potential messages that occurred during validation.
     */
    private List<WebServiceMessage> validateUpdate(final ProductionOrder productionOrder) {
        List<WebServiceMessage> messages = new ArrayList<WebServiceMessage>();

        messages.addAll(this.checkBomsExist(productionOrder));
        messages.addAll(this.checkItemDataChangesForStatus(productionOrder));

        return messages;
    }

    /**
     * Checks if a bill of material exists for all items to be produced.
     *
     * @param productionOrder The production order to be validated.
     * @return A list of potential messages that occurred during check-up.
     */
    private List<WebServiceMessage> checkBomsExist(final ProductionOrder productionOrder) {
        List<WebServiceMessage> messages = new ArrayList<WebServiceMessage>();
        Integer idOfMaterialWithoutBom = 0;

        // At the time the order is being processed or finished, there has to be a bill of material for all the
        // materials that are going to be produced.
        if ((productionOrder.getStatus() == ProductionOrderStatus.IN_PROCESS
                || productionOrder.getStatus() == ProductionOrderStatus.FINISHED)) {
            try {
                idOfMaterialWithoutBom = this.getIdOfMaterialWithoutBom(productionOrder);
                if (idOfMaterialWithoutBom > 0) {
                    messages.add(new WebServiceMessage(WebServiceMessageType.E, MessageFormat
                            .format(this.resources.getString("productionOrder.updateNoBom"), idOfMaterialWithoutBom)));
                }
            } catch (Exception e) {
                messages.add(new WebServiceMessage(WebServiceMessageType.E, MessageFormat
                        .format(this.resources.getString("productionOrder.updateError"), productionOrder.getId())));
                LOGGER.error(MessageFormat.format(this.resources.getString("productionOrder.updateError"),
                        productionOrder.getId()), e);
            }
        }

        return messages;
    }

    /**
     * Checks if item data changes are valid based on the current production order status.
     *
     * @param productionOrder The production order to be validated.
     * @return A list of potential messages that occurred during check-up.
     */
    private List<WebServiceMessage> checkItemDataChangesForStatus(final ProductionOrder productionOrder) {
        List<WebServiceMessage> messages = new ArrayList<WebServiceMessage>();
        ProductionOrder databaseProductionOrder;
        ProductionOrderItem databaseProductionOrderItem;

        // Item data changes are only allowed in status 'OPEN' and 'IN_PROCESS'.
        if (productionOrder.getStatus() == ProductionOrderStatus.OPEN
                || productionOrder.getStatus() == ProductionOrderStatus.IN_PROCESS) {
            return messages; // No further checks needed.
        }

        try {
            databaseProductionOrder = this.productionOrderDAO.getProductionOrder(productionOrder.getId());

            // The number of items has changed.
            if (databaseProductionOrder.getItems().size() != productionOrder.getItems().size()) {
                messages.add(new WebServiceMessage(WebServiceMessageType.E,
                        this.resources.getString("productionOrder.updateItemWrongStatus")));
                return messages;
            }

            for (ProductionOrderItem orderItem : productionOrder.getItems()) {
                databaseProductionOrderItem = databaseProductionOrder.getItemWithId(orderItem.getId());
                this.validateItemChanges(messages, orderItem, databaseProductionOrderItem);
            }
        } catch (Exception e) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E, MessageFormat
                    .format(this.resources.getString("productionOrder.updateError"), productionOrder.getId())));
            LOGGER.error(MessageFormat.format(this.resources.getString("productionOrder.updateError"),
                    productionOrder.getId()), e);
        }

        return messages;
    }

    /**
     * Validates changes of ProductionOrderItem data based on the current production order status.
     *
     * @param messages                    A List of potential validation messages.
     * @param orderItem                   The updated ProductionOrderItem.
     * @param databaseProductionOrderItem The current database state of the updated ProductionOrderItem.
     */
    private void validateItemChanges(final List<WebServiceMessage> messages, final ProductionOrderItem orderItem,
            final ProductionOrderItem databaseProductionOrderItem) {
        // The production order has an item defined that did not exist before.
        if (databaseProductionOrderItem == null) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("productionOrder.updateItemWrongStatus")));
            return;
        }

        // Compare the material of the order items. Database state and current state are compared.
        if (!orderItem.getMaterial().getId().equals(databaseProductionOrderItem.getMaterial().getId())) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("productionOrder.updateMaterialWrongStatus")));
            return;
        }

        // Compare the quantity of the order items. Database state and current state are compared.
        if (orderItem.getQuantity() != databaseProductionOrderItem.getQuantity()) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("productionOrder.updateQuantityWrongStatus")));
        }
    }

    /**
     * Checks if a bill of material exists for each production order item. If the production order uses a material where
     * no corresponding BOM exists, the ID of the material is returned.
     *
     * @param productionOrder The production order to be validated.
     * @return 0, if all materials have a BOM defined; the ID of the material without corresponding BOM.
     * @throws Exception In case the bill of material determination for the production order items fails.
     */
    private Integer getIdOfMaterialWithoutBom(final ProductionOrder productionOrder) throws Exception {
        BillOfMaterialDao billOfMaterialDAO = DAOManager.getInstance().getBillOfMaterialDAO();
        List<BillOfMaterial> billOfMaterials;

        for (ProductionOrderItem item : productionOrder.getItems()) {
            billOfMaterials = billOfMaterialDAO.getBillOfMaterials(item.getMaterial());
            if (billOfMaterials.size() == 0) {
                return item.getMaterial().getId();
            }
        }

        return 0;
    }

    /**
     * Updates the given production order.
     *
     * @param productionOrder The production order to be updated.
     * @return A list of potential messages that occurred during updating.
     */
    private List<WebServiceMessage> update(final ProductionOrder productionOrder) {
        List<WebServiceMessage> messages = new ArrayList<WebServiceMessage>();
        ProductionOrder databaseProductionOrder;

        try {
            databaseProductionOrder = this.productionOrderDAO.getProductionOrder(productionOrder.getId());
            this.productionOrderDAO.updateProductionOrder(productionOrder);
            this.inventoryController.updateMaterialInventoryOnOrderUpdate(productionOrder, databaseProductionOrder);
            messages.add(new WebServiceMessage(WebServiceMessageType.S, MessageFormat
                    .format(this.resources.getString("productionOrder.updateSuccess"), productionOrder.getId())));
        } catch (ObjectUnchangedException objectUnchangedException) {
            messages.add(new WebServiceMessage(WebServiceMessageType.I, MessageFormat
                    .format(this.resources.getString("productionOrder.updateUnchanged"), productionOrder.getId())));
        } catch (Exception e) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E, MessageFormat
                    .format(this.resources.getString("productionOrder.updateError"), productionOrder.getId())));

            LOGGER.error(MessageFormat.format(this.resources.getString("productionOrder.updateError"),
                    productionOrder.getId()), e);
        }

        return messages;
    }

    /**
     * Inserts the given production order.
     *
     * @param productionOrder The production order to be inserted.
     * @return A list of potential messages that occurred during adding.
     */
    private List<WebServiceMessage> add(final ProductionOrder productionOrder) {
        List<WebServiceMessage> messages = new ArrayList<WebServiceMessage>();

        try {
            this.productionOrderDAO.insertProductionOrder(productionOrder);
            messages.add(new WebServiceMessage(WebServiceMessageType.S,
                    this.resources.getString("productionOrder.addSuccess")));
        } catch (Exception e) {
            messages.add(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("productionOrder.addError")));
            LOGGER.error(this.resources.getString("productionOrder.addError"), e);
        }

        return messages;
    }
}
