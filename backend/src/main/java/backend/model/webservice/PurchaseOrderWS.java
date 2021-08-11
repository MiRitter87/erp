package backend.model.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import backend.model.PurchaseOrderStatus;

/**
 * A lean version of a purchase order that is used by the WebService to transfer object data.
 * The main difference to the regular PurchaseOrder is that IDs are used instead of object references.
 * 
 * @author Michael
 */
public class PurchaseOrderWS {
	/**
	 * The ID of the purchase order.
	 */
	private Integer purchaseOrderId;
	
	/**
	 * The ID of the vendor.
	 */
	private Integer vendorId;
	
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
	private List<PurchaseOrderItemWS> items;
	
	
	/**
	 * Constructor.
	 */
	public PurchaseOrderWS() {
		this.items = new ArrayList<PurchaseOrderItemWS>();
	}
	
	
	/**
	 * Adds the given item to the order.
	 * 
	 * @param purchaseOrderItem The item to be added.
	 */
	public void addItem(final PurchaseOrderItemWS purchaseOrderItem) {
		this.items.add(purchaseOrderItem);
	}


	/**
	 * @return the purchaseOrderId
	 */
	public Integer getPurchaseOrderId() {
		return purchaseOrderId;
	}


	/**
	 * @param purchaseOrderId the purchaseOrderId to set
	 */
	public void setPurchaseOrderId(Integer purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}


	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}


	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
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
	public List<PurchaseOrderItemWS> getItems() {
		return items;
	}


	/**
	 * @param items the items to set
	 */
	public void setItems(List<PurchaseOrderItemWS> items) {
		this.items = items;
	}
}
