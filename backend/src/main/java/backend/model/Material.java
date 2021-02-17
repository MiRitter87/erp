package backend.model;

import java.math.BigDecimal;

/**
 * A material.
 * 
 * @author Michael
 */
public class Material {
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
	 * The unit of measurement.
	 */
	private UnitOfMeasurement unit;
	
	/**
	 * The price per unit.
	 */
	private BigDecimal pricePerUnit;
	
	/**
	 * The currency of the price.
	 */
	private Currency currency;
	
	/**
	 * The inventory measured in the unit of measurement.
	 */
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
