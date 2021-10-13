package backend.dao;

import backend.model.account.Posting;

/**
 * Interface for posting persistence.
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
	void insertPosting(final Posting posting) throws Exception;
	
	
	/**
	 * Deletes a posting.
	 * 
	 * @param posting The posting to be deleted.
	 * @throws Exception Deletion failed.
	 */
	void deletePosting(final Posting posting) throws Exception;
	
	
	/**
	 * Gets the posting with the given id.
	 * 
	 * @param id The id of the posting.
	 * @return The posting with the given id.
	 * @throws Exception Posting retrieval failed.
	 */
	Posting getPosting(final Integer id) throws Exception;
}
