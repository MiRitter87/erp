package backend.model.billOfMaterial;

import java.util.ArrayList;
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

import backend.model.material.Material;

/**
 * A bill of material defines a list of materials that are necessary to create another material.
 * 
 * @author Michael
 */
@Table(name="BILL_OF_MATERIAL")
@Entity
@SequenceGenerator(name = "billOfMaterialSequence", initialValue = 1, allocationSize = 1)
public class BillOfMaterial {
	/**
	 * The distinct identification number.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billOfMaterialSequence")
	@Column(name="BILL_OF_MATERIAL_ID")
	private Integer id;
	
	/**
	 * The name.
	 */
	@Column(name="NAME", length = 50)
	private String name;
	
	/**
	 * The description.
	 */
	@Column(name="DESCRIPTION", length = 250)
	private String description;
	
	/**
	 * The material whose parts are listed.
	 */
	@OneToOne
	@JoinColumn(name="MATERIAL_ID")
	private Material material;
	
	/**
	 * The items needed to create a material.
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "billOfMaterial")
	private List<BillOfMaterialItem> items;
	
	
	/**
	 * Default constructor.
	 */
	public BillOfMaterial() {
		this.items = new ArrayList<BillOfMaterialItem>();
	}

	
	/**
	 * Adds a BillOfMaterialItem to the BillOfMaterial.
	 * 
	 * @param item The BillOfMaterialItem.
	 */
	public void addItem(final BillOfMaterialItem item) {
		item.setBillOfMaterial(this);
		this.items.add(item);
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
