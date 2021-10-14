package backend.model.account;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name="ACCOUNT_ID")
	private Set<Posting> postings;
	
	
	/**
	 * Default constructor.
	 */
	public Account() {
		this.postings = new HashSet<Posting>();
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
	public Set<Posting> getPostings() {
		return postings;
	}


	/**
	 * @param postings the postings to set
	 */
	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((postings == null) ? 0 : postings.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Account other = (Account) obj;
		if (balance == null) {
			if (other.balance != null) {
				return false;
			}
		} else if (balance.compareTo(other.balance) != 0) {
			return false;
		}
		if (currency != other.currency) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		if(this.arePostingsEqual(other) == false)
			return false;

		return true;
	}
	
	
	/**
	 * Checks if the list of postings is equal.
	 * 
	 * @param other The other account for comparison.
	 * @return true, if postings are equal; false otherwise.
	 */
	private boolean arePostingsEqual(Account other) {
		if (this.postings == null && other.postings != null)
			return false;
		
		if (this.postings != null && other.postings == null)
			return false;
		
		if(this.postings.size() != other.postings.size())
			return false;
		
		for(Posting tempPosting:this.postings) {
			Posting otherPosting = other.getPostingWithId(tempPosting.getId());
			
			if(otherPosting == null)
				return false;
			
			if(!tempPosting.equals(otherPosting))
				return false;
		}
		
		return true;
	}
	
	
	/**
	 * Gets the posting with the given id.
	 * 
	 * @param id The id of the posting.
	 * @return The posting with the given id, if found.
	 */
	public Posting getPostingWithId(Integer id) {
		for(Posting tempPosting:this.postings) {
			if(tempPosting.getId() == id)
				return tempPosting;
		}
		
		return null;
	}
}
