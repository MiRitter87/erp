package backend.model.account;

/**
 * The type of a posting.
 *
 * @author Michael
 */
public enum PostingType {
    /**
     * Incoming payment.
     */
    RECEIPT,

    /**
     * Outgoing payment.
     */
    DISBURSAL
}
