package backend.model;

import java.util.ArrayList;
import java.util.Date;
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

import backend.exception.NoItemsException;

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
	 * The status of the purchase order.
	 */
	@Column(name="STATUS", length = 20)
	@Enumerated(EnumType.STRING)
	@NotNull(message = "{purchaseOrder.status.notNull.message}")
	private PurchaseOrderStatus status;
	
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
	public PurchaseOrderStatus getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(PurchaseOrderStatus status) {
		this.status = status;
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
	
	
	/**
	 * Validates the purchase order.
	 * 
	 * @throws NoItemsException Indicates that the purchase order has no items defined
	 * @throws Exception In case a general validation error occurred.
	 */
	public void validate() throws NoItemsException, Exception {
		this.validateAnnotations();
		this.validateAdditionalCharacteristics();
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
	 */
	private void validateAdditionalCharacteristics() throws NoItemsException {
		this.validateItemsDefined();
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
}
