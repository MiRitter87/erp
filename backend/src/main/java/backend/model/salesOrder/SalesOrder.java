package backend.model.salesOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;
import backend.exception.QuantityExceedsInventoryException;
import backend.model.Currency;
import backend.model.account.Account;
import backend.model.businessPartner.BusinessPartner;

/**
 * Represents an order issued by a customer.
 *
 * @author Michael
 */
@Table(name = "SALES_ORDER")
@Entity
@SequenceGenerator(name = "salesOrderSequence", initialValue = 1, allocationSize = 1)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SalesOrder {
    /**
     * The maximum status field length allowed.
     */
    private static final int MAX_STATUS_LENGTH = 10;

    /**
     * The ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesOrderSequence")
    @Column(name = "SALES_ORDER_ID")
    @Min(value = 1, message = "{salesOrder.id.min.message}")
    private Integer id;

    /**
     * The purchaser.
     */
    @OneToOne
    @JoinColumn(name = "SOLD_TO_ID")
    @NotNull(message = "{salesOrder.soldToParty.notNull.message}")
    private BusinessPartner soldToParty;

    /**
     * The goods recipient.
     */
    @OneToOne
    @JoinColumn(name = "SHIP_TO_ID")
    @NotNull(message = "{salesOrder.shipToParty.notNull.message}")
    private BusinessPartner shipToParty;

    /**
     * The invoice recipient.
     */
    @OneToOne
    @JoinColumn(name = "BILL_TO_ID")
    @NotNull(message = "{salesOrder.billToParty.notNull.message}")
    private BusinessPartner billToParty;

    /**
     * The account on which the bill has to be settled.
     */
    @OneToOne
    @JoinColumn(name = "PAYMENT_ACCOUNT_ID")
    @NotNull(message = "{salesOrder.paymentAccount.notNull.message}")
    private Account paymentAccount;

    /**
     * The order date.
     */
    @Column(name = "ORDER_DATE")
    private Date orderDate;

    /**
     * The requested date for order delivery.
     */
    @Column(name = "REQUESTED_DELIVERY_DATE")
    private Date requestedDeliveryDate;

    /**
     * The status of the sales order.
     */
    @Column(name = "STATUS", length = MAX_STATUS_LENGTH)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{salesOrder.status.notNull.message}")
    private SalesOrderStatus status;

    /**
     * The items that are being ordered.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "salesOrder")
    private Set<SalesOrderItem> items;

    /**
     * Default constructor.
     */
    public SalesOrder() {
        this.orderDate = new Date();
        this.items = new HashSet<SalesOrderItem>();
    }

    /**
     * Adds a sales order item to the sales order.
     *
     * @param item The sales order item.
     */
    public void addItem(final SalesOrderItem item) {
        item.setSalesOrder(this);
        this.items.add(item);
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
     * @return the soldToParty
     */
    public BusinessPartner getSoldToParty() {
        return soldToParty;
    }

    /**
     * @param soldToParty the soldToParty to set
     */
    public void setSoldToParty(final BusinessPartner soldToParty) {
        this.soldToParty = soldToParty;
    }

    /**
     * @return the shipToParty
     */
    public BusinessPartner getShipToParty() {
        return shipToParty;
    }

    /**
     * @param shipToParty the shipToParty to set
     */
    public void setShipToParty(final BusinessPartner shipToParty) {
        this.shipToParty = shipToParty;
    }

    /**
     * @return the billToParty
     */
    public BusinessPartner getBillToParty() {
        return billToParty;
    }

    /**
     * @param billToParty the billToParty to set
     */
    public void setBillToParty(final BusinessPartner billToParty) {
        this.billToParty = billToParty;
    }

    /**
     * @return the paymentAccount
     */
    public Account getPaymentAccount() {
        return paymentAccount;
    }

    /**
     * @param paymentAccount the paymentAccount to set
     */
    public void setPaymentAccount(final Account paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    /**
     * @return the orderDate
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the requestedDeliveryDate
     */
    public Date getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    /**
     * @param requestedDeliveryDate the requestedDeliveryDate to set
     */
    public void setRequestedDeliveryDate(final Date requestedDeliveryDate) {
        this.requestedDeliveryDate = requestedDeliveryDate;
    }

    /**
     * @return the items
     */
    public Set<SalesOrderItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(final Set<SalesOrderItem> items) {
        this.items = items;
    }

    /**
     * @return the status
     */
    public SalesOrderStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final SalesOrderStatus status) {
        this.status = status;
    }

    /**
     * Calculates the hashCode of a SalesOrder.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((billToParty == null) ? 0 : billToParty.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
        result = prime * result + ((paymentAccount == null) ? 0 : paymentAccount.hashCode());
        result = prime * result + ((requestedDeliveryDate == null) ? 0 : requestedDeliveryDate.hashCode());
        result = prime * result + ((shipToParty == null) ? 0 : shipToParty.hashCode());
        result = prime * result + ((soldToParty == null) ? 0 : soldToParty.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    /**
     * Indicates whether some other SalesOrder is "equal to" this one.
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
        SalesOrder other = (SalesOrder) obj;
        if (billToParty == null) {
            if (other.billToParty != null) {
                return false;
            }
        } else if (!billToParty.equals(other.billToParty)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (orderDate == null && other.orderDate != null) {
            return false;
        }
        if (orderDate != null && other.orderDate == null) {
            return false;
        }
        if (orderDate != null && other.orderDate != null) {
            if (orderDate.getTime() != other.orderDate.getTime()) {
                return false;
            }
        }
        if (paymentAccount == null) {
            if (other.paymentAccount != null) {
                return false;
            }
        } else if (!paymentAccount.equals(other.paymentAccount)) {
            return false;
        }
        if (requestedDeliveryDate == null && other.requestedDeliveryDate != null) {
            return false;
        }
        if (requestedDeliveryDate != null && other.requestedDeliveryDate == null) {
            return false;
        }
        if (requestedDeliveryDate != null && other.requestedDeliveryDate != null) {
            if (requestedDeliveryDate.getTime() != other.requestedDeliveryDate.getTime()) {
                return false;
            }
        }
        if (shipToParty == null) {
            if (other.shipToParty != null) {
                return false;
            }
        } else if (!shipToParty.equals(other.shipToParty)) {
            return false;
        }
        if (soldToParty == null) {
            if (other.soldToParty != null) {
                return false;
            }
        } else if (!soldToParty.equals(other.soldToParty)) {
            return false;
        }
        if (status != other.status) {
            return false;
        }

        if (!this.areItemsEqual(other)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the list of items is equal.
     *
     * @param other The other sales order for comparison.
     * @return true, if items are equal; false otherwise.
     */
    private boolean areItemsEqual(final SalesOrder other) {
        if (this.items == null && other.items != null) {
            return false;
        }

        if (this.items != null && other.items == null) {
            return false;
        }

        if (this.items.size() != other.items.size()) {
            return false;
        }

        for (SalesOrderItem tempItem : this.items) {
            SalesOrderItem otherItem = other.getItemWithId(tempItem.getId());

            if (otherItem == null) {
                return false;
            }

            if (!tempItem.equals(otherItem)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates the sales order.
     *
     * @throws NoItemsException                  Indicates that the sales order has no items defined.
     * @throws DuplicateIdentifierException      Indicates that multiple items share the same id.
     * @throws QuantityExceedsInventoryException Indicates that the ordered quantity exceeds the inventory.
     * @throws Exception                         In case a general validation error occurred.
     */
    public void validate()
            throws NoItemsException, DuplicateIdentifierException, QuantityExceedsInventoryException, Exception {
        this.validateAnnotations();
        this.validateAdditionalCharacteristics();

        for (SalesOrderItem item : this.items) {
            item.validate();
        }
    }

    /**
     * Validates the sales order according to the annotations of the Validation Framework.
     *
     * @exception Exception In case the validation failed.
     */
    private void validateAnnotations() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SalesOrder>> violations = validator.validate(this);

        for (ConstraintViolation<SalesOrder> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }

    /**
     * Validates additional characteristics of the sales order besides annotations.
     *
     * @throws NoItemsException             Indicates that the sales order has no items defined.
     * @throws DuplicateIdentifierException Indicates that multiple items share the same id.
     */
    private void validateAdditionalCharacteristics() throws NoItemsException, DuplicateIdentifierException {
        this.validateItemsDefined();
        this.validateDistinctItemIds();
    }

    /**
     * Checks if any item ID is used multiple times.
     *
     * @throws DuplicateIdentifierException Indicates that an item ID is used multiple times.
     */
    private void validateDistinctItemIds() throws DuplicateIdentifierException {
        Set<Integer> usedIds = new HashSet<Integer>();
        boolean isDistinctId;

        for (SalesOrderItem item : this.items) {
            isDistinctId = usedIds.add(item.getId());

            if (!isDistinctId) {
                throw new DuplicateIdentifierException(item.getId().toString());
            }
        }
    }

    /**
     * Checks if items are defined.
     *
     * @throws NoItemsException If no items are defined
     */
    private void validateItemsDefined() throws NoItemsException {
        if (this.items == null || this.items.size() == 0) {
            throw new NoItemsException();
        }
    }

    /**
     * Gets the item with the given id.
     *
     * @param itemId The id of the item.
     * @return The item with the given id, if found.
     */
    public SalesOrderItem getItemWithId(final Integer itemId) {
        for (SalesOrderItem tempItem : this.items) {
            if (tempItem.getId() == itemId) {
                return tempItem;
            }
        }

        return null;
    }

    /**
     * Gets the total price of all items.
     *
     * @return The total price of all items.
     */
    public BigDecimal getPriceTotal() {
        BigDecimal priceTotal = BigDecimal.valueOf(0);

        for (SalesOrderItem tempItem : this.items) {
            priceTotal = priceTotal.add(tempItem.getPriceTotal());
        }

        return priceTotal;
    }

    /**
     * Gets the currency of the price total.
     *
     * @return The currency.
     */
    public Currency getPriceTotalCurrency() {
        Currency currency = Currency.EUR; // Initialize default currency.

        for (SalesOrderItem tempItem : this.items) {
            currency = tempItem.getMaterial().getCurrency();
        }

        return currency;
    }
}
