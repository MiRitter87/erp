package backend.webservice.common;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.BusinessPartnerHibernateDao;
import backend.model.BusinessPartner;
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
	private BusinessPartnerHibernateDao businessPartnerDAO;
	
	/**
	 * Access to localized application resources.
	 */
	private ResourceBundle resources = ResourceBundle.getBundle("backend");
	
	/**
	 * Application logging.
	 */
	public static final Logger logger = LogManager.getLogger(BusinessPartnerService.class);
	
	
	/**
	 * Provides the business partner with the given id.
	 * 
	 * @param id The id of the business partner.
	 * @return The business partner with the given id, if found.
	 */
	public WebServiceResult getBusinessPartner(final Integer id) {
		return null;
	}
	
	
	/**
	 * Provides a list of all business partners.
	 * 
	 * @return A list of all business partners.
	 */
	public WebServiceResult getBusinessPartners() {
		return null;
	}
	
	
	/**
	 * Adds a business partner.
	 * 
	 * @param businessPartner The business partner to be added.
	 * @return The result of the add function.
	 */
	public WebServiceResult addBusinessPartner(final BusinessPartner businessPartner) {
		return null;
	}
	
	
	/**
	 * Deletes the business partner with the given id.
	 * 
	 * @param id The id of the business partner to be deleted.
	 * @return The result of the delete function.
	 */
	public WebServiceResult deleteBusinessPartner(final Integer id) {
		return null;
	}
	
	
	/**
	 * Updates an existing business partner.
	 * 
	 * @param businessPartner The business partner to be updated.
	 * @return The result of the update function.
	 */
	public WebServiceResult updateBusinessPartner(final BusinessPartner businessPartner) {
		return null;
	}
}
