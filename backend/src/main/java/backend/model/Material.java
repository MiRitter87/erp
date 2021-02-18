package backend.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * A material.
 * 
 * @author Michael
 */
@Table(name="MATERIAL")
@Entity
@SequenceGenerator(name = "materialSequence", initialValue = 1, allocationSize = 1)
public class Material {
	/**
	 * The distinct identification number.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materialSequence")
	@Column(name="MATERIAL_ID")
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
	 * The unit of measurement.
	 */
	@Column(name="UNIT", length = 3)
	@Enumerated(EnumType.STRING)
	private UnitOfMeasurement unit;
	
	/**
	 * The price per unit.
	 */
	@Column(name="PRICE_PER_UNIT")
	private BigDecimal pricePerUnit;
	
	/**
	 * The currency of the price.
	 */
	@Column(name="CURRENCY", length = 3)
	@Enumerated(EnumType.STRING)
	private Currency currency;
	
	/**
	 * The inventory measured in the unit of measurement.
	 */
	@Column(name="INVENTORY")
	private Long inventory;
	
	
	/**
	 * Default constructor.
	 */
	public Material() {
		
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
	 * @return the unit
	 */
	public UnitOfMeasurement getUnit() {
		return unit;
	}


	/**
	 * @param unit the unit to set
	 */
	public void setUnit(UnitOfMeasurement unit) {
		this.unit = unit;
	}


	/**
	 * @return the pricePerUnit
	 */
	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}


	/**
	 * @param pricePerUnit the pricePerUnit to set
	 */
	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}


	/**
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}


	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}


	/**
	 * @return the inventory
	 */
	public Long getInventory() {
		return inventory;
	}


	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}
}
