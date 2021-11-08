package backend.model.billOfMaterial;

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

import backend.model.material.Material;

/**
 * A bill of material item defines a material in a specific quantity that is needed for creation of another material.
 * 
 * @author Michael
 */
@Table(name="BILL_OF_MATERIAL_ITEM")
@Entity
@IdClass(BillOfMaterialItemId.class)
public class BillOfMaterialItem {
	/**
	 * The distinct identification number.
	 */
	@Id
	@Column(name="ITEM_ID")
	@NotNull(message = "{billOfMaterialItem.id.notNull.message}")
	@Min(value = 1, message = "{billOfMaterialItem.id.min.message}")
	private Integer id;
	
	/**
	 * The BillOfMaterial to which the item belongs to.
	 */
	@Id
	@JoinColumn(name = "BILL_OF_MATERIAL_ID")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private BillOfMaterial billOfMaterial;
	
	/**
	 * The material needed.
	 */
	@OneToOne
	@JoinColumn(name="MATERIAL_ID")
	@NotNull(message = "{billOfMaterialItem.material.notNull.message}")
	private Material material;
	
	/**
	 * The quantity of the material.
	 */
	@Column(name="QUANTITY")
	@NotNull(message = "{billOfMaterialItem.quantity.notNull.message}")
	@Min(value = 1, message = "{billOfMaterialItem.quantity.min.message}")
	private Integer quantity;
	
	
	/**
	 * Default constructor.
	 */
	public BillOfMaterialItem() {
		
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
	public Integer getQuantity() {
		return quantity;
	}


	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	/**
	 * @return the billOfMaterial
	 */
	public BillOfMaterial getBillOfMaterial() {
		return billOfMaterial;
	}


	/**
	 * @param billOfMaterial the billOfMaterial to set
	 */
	public void setBillOfMaterial(BillOfMaterial billOfMaterial) {
		this.billOfMaterial = billOfMaterial;
	}
	
	
	/**
	 * Validates the BillOfMaterialItem.
	 * 
	 * @throws Exception In case a general validation error occurred.
	 */
	public void validate() throws Exception {
		this.validateAnnotations();
	}
	
	
	/**
	 * Validates the BillOfMaterialItem according to the annotations of the Validation Framework.
	 * 
	 * @exception Exception In case the validation failed.
	 */
	private void validateAnnotations() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<BillOfMaterialItem>> violations = validator.validate(this);
		
		for(ConstraintViolation<BillOfMaterialItem> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}
}
