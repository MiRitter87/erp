package backend.model.businessPartner;

/**
 * Query Parameter for business partner selection. Specifies the business partners to be selected based on the type
 * attribute.
 *
 * @author Michael
 */
public enum BPTypeQueryParameter {
    /**
     * Select all business partners.
     */
    ALL,

    /**
     * Select only business partners of type customer.
     */
    CUSTOMER,

    /**
     * Select only business partners of type vendor.
     */
    VENDOR
}
