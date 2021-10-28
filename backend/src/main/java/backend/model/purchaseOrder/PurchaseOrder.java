package backend.model.purchaseOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;
import backend.model.account.Account;
import backend.model.businessPartner.BusinessPartner;

/**
 * Represents an order of goods from a vendor.
 * 
 * @author Michael
 */
@Table(name="PURCHASE_ORDER")
@Entity
@SequenceGenerator(name = "purchaseOrderSequence", initialValue = 1, allocationSize = 1)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaseOrder {
	/**
	 * The ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchaseOrderSequence")
	@Column(name="PURCHASE_ORDER_ID")
	@Min(value = 1, message = "{purchaseOrder.id.min.message}")
	private Integer id;
	
	/**
	 * The vendor.
	 */
	@OneToOne
	@JoinColumn(name="VENDOR_ID")
	@NotNull(message = "{purchaseOrder.vendor.notNull.message}")
	private BusinessPartner vendor;
	
	/**
	 * The account on which the bill has to be settled.
	 */
	@OneToOne
	@JoinColumn(name="PAYMENT_ACCOUNT_ID")
	@NotNull(message = "{purchaseOrder.paymentAccount.notNull.message}")
	private Account paymentAccount;
	
	/**
	 * The order date.
	 */
	@Column(name="ORDER_DATE")
	private Date orderDate;
	
	/**
	 * The requested date for order delivery.
	 */
	@Column(name="REQUESTED_DELIVERY_DATE")
	private Date requestedDeliveryDate;
	
	/**
	 * The set of active status of the purchase order.
	 */
	@CollectionTable(name="PURCHASE_ORDER_STATUS", joinColumns = {@JoinColumn(name="PURCHASE_ORDER_ID")})
	@Column(name="STATUS", length = 20)
	@ElementCollection(targetClass = PurchaseOrderStatus.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@NotEmpty(message = "{purchaseOrder.status.notEmpty.message}")
	private Set<PurchaseOrderStatus> status;
	
	/**
	 * The items that are being ordered.
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "purchaseOrder")
	private List<PurchaseOrderItem> items;
	
	
	/**
	 * Default constructor.
	 */
	public PurchaseOrder() {
		this.orderDate = new Date();
		this.items = new ArrayList<PurchaseOrderItem>();
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
	public void setId(Integer id) {
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
	public void setVendor(BusinessPartner vendor) {
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
	public void setPaymentAccount(Account paymentAccount) {
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
	public void setOrderDate(Date orderDate) {
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
	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
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
	public void setStatus(Set<PurchaseOrderStatus> status) {
		this.status = status;
		
		if(status != null)
			this.updateTransientStatus();
	}

	
	/**
	 * Sets the given status.
	 * 
	 * @param status The status to be set.
	 * @param active Sets the given status to active if true; removes the given status if false.
	 */
	public void setStatus(final PurchaseOrderStatus status, final boolean active) {
		this.setStatus(status, active, true);				
	}
	
	
	/**
	 * Sets the given status.
	 * 
	 * @param status The status to be set.
	 * @param active Sets the given status to active if true; removes the given status if false.
	 * @param updateTransientStatus Updates the transient status if set to true.
	 */
	private void setStatus(final PurchaseOrderStatus status, final boolean active, final boolean updateTransientStatus) {
		if(active)
			this.status.add(status);
		else
			this.status.remove(status);
		
		if(updateTransientStatus)
			this.updateTransientStatus();
	}
	

	/**
	 * @return the items
	 */
	public List<PurchaseOrderItem> getItems() {
		return items;
	}


	/**
	 * @param items the items to set
	 */
	public void setItems(List<PurchaseOrderItem> items) {
		this.items = items;
	}
	
	
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
		PurchaseOrder other = (PurchaseOrder) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (orderDate == null) {
			if (other.orderDate != null) {
				return false;
			}
		} else if (orderDate.getTime() != other.orderDate.getTime()) {
			return false;
		}
		if (requestedDeliveryDate == null) {
			if (other.requestedDeliveryDate != null) {
				return false;
			}
		} else if (requestedDeliveryDate.getTime() != other.requestedDeliveryDate.getTime()) {
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
		
		if(this.areItemsEqual(other) == false)
			return false;
		
		return true;
	}
	
	
	/**
	 * Checks if the list of items is equal.
	 * 
	 * @param other The other purchase order for comparison.
	 * @return true, if items are equal; false otherwise.
	 */
	private boolean areItemsEqual(PurchaseOrder other) {
		if (this.items == null && other.items != null)
			return false;
		
		if (this.items != null && other.items == null)
			return false;
		
		if(this.items.size() != other.items.size())
			return false;
		
		for(PurchaseOrderItem tempItem:this.items) {
			PurchaseOrderItem otherItem = other.getItemWithId(tempItem.getId());
			
			if(otherItem == null)
				return false;
			
			if(!tempItem.equals(otherItem))
				return false;
		}
		
		return true;
	}
	
	
	/**
	 * Updates the transient status OPEN, IN_PROCESS and FINISHED that are derived from other status.
	 */
	private void updateTransientStatus() {
		if(this.isStatusActive(PurchaseOrderStatus.CANCELED)) {
			this.setStatus(PurchaseOrderStatus.OPEN, false, false);
			this.setStatus(PurchaseOrderStatus.IN_PROCESS, false, false);
			this.setStatus(PurchaseOrderStatus.FINISHED, false, false);
		}
		else {
			if(this.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT) && this.isStatusActive(PurchaseOrderStatus.INVOICE_RECEIPT) &&
					this.isStatusActive(PurchaseOrderStatus.INVOICE_SETTLED)) {
				
				this.setStatus(PurchaseOrderStatus.OPEN, false, false);
				this.setStatus(PurchaseOrderStatus.IN_PROCESS, false, false);
				this.setStatus(PurchaseOrderStatus.FINISHED, true, false);
			}
			else {
				if(this.isStatusActive(PurchaseOrderStatus.GOODS_RECEIPT) || this.isStatusActive(PurchaseOrderStatus.INVOICE_RECEIPT) ||
						this.isStatusActive(PurchaseOrderStatus.INVOICE_SETTLED)) {
					
					this.setStatus(PurchaseOrderStatus.OPEN, false, false);
					this.setStatus(PurchaseOrderStatus.IN_PROCESS, true, false);
					this.setStatus(PurchaseOrderStatus.FINISHED, false, false);
				}
				else {					
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
	 * @param status The status to be checked.
	 * @return true, if status is active; false if not.
	 */
	public boolean isStatusActive(PurchaseOrderStatus status) {
		if(this.status.contains(status))
			return true;
		else		
			return false;
	}


	/**
	 * Validates the purchase order.
	 * 
	 * @throws NoItemsException Indicates that the purchase order has no items defined
	 * @throws DuplicateIdentifierException Indicates that multiple items share the same id.
	 * @throws Exception In case a general validation error occurred.
	 */
	public void validate() throws NoItemsException, DuplicateIdentifierException, Exception {
		this.validateAnnotations();
		this.validateAdditionalCharacteristics();
		
		for(PurchaseOrderItem item:this.items)
			item.validate();
	}
	
	
	/**
	 * Validates the sales order according to the annotations of the Validation Framework.
	 * 
	 * @exception Exception In case the validation failed.
	 */
	private void validateAnnotations() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<PurchaseOrder>> violations = validator.validate(this);
		
		for(ConstraintViolation<PurchaseOrder> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}
	
	
	/**
	 * Validates additional characteristics of the purchase order besides annotations.
	 * 
	 * @throws NoItemsException Indicates that the purchase order has no items defined.
	 * @throws DuplicateIdentifierException Indicates that multiple items share the same id.
	 */
	private void validateAdditionalCharacteristics() throws NoItemsException, DuplicateIdentifierException {
		this.validateItemsDefined();
		this.validateDistinctItemIds();
	}
	
	
	/**
	 * Checks if items are defined.
	 * 
	 * @throws NoItemsException If no items are defined
	 */
	private void validateItemsDefined() throws NoItemsException {
		if(this.items == null || this.items.size() == 0)
			throw new NoItemsException();
	}
	
	
	/**
	 * Checks if any item ID is used multiple times.
	 * 
	 * @throws DuplicateIdentifierException Indicates that an item ID is used multiple times.
	 */
	private void validateDistinctItemIds() throws DuplicateIdentifierException {
		Set<Integer> usedIds = new HashSet<Integer>();
		boolean isDistinctId;
		
		for(PurchaseOrderItem item:this.items) {
			isDistinctId = usedIds.add(item.getId());
			
			if(!isDistinctId) {
				throw new DuplicateIdentifierException(item.getId().toString());
			}
		}
	}
	
	
	/**
	 * Gets the item with the given id.
	 * 
	 * @param id The id of the item.
	 * @return The item with the given id, if found.
	 */
	public PurchaseOrderItem getItemWithId(Integer id) {
		for(PurchaseOrderItem tempItem:this.items) {
			if(tempItem.getId() == id)
				return tempItem;
		}
		
		return null;
	}
}
