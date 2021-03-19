package backend.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * An item of a sales order representing a product in a certain quantity.
 * 
 * @author Michael
 */
@Table(name="SALES_ORDER_ITEM")
@Entity
public class SalesOrderItem {
	/**
	 * The ID.
	 */
	@Id
	@Column(name="ITEM_ID")
	private Integer id;
	
	/**
	 * The material that is being ordered.
	 */
	@OneToOne
	@JoinColumn(name="MATERIAL_ID")
	private Material material;
	
	/**
	 * The quantity that is being ordered.
	 */
	@Column(name="QUANTITY")
	private Long quantity;
	
	/**
	 * The total price of the sales order item at the time of order issuance.
	 */
	@Column(name="PRICE_TOTAL")
	private BigDecimal priceTotal;
	
	
	/**
	 * Default constructor.
	 */
	public SalesOrderItem() {
		
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
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}


	/**
	 * @param material the material to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}


	/**
	 * @return the quantity
	 */
	public Long getQuantity() {
		return quantity;
	}


	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}


	/**
	 * @return the priceTotal
	 */
	public BigDecimal getPriceTotal() {
		return priceTotal;
	}
}
