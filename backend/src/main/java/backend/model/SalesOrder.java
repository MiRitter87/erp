package backend.model;

import java.util.Date;
import java.util.List;

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
	private BusinessPartner soldToParty;
	
	/**
	 * The goods recipient.
	 */
	@OneToOne
	@JoinColumn(name="SHIP_TO_ID")
	private BusinessPartner shipToParty;
	
	/**
	 * The invoice recipient.
	 */
	@OneToOne
	@JoinColumn(name="BILL_TO_ID")
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
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "SALES_ORDER_ID")
	private List<SalesOrderItem> items;
	
	
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
	public List<SalesOrderItem> getItems() {
		return items;
	}


	/**
	 * @param items the items to set
	 */
	public void setItems(List<SalesOrderItem> items) {
		this.items = items;
	}
}
