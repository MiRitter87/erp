package backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	private Integer id;
	
	/**
	 * The vendor.
	 */
	@OneToOne
	@JoinColumn(name="VENDOR_ID")
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
}
