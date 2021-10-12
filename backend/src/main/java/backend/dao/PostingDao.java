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
}
