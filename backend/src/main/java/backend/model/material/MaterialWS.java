package backend.model.material;

import java.math.BigDecimal;

import backend.model.Currency;

/**
 * A lean version of a material that is used by the WebService to transfer object data. The main difference to the
 * regular Material is that IDs are used instead of object references.
 *
 * @author Michael
 */
public class MaterialWS {
    /**
     * The ID of the material.
     */
    private Integer materialId;

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
     * The ID of the image.
     */
    private Integer imageId;

    /**
     * @return the materialId
     */
    public Integer getMaterialId() {
        return materialId;
    }

    /**
     * @param materialId the materialId to set
     */
    public void setMaterialId(final Integer materialId) {
        this.materialId = materialId;
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
    public void setName(final String name) {
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
    public void setDescription(final String description) {
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
    public void setUnit(final UnitOfMeasurement unit) {
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
    public void setPricePerUnit(final BigDecimal pricePerUnit) {
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
    public void setCurrency(final Currency currency) {
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
    public void setInventory(final Long inventory) {
        this.inventory = inventory;
    }

    /**
     * @return the imageId
     */
    public Integer getImageId() {
        return imageId;
    }

    /**
     * @param imageId the imageId to set
     */
    public void setImageId(final Integer imageId) {
        this.imageId = imageId;
    }
}
