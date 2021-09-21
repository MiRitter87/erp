package backend.model.account;

import java.math.BigDecimal;
import java.util.Date;

import backend.model.Currency;
import backend.model.businessPartner.BusinessPartner;

/**
 * A posting represents a payment to or from an account.
 * 
 * @author Michael
 *
 */
public class Posting {
	/**
	 * The distinct identification number.
	 */
	private Integer id;
	
	/**
	 * The posting type.
	 */
	private PostingType type;
	
	/**
	 * The date and time of the posting.
	 */
	private Date timestamp;
	
	/**
	 * The counterparty.
	 */
	private BusinessPartner counterparty;
	
	/**
	 * The reference number.
	 */
	private String referenceNumber;
	
	/**
	 * The payment amount.
	 */
	private BigDecimal amount;
	
	/**
	 * The currency of the amount.
	 */
	private Currency currency;
	
	
	/**
	 * Default constructor.
	 */
	public Posting() {
		
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
	 * @return the type
	 */
	public PostingType getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(PostingType type) {
		this.type = type;
	}


	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}


	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


	/**
	 * @return the counterparty
	 */
	public BusinessPartner getCounterparty() {
		return counterparty;
	}


	/**
	 * @param counterparty the counterparty to set
	 */
	public void setCounterparty(BusinessPartner counterparty) {
		this.counterparty = counterparty;
	}


	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}


	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}


	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}


	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
