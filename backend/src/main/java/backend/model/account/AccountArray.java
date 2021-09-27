package backend.model.account;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * A list of accounts.
 * 
 * @author Michael
 */
public class AccountArray {
	/**
	 * A list of accounts.
	 */
	private List<Account> accounts = null;

	
	/**
	 * @return the accounts
	 */
	@XmlElementWrapper(name="accounts")
    @XmlElement(name="account")
	public List<Account> getAccounts() {
		return accounts;
	}

	
	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
