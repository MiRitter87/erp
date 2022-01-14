package backend.model.productionOrder;

import backend.model.material.Material;

/**
 * An item of a production order representing a material in a certain quantity that is going to be produced.
 * 
 * @author Michael
 */
public class ProductionOrderItem {
	/**
	 * The ID.
	 */
	private Integer id;
	
	/**
	 * The production order to which the item belongs to.
	 */
	private ProductionOrder productionOrder;
	
	/**
	 * The material that is being produced.
	 */
	private Material material;
	
	/**
	 * The quantity that is being produced.
	 */
	private Long quantity;
	
	
	/**
	 * Default constructor.
	 */
	public ProductionOrderItem() {
		
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
	 * @return the productionOrder
	 */
	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}


	/**
	 * @param productionOrder the productionOrder to set
	 */
	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
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
}
