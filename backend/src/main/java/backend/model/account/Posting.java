package backend.model.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

import backend.model.Currency;
import backend.model.businessPartner.BusinessPartner;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * A posting represents a payment to or from an account.
 *
 * @author Michael
 *
 */
@Table(name = "POSTING")
@Entity
@SequenceGenerator(name = "postingSequence", initialValue = 1, allocationSize = 1)
public class Posting {
    /**
     * The maximum type field length allowed.
     */
    private static final int MAX_TYPE_LENGTH = 10;

    /**
     * The maximum reference number field length allowed.
     */
    private static final int MAX_REFERENCE_NUMBER_LENGTH = 54;

    /**
     * The maximum currency field length allowed.
     */
    private static final int MAX_CURRENCY_LENGTH = 3;

    /**
     * The distinct identification number.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postingSequence")
    @Column(name = "POSTING_ID")
    @Min(value = 1, message = "{posting.id.min.message}")
    private Integer id;

    /**
     * The posting type.
     */
    @Column(name = "TYPE", length = MAX_TYPE_LENGTH)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{posting.type.notNull.message}")
    private PostingType type;

    /**
     * The date and time of the posting.
     */
    @Column(name = "TIMESTAMP")
    private Date timestamp;

    /**
     * The counterparty.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTERPARTY_ID")
    @NotNull(message = "{posting.counterparty.notNull.message}")
    private BusinessPartner counterparty;

    /**
     * The reference number.
     */
    @Column(name = "REFERENCE_NUMBER", length = MAX_REFERENCE_NUMBER_LENGTH)
    @Size(min = 0, max = MAX_REFERENCE_NUMBER_LENGTH, message = "{posting.referenceNumber.size.message}")
    private String referenceNumber;

    /**
     * The payment amount.
     */
    @Column(name = "AMOUNT")
    @NotNull(message = "{posting.amount.notNull.message}")
    @DecimalMin(value = "0.01", inclusive = true, message = "{posting.amount.decimalMin.message}")
    private BigDecimal amount;

    /**
     * The currency of the amount.
     */
    @Column(name = "CURRENCY", length = MAX_CURRENCY_LENGTH)
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
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .buildValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Posting>> violations = validator.validate(this);

        for (ConstraintViolation<Posting> violation : violations) {
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
     * @return the type
     */
    public PostingType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final PostingType type) {
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
    public void setTimestamp(final Date timestamp) {
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
    public void setCounterparty(final BusinessPartner counterparty) {
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
    public void setReferenceNumber(final String referenceNumber) {
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
    public void setAmount(final BigDecimal amount) {
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
    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    /**
     * Calculates the hashCode of a Posting.
     */
    @Override
    public int hashCode() {
        return Objects.hash(amount, counterparty, currency, id, referenceNumber, timestamp, type);
    }

    /**
     * Indicates whether some other Posting is "equal to" this one.
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
        Posting other = (Posting) obj;

        if (timestamp == null && other.timestamp != null) {
            return false;
        }
        if (timestamp != null && other.timestamp == null) {
            return false;
        }
        if (timestamp != null && other.timestamp != null) {
            if (timestamp.getTime() != other.timestamp.getTime()) {
                return false;
            }
        }

        return Objects.equals(amount, other.amount) && Objects.equals(counterparty, other.counterparty)
                && currency == other.currency && Objects.equals(id, other.id)
                && Objects.equals(referenceNumber, other.referenceNumber) && type == other.type;
    }
}
