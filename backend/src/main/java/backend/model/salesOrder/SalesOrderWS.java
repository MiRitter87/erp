package backend.model.salesOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A lean version of a sales order that is used by the WebService to transfer object data.
 * The main difference to the regular SalesOrder is that IDs are used instead of object references.
 * 
 * @author Michael
 */
public class SalesOrderWS {
	/**
	 * The ID of the sales order.
	 */
	private Integer salesOrderId;
	
	/**
	 * The ID of the sold-to party.
	 */
	private Integer soldToId;
	
	/**
	 * The ID of the ship-to party.
	 */
	private Integer shipToId;
	
	/**
	 * The ID of the bill-to party.
	 */
	private Integer billToId;
	
	/**
	 * The ID of the payment account.
	 */
	private Integer paymentAccountId;
	
	/**
	 * The order date.
	 */
	private Date orderDate;
	
	/**
	 * The requested date for order delivery.
	 */
	private Date requestedDeliveryDate;
	
	/**
	 * The status of the sales order.
	 */
	private SalesOrderStatus status;
	
	/**
	 * The items that are being ordered.
	 */
	private List<SalesOrderItemWS> items;
	
	
	/**
	 * Constructor.
	 */
	public SalesOrderWS() {
		this.items = new ArrayList<SalesOrderItemWS>();
	}
	
	
	/**
	 * Adds the given item to the order.
	 * 
	 * @param salesOrderItem The item to be added.
	 */
	public void addItem(final SalesOrderItemWS salesOrderItem) {
		this.items.add(salesOrderItem);
	}

	
	/**
	 * @return the salesOrderId
	 */
	public Integer getSalesOrderId() {
		return salesOrderId;
	}

	
	/**
	 * @param salesOrderId the salesOrderId to set
	 */
	public void setSalesOrderId(Integer salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	
	/**
	 * @return the soldToId
	 */
	public Integer getSoldToId() {
		return soldToId;
	}

	
	/**
	 * @param soldToId the soldToId to set
	 */
	public void setSoldToId(Integer soldToId) {
		this.soldToId = soldToId;
	}

	
	/**
	 * @return the shipToId
	 */
	public Integer getShipToId() {
		return shipToId;
	}

	
	/**
	 * @param shipToId the shipToId to set
	 */
	public void setShipToId(Integer shipToId) {
		this.shipToId = shipToId;
	}

	
	/**
	 * @return the billToId
	 */
	public Integer getBillToId() {
		return billToId;
	}

	
	/**
	 * @param billToId the billToId to set
	 */
	public void setBillToId(Integer billToId) {
		this.billToId = billToId;
	}

	
	/**
	 * @return the paymentAccountId
	 */
	public Integer getPaymentAccountId() {
		return paymentAccountId;
	}


	/**
	 * @param paymentAccountId the paymentAccountId to set
	 */
	public void setPaymentAccountId(Integer paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
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
	public List<SalesOrderItemWS> getItems() {
		return items;
	}

	
	/**
	 * @param items the items to set
	 */
	public void setItems(List<SalesOrderItemWS> items) {
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
	public void setStatus(SalesOrderStatus status) {
		this.status = status;
	}
}
