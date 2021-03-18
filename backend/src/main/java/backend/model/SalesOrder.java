package backend.model;

import java.util.Date;

/**
 * Represents an order issued by a customer.
 * 
 * @author Michael
 */
public class SalesOrder {
	/**
	 * The ID.
	 */
	private Integer id;
	
	/**
	 * The purchaser.
	 */
	private BusinessPartner soldToParty;
	
	/**
	 * The goods recipient.
	 */
	private BusinessPartner shipToParty;
	
	/**
	 * The invoice recipient.
	 */
	private BusinessPartner billToParty;
	
	/**
	 * The order date.
	 */
	private Date orderDate;
	
	/**
	 * The requested date for order delivery.
	 */
	private Date requestedDeliveryDate;
	
	/**
	 * The items that are ordered.
	 */
	private SalesOrderItem items;
	
	
	/**
	 * Default constructor.
	 */
	public SalesOrder() {
		
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
	public SalesOrderItem getItems() {
		return items;
	}


	/**
	 * @param items the items to set
	 */
	public void setItems(SalesOrderItem items) {
		this.items = items;
	}
}
