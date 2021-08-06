package backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an order of goods from a vendor.
 * 
 * @author Michael
 */
public class PurchaseOrder {
	/**
	 * The ID.
	 */
	private Integer id;
	
	/**
	 * The vendor.
	 */
	private BusinessPartner vendor;
	
	/**
	 * The order date.
	 */
	private Date orderDate;
	
	/**
	 * The requested date for order delivery.
	 */
	private Date requestedDeliveryDate;
	
	/**
	 * The status of the purchase order.
	 */
	private PurchaseOrderStatus status;
	
	/**
	 * The items that are being ordered.
	 */
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
