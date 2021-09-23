package backend.model.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import backend.model.Currency;

/**
 * An account represents the current cash balance of an entity.
 * 
 * @author Michael
 */
@Table(name="ACCOUNT")
@Entity
@SequenceGenerator(name = "accountSequence", initialValue = 1, allocationSize = 1)
public class Account {
	/**
	 * The distinct identification number.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountSequence")
	@Column(name="ACCOUNT_ID")
	@Min(value = 1, message = "{account.id.min.message}")
	private Integer id;
	
	/**
	 * The description.
	 */
	@Column(name="DESCRIPTION", length = 100)
	@NotNull(message = "{account.description.notNull.message}")
	@Size(min = 1, max = 100, message = "{account.description.size.message}")
	private String description;
	
	/**
	 * The current cash balance.
	 */
	@Column(name="BALANCE")
	@NotNull(message = "{account.balance.notNull.message}")
	private BigDecimal balance;
	
	/**
	 * The currency of the balance.
	 */
	@Column(name="CURRENCY", length = 3)
	@Enumerated(EnumType.STRING)
	@NotNull(message = "{account.currency.notNull.message}")
	private Currency currency;
	
	/**
	 * The postings of the account.
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="ACCOUNT_ID")
	private List<Posting> postings;
	
	
	/**
	 * Default constructor.
	 */
	public Account() {
		
	}
	
	
	/**
	 * Validates the account.
	 * 
	 * @throws Exception In case a general validation error occurred.
	 */
	public void validate() throws Exception {
		this.validateAnnotations();
	}
	
	
	/**
	 * Validates the account according to the annotations of the Validation Framework.
	 * 
	 * @exception Exception In case the validation failed.
	 */
	private void validateAnnotations() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Account>> violations = validator.validate(this);
		
		for(ConstraintViolation<Account> violation:violations) {
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


	/**
	 * @return the postings
	 */
	public List<Posting> getPostings() {
		return postings;
	}


	/**
	 * @param postings the postings to set
	 */
	public void setPostings(List<Posting> postings) {
		this.postings = postings;
	}
}
