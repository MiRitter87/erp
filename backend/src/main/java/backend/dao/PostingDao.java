package backend.dao;

import backend.model.account.Posting;

/**
 * Interface for Posting persistence.
 *
 * @author Michael
 */
public interface PostingDao {
    /**
     * Inserts a posting.
     *
     * @param posting The posting to be inserted.
     * @throws Exception Insertion failed.
     */
    void insertPosting(Posting posting) throws Exception;

    /**
     * Deletes a posting.
     *
     * @param posting The posting to be deleted.
     * @throws Exception Deletion failed.
     */
    void deletePosting(Posting posting) throws Exception;

    /**
     * Gets the posting with the given id.
     *
     * @param id The id of the posting.
     * @return The posting with the given id.
     * @throws Exception Posting retrieval failed.
     */
    Posting getPosting(Integer id) throws Exception;
}
