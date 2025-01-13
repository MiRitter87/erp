package backend.model.purchaseOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;
import backend.model.Currency;
import backend.model.account.Account;
import backend.model.businessPartner.BusinessPartner;

/**
 * Represents an order of goods from a vendor.
 *
 * @author Michael
 */
@Table(name = "PURCHASE_ORDER")
@Entity
@SequenceGenerator(name = "purchaseOrderSequence", initialValue = 1, allocationSize = 1)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PurchaseOrder {
    /**
     * The maximum status field length allowed.
     */
    private static final int MAX_STATUS_LENGTH = 20;

    /**
     * The ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseOrderSequence")
    @Column(name = "PURCHASE_ORDER_ID")
    @Min(value = 1, message = "{purchaseOrder.id.min.message}")
    private Integer id;

    /**
     * The vendor.
     */
    @OneToOne
    @JoinColumn(name = "VENDOR_ID")
    @NotNull(message = "{purchaseOrder.vendor.notNull.message}")
    private BusinessPartner vendor;

    /**
     * The account on which the bill has to be settled.
     */
    @OneToOne
    @JoinColumn(name = "PAYMENT_ACCOUNT_ID")
    @NotNull(message = "{purchaseOrder.paymentAccount.notNull.message}")
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
     * The set of active status of the purchase order.
     */
    @CollectionTable(name = "PURCHASE_ORDER_STATUS", joinColumns = { @JoinColumn(name = "PURCHASE_ORDER_ID") })
    @Column(name = "STATUS", length = MAX_STATUS_LENGTH)
    @ElementCollection(targetClass = PurchaseOrderStatus.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "{purchaseOrder.status.notEmpty.message}")
    private Set<PurchaseOrderStatus> status;

    /**
     * The items that are being ordered.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "purchaseOrder")
    private Set<PurchaseOrderItem> items;

    /**
     * Default constructor.
     */
    public PurchaseOrder() {
        this.orderDate = new Date();
        this.items = new HashSet<PurchaseOrderItem>();
        this.status = new HashSet<PurchaseOrderStatus>();
        this.updateTransientStatus();
    }

    /**
     * Adds a purchase order item to the purchase order.
     *
     * @param item The purchase order item.
     */
    public void addItem(final PurchaseOrderItem item) {
        item.setPurchaseOrder(this);
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
     * @return the vendor
     */
    public BusinessPartner getVendor() {
        return vendor;
    }

    /**
     * @param vendor the vendor to set
     */
    public void setVendor(final BusinessPartner vendor) {
        this.vendor = vendor;
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
     * @return the status
     */
    public Set<PurchaseOrderStatus> getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final Set<PurchaseOrderStatus> status) {
        this.status = status;

        if (status != null) {
            this.updateTransientStatus();
        }
    }

    /**
     * Sets the given status.
     *
     * @param purchaseOrderStatus The status to be set.
     * @param active              Sets the given status to active if true; removes the given status if false.
     */
    public void setStatus(final PurchaseOrderStatus purchaseOrderStatus, final boolean active) {
        this.setStatus(purchaseOrderStatus, active, true);
    }

    /**
     * Sets the given status.
     *
     * @param purchaseOrderStatus   The status to be set.
     * @param active                Sets the given status to active if true; removes the given status if false.
     * @param updateTransientStatus Updates the transient status if set to true.
     */
    private void setStatus(final PurchaseOrderStatus purchaseOrderStatus, final boolean active,
            final boolean updateTransientStatus) {
        if (active) {
            this.status.add(purchaseOrderStatus);
        } else {
            this.status.remove(purchaseOrderStatus);
        }

        if (updateTransientStatus) {
            this.updateTransientStatus();
        }
    }

    /**
     * @return the items
     */
    public Set<PurchaseOrderItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(final Set<PurchaseOrderItem> items) {
        this.items = items;
    }

    /**
     * Calculates the hashCode of a PurchaseOrder.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
        result = prime * result + ((requestedDeliveryDate == null) ? 0 : requestedDeliveryDate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
        result = prime * result + ((paymentAccount == null) ? 0 : paymentAccount.hashCode());
        return result;
    }

    /**
     * Indicates whether some other PurchaseOrder is "equal to" this one.
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
        PurchaseOrder other = (PurchaseOrder) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (status == null) {
            if (other.status != null) {
                return false;
            }
        } else if (!status.equals(other.status)) {
            return false;
        }
        if (vendor == null) {
            if (other.vendor != null) {
                return false;
            }
        } else if (!vendor.equals(other.vendor)) {
            return false;
        }
        if (paymentAccount == null) {
            if (other.paymentAccount != null) {
                return false;
            }
        } else if (!paymentAccount.equals(other.paymentAccount)) {
            return false;
        }

        if (!this.areDatesEqual(other)) {
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
     * @param other The other purchase order for comparison.
     * @return true, if items are equal; false otherwise.
     */
    private boolean areItemsEqual(final PurchaseOrder other) {
        if (this.items == null && other.items != null) {
            return false;
        }

        if (this.items != null && other.items == null) {
            return false;
        }

        if (this.items.size() != other.items.size()) {
            return false;
        }

        for (PurchaseOrderItem tempItem : this.items) {
            PurchaseOrderItem otherItem = other.getItemWithId(tempItem.getId());

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
     * Checks if the dates are equal.
     *
     * @param other The other PurchaseOrder for comparison.
     * @return true, if dates are equal; false otherwise.
     */
    private boolean areDatesEqual(final PurchaseOrder other) {
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

        return true;
    }

    /**
     * Updates the transient status OPEN, IN_PROCESS and FINISHED that are derived from other status.
     */
    private void updateTransientStatus() {
        if (this.isStatusActive(PurchaseOrderStatus.CANCELED)) {
            this.setStatus(PurchaseOrderStatus.OPEN, false, false);
            this.setStatus(PurchaseOrderStatus.IN_PROCESS, false, false);
            this.setStatus(PurchaseOrderStatus.FINISHED, false, false);
        } else {
            if (this.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT)
                    && this.isStatusActive(PurchaseOrderStatus.INVOICE_RECEIPT)
                    && this.isStatusActive(PurchaseOrderStatus.INVOICE_SETTLED)) {

                this.setStatus(PurchaseOrderStatus.OPEN, false, false);
                this.setStatus(PurchaseOrderStatus.IN_PROCESS, false, false);
                this.setStatus(PurchaseOrderStatus.FINISHED, true, false);
            } else {
                if (this.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT)
                        || this.isStatusActive(PurchaseOrderStatus.INVOICE_RECEIPT)
                        || this.isStatusActive(PurchaseOrderStatus.INVOICE_SETTLED)) {

                    this.setStatus(PurchaseOrderStatus.OPEN, false, false);
                    this.setStatus(PurchaseOrderStatus.IN_PROCESS, true, false);
                    this.setStatus(PurchaseOrderStatus.FINISHED, false, false);
                } else {
                    this.setStatus(PurchaseOrderStatus.OPEN, true, false);
                    this.setStatus(PurchaseOrderStatus.IN_PROCESS, false, false);
                    this.setStatus(PurchaseOrderStatus.FINISHED, false, false);
                }

            }
        }
    }

    /**
     * Checks if the given status is active.
     *
     * @param purchaseOrderStatus The status to be checked.
     * @return true, if status is active; false if not.
     */
    public boolean isStatusActive(final PurchaseOrderStatus purchaseOrderStatus) {
        if (this.status.contains(purchaseOrderStatus)) {
            return true;
        }

        return false;
    }

    /**
     * Validates the purchase order.
     *
     * @throws NoItemsException             Indicates that the purchase order has no items defined
     * @throws DuplicateIdentifierException Indicates that multiple items share the same id.
     * @throws Exception                    In case a general validation error occurred.
     */
    public void validate() throws NoItemsException, DuplicateIdentifierException, Exception {
        PurchaseOrderValidator validator = new PurchaseOrderValidator(this);
        validator.validate();
    }

    /**
     * Gets the item with the given id.
     *
     * @param itemId The id of the item.
     * @return The item with the given id, if found.
     */
    public PurchaseOrderItem getItemWithId(final Integer itemId) {
        for (PurchaseOrderItem tempItem : this.items) {
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

        for (PurchaseOrderItem tempItem : this.items) {
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

        for (PurchaseOrderItem tempItem : this.items) {
            currency = tempItem.getMaterial().getCurrency();
        }

        return currency;
    }
}
