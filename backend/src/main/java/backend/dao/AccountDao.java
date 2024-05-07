package backend.dao;

import java.util.List;

import backend.exception.ObjectUnchangedException;
import backend.model.account.Account;

/**
 * Interface for account persistence..
 *
 * @author Michael
 */
public interface AccountDao {
    /**
     * Inserts an account.
     *
     * @param account The account to be inserted.
     * @throws Exception Insertion failed.
     */
    void insertAccount(Account account) throws Exception;

    /**
     * Deletes an account.
     *
     * @param account The account to be deleted.
     * @throws Exception Deletion failed.
     */
    void deleteAccount(Account account) throws Exception;

    /**
     * Gets all accounts.
     *
     * @return All accounts.
     * @throws Exception Account retrieval failed.
     */
    List<Account> getAccounts() throws Exception;

    /**
     * Gets the account with the given id.
     *
     * @param id The id of the account.
     * @return The account with the given id.
     * @throws Exception Account retrieval failed.
     */
    Account getAccount(Integer id) throws Exception;

    /**
     * Updates the given account.
     *
     * @param account The account to be updated.
     * @throws Exception Account update failed.
     */
    void updateAccount(Account account) throws ObjectUnchangedException, Exception;
}
