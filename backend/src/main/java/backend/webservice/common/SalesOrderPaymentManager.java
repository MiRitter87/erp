package backend.webservice.common;

import backend.dao.AccountDao;
import backend.dao.DAOManager;
import backend.exception.ObjectUnchangedException;
import backend.model.account.Account;
import backend.model.account.Posting;
import backend.model.account.PostingType;
import backend.model.salesOrder.SalesOrder;
import backend.model.salesOrder.SalesOrderStatus;

/**
 * Manages the payment handling of the sales order service.
 * 
 * Updating and deleting of sales orders can influence the balance of the corresponding payment account.
 * This manager creates postings and updates the balance of the payment account on relevant sales order status changes.
 * 
 * @author Michael
 */
public class SalesOrderPaymentManager {
	/**
	 * Prefix of sales order reference numbers used in postings.
	 */
	private static final String REFERENCE_PREFIX = "SO-";
	
	
	/**
	 * Gets the reference number for the given sales order.
	 * 
	 * @param salesOrder The sales order.
	 * @return The reference number.
	 */
	public static String getReferenceNumber(SalesOrder salesOrder) {
		StringBuilder referenceNumberBuilder = new StringBuilder();
		
		referenceNumberBuilder.append(REFERENCE_PREFIX);
		referenceNumberBuilder.append(salesOrder.getId());
		
		return referenceNumberBuilder.toString();
	}
	
	
	/**
	 * Updates the payment account and creates a posting if the sales order is being updated.
	 * 
	 * @param salesOrder The sales order being updated.
	 * @param databaseSalesOrder The database state of the sales order before the update has been performed.
	 * @throws Exception In case the update of the payment account or posting creation fails.
	 */
	public void updateSalesOrderPayment(final SalesOrder salesOrder, final SalesOrder databaseSalesOrder) throws Exception {
		//If the sales order status changes to "Finished", a posting of payment receipt is created and the total price is added to the payment account.
		if(databaseSalesOrder.getStatus() != SalesOrderStatus.FINISHED && salesOrder.getStatus() == SalesOrderStatus.FINISHED) {
			this.addPaymentToAccount(salesOrder);
			return;
		}
		
		//If the sales order status "Finished" is reverted, a posting of payment disbursal is created and the total price is subtracted from the payment account.
		if(databaseSalesOrder.getStatus() == SalesOrderStatus.FINISHED && salesOrder.getStatus() != SalesOrderStatus.FINISHED) {
			this.removePaymentFromAccount(salesOrder);
			return;
		}
	}
	
	
	/**
	 * Adds the total price of the sales order to the referenced payment account.
	 * 
	 * @param salesOrder The sales order.
	 * @throws Exception Update of the account failed.
	 * @throws ObjectUnchangedException The account has not been changed.
	 */
	private void addPaymentToAccount(SalesOrder salesOrder) throws ObjectUnchangedException, Exception {
		AccountDao accountDAO = DAOManager.getInstance().getAccountDAO();
		Account paymentAccount = salesOrder.getPaymentAccount();
		
		paymentAccount.setBalance(salesOrder.getPaymentAccount().getBalance().add(salesOrder.getPriceTotal()));
		paymentAccount.getPostings().add(this.getPosting(salesOrder, PostingType.RECEIPT));
		accountDAO.updateAccount(paymentAccount);
	}
	
	
	/**
	 * Removes the total price of the sales order from the referenced payment account.
	 * 
	 * @param salesOrder The sales order.
	 * @throws Exception Update of the account failed.
	 * @throws ObjectUnchangedException The account has not been changed.
	 */
	public void removePaymentFromAccount(SalesOrder salesOrder) throws ObjectUnchangedException, Exception {
		AccountDao accountDAO = DAOManager.getInstance().getAccountDAO();
		Account paymentAccount = salesOrder.getPaymentAccount();
		
		paymentAccount.setBalance(salesOrder.getPaymentAccount().getBalance().subtract(salesOrder.getPriceTotal()));
		paymentAccount.getPostings().add(this.getPosting(salesOrder, PostingType.DISBURSAL));
		accountDAO.updateAccount(paymentAccount);
	}
	
	
	/**
	 * Returns a posting with the given type for the sales order.
	 * 
	 * @param salesOrder The sales order for which the posting is created.
	 * @param type The type of the posting.
	 * @return The posting.
	 */
	private Posting getPosting(SalesOrder salesOrder, PostingType type) {
		Posting posting;
		
		posting = new Posting();
		posting.setType(type);
		posting.setCounterparty(salesOrder.getBillToParty());
		posting.setReferenceNumber(getReferenceNumber(salesOrder));
		posting.setAmount(salesOrder.getPriceTotal());
		posting.setCurrency(salesOrder.getPriceTotalCurrency());
		
		return posting;	
	}
}
