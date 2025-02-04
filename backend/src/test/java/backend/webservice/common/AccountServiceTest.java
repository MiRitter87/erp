package backend.webservice.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import backend.dao.AccountDao;
import backend.dao.BusinessPartnerDao;
import backend.dao.DAOManager;
import backend.dao.PostingDao;
import backend.model.Currency;
import backend.model.account.Account;
import backend.model.account.AccountArray;
import backend.model.account.Posting;
import backend.model.account.PostingType;
import backend.model.businessPartner.BusinessPartner;
import backend.model.businessPartner.BusinessPartnerType;
import backend.model.webservice.WebServiceMessageType;
import backend.model.webservice.WebServiceResult;
import backend.tools.WebServiceTools;
import backend.tools.test.ValidationMessageProvider;

/**
 * Tests the account service.
 *
 * @author Michael
 */
public class AccountServiceTest {
    /**
     * Access to localized application resources.
     */
    private ResourceBundle resources = ResourceBundle.getBundle("backend");

    /**
     * DAO to access account data.
     */
    private static AccountDao accountDAO;

    /**
     * DAO to access posting data.
     */
    private static PostingDao postingDAO;

    /**
     * DAO to access business partner data.
     */
    private static BusinessPartnerDao partnerDAO;

    /**
     * The first account under test.
     */
    private Account account1;

    /**
     * The second account under test.
     */
    private Account account2;

    /**
     * The posting under test.
     */
    private Posting posting;

    /**
     * The counterparty of the posting.
     */
    private BusinessPartner counterparty;

    @BeforeAll
    /**
     * Tasks to be performed once at startup of test class.
     */
    public static void setUpClass() {
        accountDAO = DAOManager.getInstance().getAccountDAO();
        postingDAO = DAOManager.getInstance().getPostingDAO();
        partnerDAO = DAOManager.getInstance().getBusinessPartnerDAO();
    }

    @AfterAll
    /**
     * Tasks to be performed once at end of test class.
     */
    public static void tearDownClass() {
        try {
            DAOManager.getInstance().close();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @BeforeEach
    /**
     * Tasks to be performed before each test is run.
     */
    private void setUp() {
        this.createDummyCounterparty();
        this.createDummyAccounts();
        this.createDummyPosting();
        this.addPostingToAccount2();
    }

    @AfterEach
    /**
     * Tasks to be performed after each test has been run.
     */
    private void tearDown() {
        this.deleteDummyPosting();
        this.deleteDummyAccounts();
        this.deleteDummyCounterparty();
    }

    /**
     * Initializes the database with dummy accounts.
     */
    private void createDummyAccounts() {
        this.account1 = this.getDummyAccount1();
        this.account2 = this.getDummyAccount2();

        try {
            accountDAO.insertAccount(this.account1);
            accountDAO.insertAccount(this.account2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Initializes the database with a dummy counterparty.
     */
    private void createDummyCounterparty() {
        this.counterparty = new BusinessPartner();
        this.counterparty.setCompanyName("A Company Making Everything");
        this.counterparty.setFirstName("Wile E.");
        this.counterparty.setLastName("Coyote");
        this.counterparty.setStreetName("Desert Road");
        this.counterparty.setHouseNumber("2");
        this.counterparty.setZipCode("1111");
        this.counterparty.setCityName("Durango");
        this.counterparty.setPhoneNumber("+1 456-125-7");
        this.counterparty.addType(BusinessPartnerType.VENDOR);

        try {
            partnerDAO.insertBusinessPartner(this.counterparty);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Removes the dummy counterparty from the database.
     */
    private void deleteDummyCounterparty() {
        try {
            partnerDAO.deleteBusinessPartner(this.counterparty);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Initializes the database with a dummy posting.
     */
    private void createDummyPosting() {
        this.posting = new Posting();
        this.posting.setType(PostingType.DISBURSAL);
        this.posting.setTimestamp(new Date());
        this.posting.setCounterparty(this.counterparty);
        this.posting.setReferenceNumber("A1234");
        this.posting.setAmount(BigDecimal.valueOf(99.95));
        this.posting.setCurrency(Currency.EUR);

        try {
            postingDAO.insertPosting(this.posting);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Removes the dummy posting from the database.
     */
    private void deleteDummyPosting() {
        try {
            postingDAO.deletePosting(this.posting);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Adds a Posting to Account 2.
     */
    private void addPostingToAccount2() {
        this.account2.getPostings().add(this.posting);

        try {
            accountDAO.updateAccount(this.account2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Initializes and returns dummy account 1.
     *
     * @return Dummy account 1.
     */
    private Account getDummyAccount1() {
        Account account;

        account = new Account();
        account.setDescription("Account 1");
        account.setBalance(BigDecimal.valueOf(1500));
        account.setCurrency(Currency.EUR);

        return account;
    }

    /**
     * Initializes and returns dummy account 2.
     *
     * @return Dummy account 2.
     */
    private Account getDummyAccount2() {
        Account account;

        account = new Account();
        account.setDescription("Account 2");
        account.setBalance(BigDecimal.valueOf(9999.23));
        account.setCurrency(Currency.EUR);

        return account;
    }

    /**
     * Deletes the dummy accounts from the database.
     */
    private void deleteDummyAccounts() {
        try {
            accountDAO.deleteAccount(this.account2);
            accountDAO.deleteAccount(this.account1);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    /**
     * Tests the retrieval of an account.
     */
    public void testGetAccount() {
        WebServiceResult getAccountResult;
        Account account;
        Posting posting;

        // Get the account
        AccountService service = new AccountService();
        getAccountResult = service.getAccount(this.account2.getId());

        // Assure no error message exists
        assertTrue(WebServiceTools.resultContainsErrorMessage(getAccountResult) == false);

        // Assure that an account is returned
        assertTrue(getAccountResult.getData() instanceof Account);

        account = (Account) getAccountResult.getData();

        // Check each attribute of the account
        assertEquals(account.getId(), this.account2.getId());
        assertEquals(account.getDescription(), this.account2.getDescription());
        assertTrue(account.getBalance().compareTo(this.account2.getBalance()) == 0);
        assertEquals(account.getCurrency(), this.account2.getCurrency());
        assertEquals(1, account.getPostings().size());

        // Read data of the referenced Posting
        posting = account.getPostingWithId(this.posting.getId());
        assertEquals(this.posting.getCounterparty().getCityName(), posting.getCounterparty().getCityName());
    }

    @Test
    /**
     * Tests the retrieval of an account that does not exist.
     */
    public void testGetNonExistentAccount() {
        Integer accountId = this.account2.getId() + 1;
        WebServiceResult getAccountResult;
        String expectedErrorMessage, actualErrorMessage;

        // Get the account
        AccountService service = new AccountService();
        getAccountResult = service.getAccount(accountId);

        // Assure that no account is returned
        assertNull(getAccountResult.getData());

        // There should be a return message of type E.
        assertTrue(getAccountResult.getMessages().size() == 1);
        assertTrue(getAccountResult.getMessages().get(0).getType() == WebServiceMessageType.E);

        // Verify the expected error message.
        expectedErrorMessage = MessageFormat.format(this.resources.getString("account.notFound"), accountId);
        actualErrorMessage = getAccountResult.getMessages().get(0).getText();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    /**
     * Tests the retrieval of all accounts.
     */
    public void testGetAllAccounts() {
        WebServiceResult getAccountsResult;
        AccountArray accounts;
        Account account;
        Posting posting;

        // Get the accounts.
        AccountService service = new AccountService();
        getAccountsResult = service.getAccounts();
        accounts = (AccountArray) getAccountsResult.getData();

        // Assure no error message exists
        assertTrue(WebServiceTools.resultContainsErrorMessage(getAccountsResult) == false);

        // Check if two accounts are returned.
        assertTrue(accounts.getAccounts().size() == 2);

        // Check both accounts by each attribute
        account = accounts.getAccounts().get(0);
        assertEquals(account.getId(), this.account1.getId());
        assertEquals(account.getDescription(), this.account1.getDescription());
        assertTrue(account.getBalance().compareTo(this.account1.getBalance()) == 0);
        assertEquals(account.getCurrency(), this.account1.getCurrency());

        account = accounts.getAccounts().get(1);
        assertEquals(account.getId(), this.account2.getId());
        assertEquals(account.getDescription(), this.account2.getDescription());
        assertTrue(account.getBalance().compareTo(this.account2.getBalance()) == 0);
        assertEquals(account.getCurrency(), this.account2.getCurrency());

        // Read data of the referenced Posting
        posting = account.getPostingWithId(this.posting.getId());
        assertEquals(this.posting.getCounterparty().getCityName(), posting.getCounterparty().getCityName());
    }

    @Test
    /**
     * Tests deletion of an account.
     */
    public void testDeleteAccount() {
        WebServiceResult deleteAccountResult;
        Account deletedAccount;

        try {
            // Delete account 1 using the service.
            AccountService service = new AccountService();
            deleteAccountResult = service.deleteAccount(this.account1.getId());

            // There should be no error messages
            assertTrue(WebServiceTools.resultContainsErrorMessage(deleteAccountResult) == false);

            // There should be a success message
            assertTrue(deleteAccountResult.getMessages().size() == 1);
            assertTrue(deleteAccountResult.getMessages().get(0).getType() == WebServiceMessageType.S);

            // Check if account 1 is missing using the DAO.
            deletedAccount = accountDAO.getAccount(this.account1.getId());

            if (deletedAccount != null)
                fail("Account 1 is still persisted but should have been deleted by the WebService operation 'deleteAccount'.");
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            // Restore old database state by adding the account that has been deleted previously.
            try {
                this.account1 = this.getDummyAccount1();
                accountDAO.insertAccount(this.account1);
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }
    }

    @Test
    /**
     * Tests updating an account with valid data.
     */
    public void testUpdateValidAccount() {
        WebServiceResult updateAccountResult;
        Account updatedAccount;
        AccountService service = new AccountService();

        // Update the requested delivery date.
        this.account1.setDescription("Account 1 - editiert");
        updateAccountResult = service.updateAccount(this.account1);

        // Assure no error message exists
        assertTrue(WebServiceTools.resultContainsErrorMessage(updateAccountResult) == false);

        // There should be a success message
        assertTrue(updateAccountResult.getMessages().size() == 1);
        assertTrue(updateAccountResult.getMessages().get(0).getType() == WebServiceMessageType.S);

        // Retrieve the updated account and check if the changes have been persisted.
        try {
            updatedAccount = accountDAO.getAccount(this.account1.getId());
            assertEquals(this.account1.getDescription(), updatedAccount.getDescription());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    /**
     * Tests updating an account with invalid data.
     */
    public void testUpdateInvalidAccount() {
        WebServiceResult updateAccountResult;
        AccountService service = new AccountService();
        ValidationMessageProvider messageProvider = new ValidationMessageProvider();
        String actualErrorMessage, expectedErrorMessage;

        // Remove the currency.
        this.account1.setCurrency(null);
        updateAccountResult = service.updateAccount(this.account1);

        // There should be a return message of type E.
        assertTrue(updateAccountResult.getMessages().size() == 1);
        assertTrue(updateAccountResult.getMessages().get(0).getType() == WebServiceMessageType.E);

        // A proper message should be provided.
        expectedErrorMessage = messageProvider.getNotNullValidationMessage("account", "currency");
        actualErrorMessage = updateAccountResult.getMessages().get(0).getText();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    /**
     * Tests updating an accout without changing any data.
     */
    public void testUpdateUnchangedAccount() {
        WebServiceResult updateAccountResult;
        AccountService service = new AccountService();
        String actualErrorMessage, expectedErrorMessage;

        // Update purchase order without changing any data.
        updateAccountResult = service.updateAccount(this.account1);

        // There should be a return message of type I
        assertTrue(updateAccountResult.getMessages().size() == 1);
        assertTrue(updateAccountResult.getMessages().get(0).getType() == WebServiceMessageType.I);

        // A proper message should be provided.
        expectedErrorMessage = MessageFormat.format(this.resources.getString("account.updateUnchanged"),
                this.account1.getId());
        actualErrorMessage = updateAccountResult.getMessages().get(0).getText();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    /**
     * Tests adding of a new account.
     */
    public void testAddValidAccount() {
        Account newAccount = new Account();
        Account addedAccount;
        WebServiceResult addAccountResult;
        AccountService service = new AccountService();

        // Define the new account
        newAccount.setDescription("Neues Konto");
        newAccount.setBalance(BigDecimal.valueOf(0));
        newAccount.setCurrency(Currency.EUR);

        // Add a new account to the database via WebService
        addAccountResult = service.addAccount(newAccount);

        // Assure no error message exists
        assertTrue(WebServiceTools.resultContainsErrorMessage(addAccountResult) == false);

        // There should be a success message
        assertTrue(addAccountResult.getMessages().size() == 1);
        assertTrue(addAccountResult.getMessages().get(0).getType() == WebServiceMessageType.S);

        // The ID of the newly created account should be provided in the data part of the WebService return.
        assertNotNull(addAccountResult.getData());
        assertTrue(addAccountResult.getData() instanceof Integer);
        newAccount.setId((Integer) addAccountResult.getData());

        // Read the persisted account via DAO
        try {
            addedAccount = accountDAO.getAccount(newAccount.getId());

            // Check if the account read by the DAO equals the account inserted using the WebService in each attribute.
            assertEquals(newAccount.getId(), addedAccount.getId());
            assertEquals(newAccount.getDescription(), addedAccount.getDescription());
            assertTrue(newAccount.getBalance().compareTo(addedAccount.getBalance()) == 0);
            assertEquals(newAccount.getCurrency(), addedAccount.getCurrency());
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            // Delete the newly added account.
            try {
                accountDAO.deleteAccount(newAccount);
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }
    }

    @Test
    /**
     * Tests adding of an invalid account.
     */
    public void testAddInvalidAccount() {
        Account newAccount = new Account();
        WebServiceResult addAccountResult;
        AccountService service = new AccountService();

        // Define the new account without a currency
        newAccount.setDescription("New Account without Currency");
        newAccount.setBalance(BigDecimal.valueOf(1));

        // Add a new account to the database via WebService
        addAccountResult = service.addAccount(newAccount);

        // There should be a return message of type E.
        assertTrue(addAccountResult.getMessages().size() == 1);
        assertTrue(addAccountResult.getMessages().get(0).getType() == WebServiceMessageType.E);

        // The new account should not have been persisted
        assertNull(newAccount.getId());
    }

    /*
     * TODO implement additional tests.
     *
     * add assertEquals of all postings to all relevant tests (get, add)
     */
}
