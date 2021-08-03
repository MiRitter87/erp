package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.BusinessPartner;
import backend.model.webservice.BPTypeQueryParameter;

/**
 * Interface for business partner persistence.
 * 
 * @author Michael
 */
public interface BusinessPartnerDao {
	/**
	 * Inserts a business partner.
	 * 
	 * @param businessPartner The business partner to be inserted.
	 * @throws Exception Insertion failed.
	 */
	void insertBusinessPartner(final BusinessPartner businessPartner) throws Exception;
	
	
	/**
	 * Deletes a business partner.
	 * 
	 * @param businessPartner The business partner to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deleteBusinessPartner(final BusinessPartner businessPartner) throws Exception;
	
	
	/**
	 * Gets all business partners.
	 * 
	 * @param bpTypeQuery The type of the business partners to be queried.
	 * @return All business partners.
	 * @throws Exception Business partner retrieval failed.
	 */
	List<BusinessPartner> getBusinessPartners(final BPTypeQueryParameter bpTypeQuery) throws Exception;
	
	
	/**
	 * Gets the business partner with the given id.
	 * 
	 * @param id The id of the business partner.
	 * @return The business partner with the given id.
	 * @throws Exception Business partner retrieval failed.
	 */
	BusinessPartner getBusinessPartner(final Integer id) throws Exception;
	
	
	/**
	 * Updates the given business partner.
	 * 
	 * @param businessPartner The business partner to be updated.
	 * @throws ObjectUnchangedException The business partner has not been changed.
	 * @throws Exception Business partner update failed.
	 */
	void updateBusinessPartner(final BusinessPartner businessPartner) throws ObjectUnchangedException, Exception;
}
