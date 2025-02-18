package backend.webservice.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import backend.model.account.Account;
import backend.model.webservice.WebServiceResult;
import backend.webservice.common.AccountService;

/**
 * WebService for account access using REST technology..
 *
 * @author Michael
 */
@Path("/accounts")
public class AccountRestService {
    /**
     * Provides the account with the given ID.
     *
     * @param id The ID of the account.
     * @return The account with the given ID.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getAccount(@PathParam("id") final Integer id) {
        AccountService accountService = new AccountService();
        return accountService.getAccount(id);
    }

    /**
     * Provides a list of all accounts.
     *
     * @return A list of all accounts.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult getAccounts() {
        AccountService accountService = new AccountService();
        return accountService.getAccounts();
    }

    /**
     * Adds an account.
     *
     * @param account The account to be added.
     * @return The result of the add function.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult addAccount(final Account account) {
        AccountService accountService = new AccountService();
        return accountService.addAccount(account);
    }

    /**
     * Updates an existing account.
     *
     * @param account The account to be updated.
     * @return The result of the update function.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult updateAccount(final Account account) {
        AccountService accountService = new AccountService();
        return accountService.updateAccount(account);
    }

    /**
     * Deletes the account with the given id.
     *
     * @param id The id of the account to be deleted.
     * @return The result of the delete function.
     */
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WebServiceResult deleteAccount(@PathParam("id") final Integer id) {
        AccountService accountService = new AccountService();
        return accountService.deleteAccount(id);
    }
}
