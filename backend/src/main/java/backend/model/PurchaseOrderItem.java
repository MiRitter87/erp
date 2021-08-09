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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * An item of a purchase order representing a product in a certain quantity.
 * 
 * @author Michael
 */
@Table(name="PURCHASE_ORDER_ITEM")
@Entity
@IdClass(PurchaseOrderItemId.class)
public class PurchaseOrderItem {
	/**
	 * The ID.
	 */
	@Id
	@Column(name="ITEM_ID")
	@NotNull(message = "{purchaseOrderItem.id.notNull.message}")
	@Min(value = 1, message = "{purchaseOrderItem.id.min.message}")
	private Integer id;
	
	/**
	 * The PurchaseOrder to which the item belongs to.
	 */
	@Id
	@JoinColumn(name = "PURCHASE_ORDER_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private PurchaseOrder purchaseOrder;
	
	/**
	 * The material that is being ordered.
	 */
	@OneToOne
	@JoinColumn(name="MATERIAL_ID")
	@NotNull(message = "{purchaseOrderItem.material.notNull.message}")
	private Material material;
	
	/**
	 * The quantitiy that is being ordered.
	 */
	@Column(name="QUANTITY")
	@NotNull(message = "{purchaseOrderItem.quantity.notNull.message}")
	@Min(value = 1, message = "{purchaseOrderItem.quantity.min.message}")
	private Long quantity;
	
	/**
	 * The total price of the purchase order item at the time of order issuance.
	 */
	@Column(name="PRICE_TOTAL")
	private BigDecimal priceTotal;
	
	
	/**
	 * Default constructor.
	 */
	public PurchaseOrderItem() {
		
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
	 * @return the purchaseOrder
	 */
	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}


	/**
	 * @param purchaseOrder the purchaseOrder to set
	 */
	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
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


	/**
	 * @param priceTotal the priceTotal to set
	 */
	public void setPriceTotal(BigDecimal priceTotal) {
		this.priceTotal = priceTotal;
	}
	
	
	/**
	 * Validates the purchase order item.
	 * 
	 * @throws Exception In case a general validation error occurred.
	 */
	public void validate() throws Exception {
		this.validateAnnotations();
	}
	
	
	/**
	 * Validates the purchase order item according to the annotations of the Validation Framework.
	 * 
	 * @exception Exception In case the validation failed.
	 */
	private void validateAnnotations() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(this);
		
		for(ConstraintViolation<PurchaseOrderItem> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}
}
