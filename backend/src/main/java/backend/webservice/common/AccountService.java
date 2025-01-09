package backend.webservice.common;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import backend.dao.AccountDao;
import backend.dao.DAOManager;
import backend.exception.ObjectUnchangedException;
import backend.model.account.Account;
import backend.model.account.AccountArray;
import backend.model.webservice.WebServiceMessage;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;

/**
 * Common implementation of the account WebService that is used by the SOAP as well as the REST service.
 *
 * @author Michael
 */
public class AccountService {
    /**
     * DAO for account access.
     */
    private AccountDao accountDAO;

    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources = ResourceBundle.getBundle("backend");

    /**
     * Application logging.
     */
    public static final Logger LOGGER = LogManager.getLogger(AccountService.class);

    /**
     * Initializes the account service.
     */
    public AccountService() {
        this.accountDAO = DAOManager.getInstance().getAccountDAO();
    }

    /**
     * Provides the account with the given id.
     *
     * @param id The id of the account.
     * @return The account with the given id, if found.
     */
    public WebServiceResult getAccount(final Integer id) {
        Account account = null;
        WebServiceResult getAccountResult = new WebServiceResult(null);

        try {
            account = this.accountDAO.getAccount(id);

            if (account != null) {
                // Account found
                getAccountResult.setData(account);
            } else {
                // Account not found
                getAccountResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                        MessageFormat.format(this.resources.getString("account.notFound"), id)));
            }
        } catch (Exception e) {
            getAccountResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("account.getError"), id)));

            LOGGER.error(MessageFormat.format(this.resources.getString("account.getError"), id), e);
        }

        return getAccountResult;
    }

    /**
     * Provides a list of all accounts.
     *
     * @return A list of all accounts.
     */
    public WebServiceResult getAccounts() {
        AccountArray accounts = new AccountArray();
        WebServiceResult getAccountsResult = new WebServiceResult(null);

        try {
            accounts.setAccounts(this.accountDAO.getAccounts());
            getAccountsResult.setData(accounts);
        } catch (Exception e) {
            getAccountsResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    this.resources.getString("account.getAccountsError")));

            LOGGER.error(this.resources.getString("account.getAccountsError"), e);
        }

        return getAccountsResult;
    }

    /**
     * Adds an account.
     *
     * @param account The account to be added.
     * @return The result of the add function.
     */
    public WebServiceResult addAccount(final Account account) {
        WebServiceResult addAccountResult = new WebServiceResult();

        // Validate the given account.
        try {
            account.validate();
        } catch (Exception validationException) {
            addAccountResult
                    .addMessage(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
            return addAccountResult;
        }

        // Insert account if validation is successful.
        try {
            this.accountDAO.insertAccount(account);
            addAccountResult.addMessage(
                    new WebServiceMessage(WebServiceMessageType.S, this.resources.getString("account.addSuccess")));
            addAccountResult.setData(account.getId());
        } catch (Exception e) {
            addAccountResult.addMessage(
                    new WebServiceMessage(WebServiceMessageType.E, this.resources.getString("account.addError")));
            LOGGER.error(this.resources.getString("account.addError"), e);
        }

        return addAccountResult;
    }

    /**
     * Deletes the account with the given id.
     *
     * @param id The id of the account to be deleted.
     * @return The result of the delete function.
     */
    public WebServiceResult deleteAccount(final Integer id) {
        Account account = null;
        WebServiceResult deleteAccountResult = new WebServiceResult(null);

        // Check if an account with the given id exists.
        try {
            account = this.accountDAO.getAccount(id);

            if (account != null) {
                // Delete account if exists.
                this.accountDAO.deleteAccount(account);
                deleteAccountResult.addMessage(new WebServiceMessage(WebServiceMessageType.S,
                        MessageFormat.format(this.resources.getString("account.deleteSuccess"), id)));
            } else {
                // Account not found.
                deleteAccountResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                        MessageFormat.format(this.resources.getString("account.notFound"), id)));
            }
        } catch (Exception e) {
            deleteAccountResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("account.deleteError"), id)));

            LOGGER.error(MessageFormat.format(this.resources.getString("account.deleteError"), id), e);
        }

        return deleteAccountResult;
    }

    /**
     * Updates an existing account.
     *
     * @param account The account to be updated.
     * @return The result of the update function.
     */
    public WebServiceResult updateAccount(final Account account) {
        WebServiceResult updateAccountResult = new WebServiceResult(null);

        // Validation of the given account.
        try {
            account.validate();
        } catch (Exception validationException) {
            updateAccountResult
                    .addMessage(new WebServiceMessage(WebServiceMessageType.E, validationException.getMessage()));
            return updateAccountResult;
        }

        // Update account if validation is successful.
        try {
            this.accountDAO.updateAccount(account);
            updateAccountResult.addMessage(new WebServiceMessage(WebServiceMessageType.S,
                    MessageFormat.format(this.resources.getString("account.updateSuccess"), account.getId())));
        } catch (ObjectUnchangedException objectUnchangedException) {
            updateAccountResult.addMessage(new WebServiceMessage(WebServiceMessageType.I,
                    MessageFormat.format(this.resources.getString("account.updateUnchanged"), account.getId())));
        } catch (Exception e) {
            updateAccountResult.addMessage(new WebServiceMessage(WebServiceMessageType.E,
                    MessageFormat.format(this.resources.getString("account.updateError"), account.getId())));

            LOGGER.error(MessageFormat.format(this.resources.getString("account.updateError"), account.getId()), e);
        }

        return updateAccountResult;
    }
}
