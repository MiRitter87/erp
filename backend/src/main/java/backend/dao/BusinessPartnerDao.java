package backend.dao;

import java.util.List;

import backend.exception.ObjectInUseException;
import backend.exception.ObjectUnchangedException;
import backend.model.businessPartner.BPTypeQueryParameter;
import backend.model.businessPartner.BusinessPartner;

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
    void insertBusinessPartner(BusinessPartner businessPartner) throws Exception;

    /**
     * Deletes a business partner.
     *
     * @param businessPartner The business partner to be deleted.
     * @throws ObjectInUseException The business partner is being used by another object.
     * @throws Exception            Deletion failed.
     */
    void deleteBusinessPartner(BusinessPartner businessPartner) throws ObjectInUseException, Exception;

    /**
     * Gets all business partners.
     *
     * @param bpTypeQuery The type of the business partners to be queried.
     * @return All business partners.
     * @throws Exception Business partner retrieval failed.
     */
    List<BusinessPartner> getBusinessPartners(BPTypeQueryParameter bpTypeQuery) throws Exception;

    /**
     * Gets the business partner with the given id.
     *
     * @param id The id of the business partner.
     * @return The business partner with the given id.
     * @throws Exception Business partner retrieval failed.
     */
    BusinessPartner getBusinessPartner(Integer id) throws Exception;

    /**
     * Updates the given business partner.
     *
     * @param businessPartner The business partner to be updated.
     * @throws ObjectUnchangedException The business partner has not been changed.
     * @throws Exception                Business partner update failed.
     */
    void updateBusinessPartner(BusinessPartner businessPartner) throws ObjectUnchangedException, Exception;
}
