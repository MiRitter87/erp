package backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.validation.constraints.NotNull;

import backend.exception.NoItemsException;

/**
 * Represents an order issued by a customer.
 * 
 * @author Michael
 */
@Table(name="SALES_ORDER")
@Entity
@SequenceGenerator(name = "salesOrderSequence", initialValue = 1, allocationSize = 1)
public class SalesOrder {
	/**
	 * The ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesOrderSequence")
	@Column(name="SALES_ORDER_ID")
	private Integer id;
	
	/**
	 * The purchaser.
	 */
	@OneToOne
	@JoinColumn(name="SOLD_TO_ID")
	@NotNull(message = "{salesOrder.soldToParty.notNull.message}")
	private BusinessPartner soldToParty;
	
	/**
	 * The goods recipient.
	 */
	@OneToOne
	@JoinColumn(name="SHIP_TO_ID")
	@NotNull(message = "{salesOrder.shipToParty.notNull.message}")
	private BusinessPartner shipToParty;
	
	/**
	 * The invoice recipient.
	 */
	@OneToOne
	@JoinColumn(name="BILL_TO_ID")
	@NotNull(message = "{salesOrder.billToParty.notNull.message}")
	private BusinessPartner billToParty;
	
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
	 * The items that are being ordered.
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "salesOrder")
	private List<SalesOrderItem> items;
	
	
	/**
	 * Default constructor.
	 */
	public SalesOrder() {
		this.orderDate = new Date();
		this.items = new ArrayList<SalesOrderItem>();
	}
	
	
	/**
	 * Adds a sales order item to the sales order.
	 * 
	 * @param item The sales order item.
	 */
	public void addItem(final SalesOrderItem item) {
		item.salesOrder = this;
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
	 * @return the soldToParty
	 */
	public BusinessPartner getSoldToParty() {
		return soldToParty;
	}


	/**
	 * @param soldToParty the soldToParty to set
	 */
	public void setSoldToParty(BusinessPartner soldToParty) {
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
	public void setShipToParty(BusinessPartner shipToParty) {
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
	public void setBillToParty(BusinessPartner billToParty) {
		this.billToParty = billToParty;
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
	 * @return the items
	 */
	public List<SalesOrderItem> getItems() {
		return items;
	}


	/**
	 * @param items the items to set
	 */
	public void setItems(List<SalesOrderItem> items) {
		this.items = items;
	}


	/**
	 * Validates the sales order.
	 * 
	 * @throws NoItemsException Indicates that the sales order has no items defined.
	 * @throws Exception In case a general validation error occurred.
	 */
	public void validate() throws NoItemsException, Exception {
		this.validateAnnotations();
		this.validateAdditionalCharacteristics();
		
		for(SalesOrderItem item:this.items)
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
		Set<ConstraintViolation<SalesOrder>> violations = validator.validate(this);
		
		for(ConstraintViolation<SalesOrder> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}
	
	
	/**
	 * Validates additional characteristics of the sales order besides annotations.
	 * 
	 * @throws NoItemsException Indicates that the sales order has no items defined.
	 */
	private void validateAdditionalCharacteristics() throws NoItemsException {
		if(this.items == null || this.items.size() == 0)
			throw new NoItemsException();
	}
}
