package backend.model.billOfMaterial;

import java.util.List;

import backend.model.material.Material;

/**
 * A bill of material defines a list of materials that are necessary to create another material.
 * 
 * @author Michael
 */
public class BillOfMaterial {
	/**
	 * The distinct identification number.
	 */
	private Integer id;
	
	/**
	 * The name.
	 */
	private String name;
	
	/**
	 * The description.
	 */
	private String description;
	
	/**
	 * The material whose parts are listed.
	 */
	private Material material;
	
	/**
	 * The items needed to create a material.
	 */
	private List<BillOfMaterialItem> items;
	
	
	/**
	 * Default constructor.
	 */
	public BillOfMaterial() {
		
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the items
	 */
	public List<BillOfMaterialItem> getItems() {
		return items;
	}


	/**
	 * @param items the items to set
	 */
	public void setItems(List<BillOfMaterialItem> items) {
		this.items = items;
	}
}
