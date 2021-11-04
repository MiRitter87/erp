package backend.model.billOfMaterial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	private Material material;
	
	/**
	 * The quantity of the material.
	 */
	@Column(name="QUANTITY")
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
}
