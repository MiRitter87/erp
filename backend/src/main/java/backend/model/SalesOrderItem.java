package backend.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import backend.exception.QuantityExceedsInventoryException;

/**
 * An item of a sales order representing a product in a certain quantity.
 * 
 * @author Michael
 */
@Table(name="SALES_ORDER_ITEM")
@Entity
@IdClass(SalesOrderItemId.class)
public class SalesOrderItem {
	/**
	 * The ID.
	 */
	@Id
	@Column(name="ITEM_ID")
	@NotNull(message = "{salesOrderItem.id.notNull.message}")
	@Min(value = 1, message = "{salesOrderItem.id.min.message}")
	private Integer id;
	
	/**
	 * The SalesOrder to which the item belongs to.
	 */
	@Id
	@JoinColumn(name = "SALES_ORDER_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	private SalesOrder salesOrder;
	
	/**
	 * The material that is being ordered.
	 */
	@OneToOne
	@JoinColumn(name="MATERIAL_ID")
	@NotNull(message = "{salesOrderItem.material.notNull.message}")
	private Material material;
	
	/**
	 * The quantity that is being ordered.
	 */
	@Column(name="QUANTITY")
	@NotNull(message = "{salesOrderItem.quantity.notNull.message}")
	@Min(value = 1, message = "{salesOrderItem.quantity.min.message}")
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
		this.updatePriceTotal();
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
		this.updatePriceTotal();
	}


	/**
	 * @return the priceTotal
	 */
	public BigDecimal getPriceTotal() {
		return priceTotal;
	}
	
	
	/**
	 * @return the salesOrder
	 */
	public SalesOrder getSalesOrder() {
		return salesOrder;
	}


	/**
	 * @param salesOrder the salesOrder to set
	 */
	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((material == null) ? 0 : material.hashCode());
		result = prime * result + ((priceTotal == null) ? 0 : priceTotal.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
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
		SalesOrderItem other = (SalesOrderItem) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (material == null) {
			if (other.material != null) {
				return false;
			}
		} else if (!material.equals(other.material)) {
			return false;
		}
		if (priceTotal == null) {
			if (other.priceTotal != null) {
				return false;
			}
		} else if (!priceTotal.equals(other.priceTotal)) {
			return false;
		}
		if (quantity == null) {
			if (other.quantity != null) {
				return false;
			}
		} else if (!quantity.equals(other.quantity)) {
			return false;
		}
		return true;
	}


	/**
	 * Updates the total price of the sales order item based on quantity and price per unit of the material.
	 */
	private void updatePriceTotal() {
		if(this.material == null || this.material.getPricePerUnit() == null || this.quantity == null)
			return;
		
		this.priceTotal = this.material.getPricePerUnit().multiply(new BigDecimal(this.quantity));
	}
	
	
	/**
	 * Validates the sales order item.
	 * 
	 * @throws QuantityExceedsInventoryException Indicates that the ordered quantity exceeds the inventory.
	 * @throws Exception In case a general validation error occurred.
	 */
	public void validate() throws QuantityExceedsInventoryException, Exception {
		this.validateAnnotations();
		this.validateAdditionalCharacteristics();
	}
	
	
	/**
	 * Validates the sales order item according to the annotations of the Validation Framework.
	 * 
	 * @exception Exception In case the validation failed.
	 */
	private void validateAnnotations() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<SalesOrderItem>> violations = validator.validate(this);
		
		for(ConstraintViolation<SalesOrderItem> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}
	
	
	/**
	 * Validates additional characteristics of the sales order item besides annotations.
	 * 
	 * @throws QuantityExceedsInventoryException Indicates that the ordered quantity exceeds the inventory.
	 */
	private void validateAdditionalCharacteristics() throws QuantityExceedsInventoryException {
		if(this.quantity > this.material.getInventory())
			throw new QuantityExceedsInventoryException(this);
	}
}
