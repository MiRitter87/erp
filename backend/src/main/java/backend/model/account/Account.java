package backend.model.account;

import java.math.BigDecimal;

import backend.model.Currency;

/**
 * An account represents the current cash balance of an entity.
 * 
 * @author Michael
 */
public class Account {
	/**
	 * The distinct identification number.
	 */
	private Integer id;
	
	/**
	 * The description.
	 */
	private String description;
	
	/**
	 * The current cash balance.
	 */
	private BigDecimal balance;
	
	/**
	 * The currency of the balance.
	 */
	private Currency currency;
	
	
	/**
	 * Default constructor.
	 */
	public Account() {
		
	}


	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}


	/**
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


	/**
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}


	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
