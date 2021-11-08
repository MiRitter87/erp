package backend.model.billOfMaterial;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;
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
	@Min(value = 1, message = "{billOfMaterial.id.min.message}")
	private Integer id;
	
	/**
	 * The name.
	 */
	@Column(name="NAME", length = 50)
	@Size(min = 1, max = 50, message = "{billOfMaterial.name.size.message}")
	@NotNull(message = "{billOfMaterial.name.notNull.message}")
	private String name;
	
	/**
	 * The description.
	 */
	@Column(name="DESCRIPTION", length = 250)
	@Size(min = 0, max = 250, message = "{billOfMaterial.description.size.message}")
	private String description;
	
	/**
	 * The material whose parts are listed.
	 */
	@OneToOne
	@JoinColumn(name="MATERIAL_ID")
	@NotNull(message = "{billOfMaterial.material.notNull.message}")
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
	
	
	/**
	 * Validates the BillOfMaterial.
	 * 
	 * @throws NoItemsException Indicates that the BillOfMaterial has no items defined.
	 * @throws DuplicateIdentifierException Indicates that multiple items share the same id.
	 * @throws Exception In case a general validation error occurred.
	 */
	public void validate() throws NoItemsException, DuplicateIdentifierException, Exception {
		this.validateAnnotations();
		this.validateAdditionalCharacteristics();
		
		for(BillOfMaterialItem item:this.items)
			item.validate();
	}
	
	
	/**
	 * Validates the BillOfMaterial according to the annotations of the Validation Framework.
	 * 
	 * @exception Exception In case the validation failed.
	 */
	private void validateAnnotations() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<BillOfMaterial>> violations = validator.validate(this);
		
		for(ConstraintViolation<BillOfMaterial> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}
	
	
	/**
	 * Validates additional characteristics of the BillOfMaterial besides annotations.
	 * 
	 * @throws NoItemsException Indicates that the BillOfMaterial has no items defined.
	 * @throws DuplicateIdentifierException Indicates that multiple items share the same id.
	 */
	private void validateAdditionalCharacteristics() throws NoItemsException, DuplicateIdentifierException {
		this.validateItemsDefined();
		this.validateDistinctItemIds();
	}
	
	
	/**
	 * Checks if items are defined.
	 * 
	 * @throws NoItemsException If no items are defined
	 */
	private void validateItemsDefined() throws NoItemsException {
		if(this.items == null || this.items.size() == 0)
			throw new NoItemsException();
	}
	
	
	/**
	 * Checks if any item ID is used multiple times.
	 * 
	 * @throws DuplicateIdentifierException Indicates that an item ID is used multiple times.
	 */
	private void validateDistinctItemIds() throws DuplicateIdentifierException {
		Set<Integer> usedIds = new HashSet<Integer>();
		boolean isDistinctId;
		
		for(BillOfMaterialItem item:this.items) {
			isDistinctId = usedIds.add(item.getId());
			
			if(!isDistinctId) {
				throw new DuplicateIdentifierException(item.getId().toString());
			}
		}
	}
}
