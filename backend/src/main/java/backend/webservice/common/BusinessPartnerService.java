package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.BusinessPartnerDao;
import backend.dao.DAOManager;
import backend.exception.ObjectInUseException;
import backend.exception.ObjectUnchangedException;
import backend.model.account.Posting;
import backend.model.businessPartner.BPTypeQueryParameter;
import backend.model.businessPartner.BusinessPartner;
import backend.model.businessPartner.BusinessPartnerArray;
import backend.model.purchaseOrder.PurchaseOrder;
import backend.model.salesOrder.SalesOrder;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the BusinessPartner WebService that is used by the SOAP as well as the REST service.
 *
 * @author Michael
 */
public class BusinessPartnerService {
    /**
     * DAO for business partner access.
     */
    private BusinessPartnerDao businessPartnerDAO;

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources = ResourceBundle.getBundle("backend");

    /**
     * Application logging.
     */
    public static final Logger LOGGER = LogManager.getLogger(BusinessPartnerService.class);

    /**
     * Provides the business partner with the given id.
     *
     * @param id The id of the business partner.
     * @return The business partner with the given id, if found.
     */
    public WebServiceResult getBusinessPartner(final Integer id) {
        BusinessPartner businessPartner = null;
        WebServiceResult getBusinessPartnerResult = new WebServiceResult(null);

        try {
            this.businessPartnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();
            businessPartner = this.businessPartnerDAO.getBusinessPartner(id);

            if (businessPartner != null) {
                // Business partner found
                getBusinessPartnerResult.setData(businessPartner);
            } else {
                // Business partner not found
                getBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                        MessageFormat.format(this.resources.getString("businessPartner.notFound"), id)));
            }
        } catch (Exception e) {
            getBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("businessPartner.getError"), id)));

            LOGGER.error(MessageFormat.format(this.resources.getString("businessPartner.getError"), id), e);
        }

        return getBusinessPartnerResult;
    }

    /**
     * Provides a list of all business partners.
     *
     * @param bpTypeQuery The type of the business partners to be queried.
     * @return A list of all business partners.
     */
    public WebServiceResult getBusinessPartners(final BPTypeQueryParameter bpTypeQuery) {
        BusinessPartnerArray businessPartners = new BusinessPartnerArray();
        WebServiceResult getBusinessPartnersResult = new WebServiceResult(null);

        try {
            this.businessPartnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();
            businessPartners.setBusinessPartners(this.businessPartnerDAO.getBusinessPartners(bpTypeQuery));
            getBusinessPartnersResult.setData(businessPartners);
        } catch (Exception e) {
            getBusinessPartnersResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("businessPartner.getBusinessPartnersError")));

            LOGGER.error(this.resources.getString("businessPartner.getBusinessPartnersError"), e);
        }

        return getBusinessPartnersResult;
    }

    /**
     * Adds a business partner.
     *
     * @param businessPartner The business partner to be added.
     * @return The result of the add function.
     */
    public WebServiceResult addBusinessPartner(final BusinessPartner businessPartner) {
        WebServiceResult addBusinessPartnerResult = new WebServiceResult();
        this.businessPartnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();

        // Validate the given business partner.
        try {
            businessPartner.validate();
        } catch (Exception validationException) {
            addBusinessPartnerResult
                    .addMessage(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
            return addBusinessPartnerResult;
        }

        // Insert business partner if validation is successful.
        try {
            this.businessPartnerDAO.insertBusinessPartner(businessPartner);
            addBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.S,
                    this.resources.getString("businessPartner.addSuccess")));
        } catch (Exception e) {
            addBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("businessPartner.addError")));

            LOGGER.error(this.resources.getString("businessPartner.addError"), e);
        }

        return addBusinessPartnerResult;
    }

    /**
     * Deletes the business partner with the given id.
     *
     * @param id The id of the business partner to be deleted.
     * @return The result of the delete function.
     */
    public WebServiceResult deleteBusinessPartner(final Integer id) {
        BusinessPartner businessPartner = null;
        WebServiceResult deleteBusinessPartnerResult = new WebServiceResult(null);

        // Check if a business partner with the given id exists.
        try {
            this.businessPartnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();
            businessPartner = this.businessPartnerDAO.getBusinessPartner(id);

            if (businessPartner != null) {
                // Delete business partner if exists.
                this.businessPartnerDAO.deleteBusinessPartner(businessPartner);
                deleteBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.S,
                        MessageFormat.format(this.resources.getString("businessPartner.deleteSuccess"), id)));
            } else {
                // Business partner not found.
                deleteBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                        MessageFormat.format(this.resources.getString("businessPartner.notFound"), id)));
            }
        } catch (ObjectInUseException objectInUseException) {
            if (objectInUseException.getUsedByObject() instanceof SalesOrder) {
                deleteBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                        MessageFormat.format(this.resources.getString("businessPartner.deleteUsedInSalesOrder"), id,
                                objectInUseException.getUsedById())));
            }
            if (objectInUseException.getUsedByObject() instanceof PurchaseOrder) {
                deleteBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                        MessageFormat.format(this.resources.getString("businessPartner.deleteUsedInPurchaseOrder"), id,
                                objectInUseException.getUsedById())));
            }
            if (objectInUseException.getUsedByObject() instanceof Posting) {
                deleteBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                        MessageFormat.format(this.resources.getString("businessPartner.deleteUsedInPosting"), id,
                                objectInUseException.getUsedById())));
            }
        } catch (Exception e) {
            deleteBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("businessPartner.deleteError"), id)));

            LOGGER.error(MessageFormat.format(this.resources.getString("businessPartner.deleteError"), id), e);
        }

        return deleteBusinessPartnerResult;
    }

    /**
     * Updates an existing business partner.
     *
     * @param businessPartner The business partner to be updated.
     * @return The result of the update function.
     */
    public WebServiceResult updateBusinessPartner(final BusinessPartner businessPartner) {
        WebServiceResult updateBusinessPartnerResult = new WebServiceResult(null);
        this.businessPartnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();

        // Validation of the given business partner.
        try {
            businessPartner.validate();
        } catch (Exception validationException) {
            updateBusinessPartnerResult
                    .addMessage(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
            return updateBusinessPartnerResult;
        }

        // Update business partner if validation is successful.
        try {
            this.businessPartnerDAO.updateBusinessPartner(businessPartner);
            updateBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.S, MessageFormat
                    .format(this.resources.getString("businessPartner.updateSuccess"), businessPartner.getId())));
        } catch (ObjectUnchangedException objectUnchangedException) {
            updateBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.I, MessageFormat
                    .format(this.resources.getString("businessPartner.updateUnchanged"), businessPartner.getId())));
        } catch (Exception e) {
            updateBusinessPartnerResult.addMessage(new WebServiceMessage(WebServiceMessageType.E, MessageFormat
                    .format(this.resources.getString("businessPartner.updateError"), businessPartner.getId())));

            LOGGER.error(MessageFormat.format(this.resources.getString("businessPartner.updateError"),
                    businessPartner.getId()), e);
        }

        return updateBusinessPartnerResult;
    }
}
