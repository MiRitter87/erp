package backend.model.billOfMaterial;

import backend.model.material.Material;

/**
 * A bill of material item defines a material in a specific quantity that is needed for creation of another material.
 * 
 * @author Michael
 */
public class BillOfMaterialItem {
	/**
	 * The distinct identification number.
	 */
	private Integer id;
	
	/**
	 * The material needed.
	 */
	private Material material;
	
	/**
	 * The quantity of the material.
	 */
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
}
