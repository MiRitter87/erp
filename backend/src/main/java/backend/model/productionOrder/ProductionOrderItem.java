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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((material == null) ? 0 : material.hashCode());
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
		ProductionOrderItem other = (ProductionOrderItem) obj;
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
		if (quantity == null) {
			if (other.quantity != null) {
				return false;
			}
		} else if (!quantity.equals(other.quantity)) {
			return false;
		}
		return true;
	}
}
