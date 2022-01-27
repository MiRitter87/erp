package backend.controller;

import backend.dao.AccountDao;
import backend.dao.DAOManager;
import backend.exception.ObjectUnchangedException;
import backend.model.account.Account;
import backend.model.account.Posting;
import backend.model.account.PostingType;
import backend.model.purchaseOrder.PurchaseOrder;
import backend.model.purchaseOrder.PurchaseOrderStatus;

/**
 * Manages the payment handling of the purchase order service.
 * 
 * Updating and deleting of purchase orders can influence the balance of the corresponding payment account.
 * This manager creates postings and updates the balance of the payment account on relevant purchase order status changes.
 * 
 * @author Michael
 */
public class PurchaseOrderPaymentController {
	/**
	 * Prefix of purchase order reference numbers used in postings.
	 */
	private static final String REFERENCE_PREFIX = "PO-";
	
	
	/**
	 * Gets the reference number for the given purchase order.
	 * 
	 * @param purchaseOrder The purchase order.
	 * @return The reference number.
	 */
	public static String getReferenceNumber(PurchaseOrder purchaseOrder) {
		StringBuilder referenceNumberBuilder = new StringBuilder();
		
		referenceNumberBuilder.append(REFERENCE_PREFIX);
		referenceNumberBuilder.append(purchaseOrder.getId());
		
		return referenceNumberBuilder.toString();
	}
	
	
	/**
	 * Updates the payment account and creates a posting if the purchase order is being updated.
	 * 
	 * @param purchaseOrder The purchase order being updated.
	 * @param databasePurchaseOrder The database state of the purchase order before the update has been performed.
	 * @throws Exception In case the update of the payment account or posting creation fails.
	 */
	public void updatePurchaseOrderPayment(final PurchaseOrder purchaseOrder, final PurchaseOrder databasePurchaseOrder) throws Exception {
		//If the purchase order status 'invoice settled' changes from inactive to active, 
		//a posting of payment disbursal is created and the total price is removed from the payment account.
		if(!databasePurchaseOrder.isStatusActive(PurchaseOrderStatus.INVOICE_SETTLED) && purchaseOrder.isStatusActive(PurchaseOrderStatus.INVOICE_SETTLED)) {
			this.reduceAccountBalance(purchaseOrder);
			return;
		}
		
		//If the purchase order status 'invoice settled' changes from inactive to active,
		//a posting of payment receipt is created and the total price is added to the payment account.
		if(databasePurchaseOrder.isStatusActive(PurchaseOrderStatus.INVOICE_SETTLED) && !purchaseOrder.isStatusActive(PurchaseOrderStatus.INVOICE_SETTLED)) {
			this.increaseAccountBalance(purchaseOrder);
			return;
		}
	}
	
	
	/**
	 * Removes the total price of the purchase order from the referenced payment account.
	 * 
	 * @param purchaseOrder The purchase order.
	 * @throws Exception Update of the account failed.
	 * @throws ObjectUnchangedException The account has not been changed.
	 */
	private void reduceAccountBalance(PurchaseOrder purchaseOrder) throws ObjectUnchangedException, Exception {
		AccountDao accountDAO = DAOManager.getInstance().getAccountDAO();
		Account paymentAccount = purchaseOrder.getPaymentAccount();
		
		paymentAccount.setBalance(purchaseOrder.getPaymentAccount().getBalance().subtract(purchaseOrder.getPriceTotal()));
		paymentAccount.getPostings().add(this.getPosting(purchaseOrder, PostingType.DISBURSAL));
		accountDAO.updateAccount(paymentAccount);
	}
	
	
	/**
	 * Increases the balance of the referenced payment account by the total price of the purchase order.
	 * 
	 * @param purchaseOrder The purchase order.
	 * @throws Exception Update of the account failed.
	 * @throws ObjectUnchangedException The account has not been changed.
	 */
	public void increaseAccountBalance(PurchaseOrder purchaseOrder) throws ObjectUnchangedException, Exception {
		AccountDao accountDAO = DAOManager.getInstance().getAccountDAO();
		Account paymentAccount = purchaseOrder.getPaymentAccount();
		
		paymentAccount.setBalance(purchaseOrder.getPaymentAccount().getBalance().add(purchaseOrder.getPriceTotal()));
		paymentAccount.getPostings().add(this.getPosting(purchaseOrder, PostingType.RECEIPT));
		accountDAO.updateAccount(paymentAccount);
	}
	
	
	/**
	 * Returns a posting with the given type for the purchase order.
	 * 
	 * @param purchaseOrder The purchase order for which the posting is created.
	 * @param type The type of the posting.
	 * @return The posting.
	 */
	private Posting getPosting(PurchaseOrder purchaseOrder, PostingType type) {
		Posting posting;
		
		posting = new Posting();
		posting.setType(type);
		posting.setCounterparty(purchaseOrder.getVendor());
		posting.setReferenceNumber(getReferenceNumber(purchaseOrder));
		posting.setAmount(purchaseOrder.getPriceTotal());
		posting.setCurrency(purchaseOrder.getPriceTotalCurrency());
		
		return posting;	
	}
}
