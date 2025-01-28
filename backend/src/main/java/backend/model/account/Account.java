package backend.model.account;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

import backend.model.Currency;

/**
 * An account represents the current cash balance of an entity.
 *
 * @author Michael
 */
@Table(name = "ACCOUNT")
@Entity
@SequenceGenerator(name = "accountSequence", initialValue = 1, allocationSize = 1)
public class Account {
    /**
     * The maximum description field length allowed.
     */
    private static final int MAX_DESCRIPTION_LENGTH = 100;

    /**
     * The maximum currency field length allowed.
     */
    private static final int MAX_CURRENCY_LENGTH = 3;

    /**
     * The distinct identification number.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountSequence")
    @Column(name = "ACCOUNT_ID")
    @Min(value = 1, message = "{account.id.min.message}")
    private Integer id;

    /**
     * The description.
     */
    @Column(name = "DESCRIPTION", length = MAX_DESCRIPTION_LENGTH)
    @NotNull(message = "{account.description.notNull.message}")
    @Size(min = 1, max = MAX_DESCRIPTION_LENGTH, message = "{account.description.size.message}")
    private String description;

    /**
     * The current cash balance.
     */
    @Column(name = "BALANCE")
    @NotNull(message = "{account.balance.notNull.message}")
    private BigDecimal balance;

    /**
     * The currency of the balance.
     */
    @Column(name = "CURRENCY", length = MAX_CURRENCY_LENGTH)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{account.currency.notNull.message}")
    private Currency currency;

    /**
     * The postings of the account.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
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
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .buildValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Account>> violations = validator.validate(this);

        for (ConstraintViolation<Account> violation : violations) {
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
    public void setId(final Integer id) {
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
    public void setDescription(final String description) {
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
    public void setBalance(final BigDecimal balance) {
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
    public void setCurrency(final Currency currency) {
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
    public void setPostings(final Set<Posting> postings) {
        this.postings = postings;
    }

    /**
     * Calculates the hashCode of an Account.
     */
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

    /**
     * Indicates whether some other Account is "equal to" this one.
     */
    @Override
    public boolean equals(final Object obj) {
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

        if (!this.arePostingsEqual(other)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the list of postings is equal.
     *
     * @param other The other account for comparison.
     * @return true, if postings are equal; false otherwise.
     */
    private boolean arePostingsEqual(final Account other) {
        if (this.postings == null && other.postings != null) {
            return false;
        }

        if (this.postings != null && other.postings == null) {
            return false;
        }

        if (this.postings.size() != other.postings.size()) {
            return false;
        }

        for (Posting tempPosting : this.postings) {
            Posting otherPosting = other.getPostingWithId(tempPosting.getId());

            if (otherPosting == null) {
                return false;
            }

            if (!tempPosting.equals(otherPosting)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the posting with the given id.
     *
     * @param postingId The id of the posting.
     * @return The posting with the given id, if found.
     */
    public Posting getPostingWithId(final Integer postingId) {
        for (Posting tempPosting : this.postings) {
            if (tempPosting.getId() == postingId) {
                return tempPosting;
            }
        }

        return null;
    }
}
