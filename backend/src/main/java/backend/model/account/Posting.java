package backend.model.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import backend.model.Currency;
import backend.model.businessPartner.BusinessPartner;

/**
 * A posting represents a payment to or from an account.
 * 
 * @author Michael
 *
 */
@Table(name="POSTING")
@Entity
@SequenceGenerator(name = "postingSequence", initialValue = 1, allocationSize = 1)
public class Posting {
	/**
	 * The distinct identification number.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postingSequence")
	@Column(name="POSTING_ID")
	@Min(value = 1, message = "{posting.id.min.message}")
	private Integer id;
	
	/**
	 * The posting type.
	 */
	@Column(name="TYPE", length = 10)
	@Enumerated(EnumType.STRING)
	@NotNull(message = "{posting.type.notNull.message}")
	private PostingType type;
	
	/**
	 * The date and time of the posting.
	 */
	@Column(name="TIMESTAMP")
	private Date timestamp;
	
	/**
	 * The counterparty.
	 */
	@OneToOne
	@JoinColumn(name="COUNTERPARTY_ID")
	@NotNull(message = "{posting.counterparty.notNull.message}")
	private BusinessPartner counterparty;
	
	/**
	 * The reference number.
	 */
	@Column(name="REFERENCE_NUMBER", length = 54)
	@Size(min = 0, max = 54, message = "{posting.referenceNumber.size.message}")
	private String referenceNumber;
	
	/**
	 * The payment amount.
	 */
	@Column(name="AMOUNT")
	@NotNull(message = "{posting.amount.notNull.message}")
	@DecimalMin(value = "0.01", inclusive = true, message = "{posting.amount.decimalMin.message}")
	private BigDecimal amount;
	
	/**
	 * The currency of the amount.
	 */
	@Column(name="CURRENCY", length = 3)
	@Enumerated(EnumType.STRING)
	@NotNull(message = "{posting.currency.notNull.message}")
	private Currency currency;
	
	
	/**
	 * Default constructor.
	 */
	public Posting() {
		this.timestamp = new Date();
	}
	
	
	/**
	 * Validates the posting.
	 * 
	 * @throws Exception In case a general validation error occurred.
	 */
	public void validate() throws Exception {
		this.validateAnnotations();
	}
	
	
	/**
	 * Validates the posting according to the annotations of the Validation Framework.
	 * 
	 * @exception Exception In case the validation failed.
	 */
	private void validateAnnotations() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Posting>> violations = validator.validate(this);
		
		for(ConstraintViolation<Posting> violation:violations) {
			throw new Exception(violation.getMessage());
		}
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
