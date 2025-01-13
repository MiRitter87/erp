package backend.model.material;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import backend.model.Currency;
import backend.model.image.ImageMetaData;

/**
 * A material.
 *
 * @author Michael
 */
@Table(name = "MATERIAL")
@Entity
@SequenceGenerator(name = "materialSequence", initialValue = 1, allocationSize = 1)
public class Material {
    /**
     * The maximum name field length allowed.
     */
    private static final int MAX_NAME_LENGTH = 50;

    /**
     * The maximum description field length allowed.
     */
    private static final int MAX_DESCRIPTION_LENGTH = 250;

    /**
     * The maximum unit field length allowed.
     */
    private static final int MAX_UNIT_LENGTH = 3;

    /**
     * The maximum currency field length allowed.
     */
    private static final int MAX_CURRENCY_LENGTH = 3;

    /**
     * The distinct identification number.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materialSequence")
    @Column(name = "MATERIAL_ID")
    @Min(value = 1, message = "{material.id.min.message}")
    private Integer id;

    /**
     * The name.
     */
    @Column(name = "NAME", length = MAX_NAME_LENGTH)
    @Size(min = 1, max = MAX_NAME_LENGTH, message = "{material.name.size.message}")
    @NotNull(message = "{material.name.notNull.message}")
    private String name;

    /**
     * The description.
     */
    @Column(name = "DESCRIPTION", length = MAX_DESCRIPTION_LENGTH)
    @Size(min = 0, max = MAX_DESCRIPTION_LENGTH, message = "{material.description.size.message}")
    private String description;

    /**
     * The unit of measurement.
     */
    @Column(name = "UNIT", length = MAX_UNIT_LENGTH)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{material.unit.notNull.message}")
    private UnitOfMeasurement unit;

    /**
     * The price per unit.
     */
    @Column(name = "PRICE_PER_UNIT")
    @NotNull(message = "{material.pricePerUnit.notNull.message}")
    private BigDecimal pricePerUnit;

    /**
     * The currency of the price.
     */
    @Column(name = "CURRENCY", length = MAX_CURRENCY_LENGTH)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{material.currency.notNull.message}")
    private Currency currency;

    /**
     * The inventory measured in the unit of measurement.
     */
    @Column(name = "INVENTORY")
    @Min(value = 0, message = "{material.inventory.min.message}")
    @NotNull(message = "{material.inventory.notNull.message}")
    private Long inventory;

    /**
     * An image of the material.
     */
    @OneToOne(targetEntity = ImageMetaData.class, cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "IMAGE_ID")
    private ImageMetaData image;

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
    public void setId(final Integer id) {
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
     * @return the image
     */
    public ImageMetaData getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(final ImageMetaData image) {
        this.image = image;
    }

    /**
     * Converts the material to the lean WebService representation.
     *
     * @return The lean WebService representation of the material.
     */
    @JsonIgnore
    public MaterialWS getWsMaterial() {
        MaterialWS wsMaterial = new MaterialWS();

        wsMaterial.setMaterialId(this.getId());
        wsMaterial.setName(this.getName());
        wsMaterial.setDescription(this.getDescription());
        wsMaterial.setUnit(this.getUnit());
        wsMaterial.setPricePerUnit(this.getPricePerUnit());
        wsMaterial.setCurrency(this.getCurrency());
        wsMaterial.setInventory(this.getInventory());

        if (this.getImage() != null && this.getImage().getId() != null) {
            wsMaterial.setImageId(this.getImage().getId());
        }

        return wsMaterial;
    }

    /**
     * Validates the material.
     *
     * @throws Exception In case a general validation error occurred.
     */
    public void validate() throws Exception {
        this.validateAnnotations();
    }

    /**
     * Validates the material according to the annotations of the Validation Framework.
     *
     * @exception Exception In case the validation failed.
     */
    private void validateAnnotations() throws Exception {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .buildValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Material>> violations = validator.validate(this);

        for (ConstraintViolation<Material> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }

    /**
     * Calculates the hashCode of a Material.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((inventory == null) ? 0 : inventory.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((pricePerUnit == null) ? 0 : pricePerUnit.hashCode());
        result = prime * result + ((unit == null) ? 0 : unit.hashCode());
        result = prime * result + ((image == null) ? 0 : image.hashCode());
        return result;
    }

    /**
     * Indicates whether some other Material is "equal to" this one.
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Material other = (Material) obj;
        if (!this.areAttributesEqual(other)) {
            return false;
        }
        if (image == null) {
            if (other.image != null) {
                return false;
            }
        } else if (!image.equals(other.image)) {
            return false;
        }
        return true;
    }

    /**
     * Indicates whether the attributes of some other Material is "equal to" this one.
     *
     * @param other The other Material.
     * @return true if attributes are equal.
     */
    private boolean areAttributesEqual(final Material other) {
        if (currency != other.currency) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (inventory == null) {
            if (other.inventory != null) {
                return false;
            }
        } else if (!inventory.equals(other.inventory)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (pricePerUnit == null) {
            if (other.pricePerUnit != null) {
                return false;
            }
        } else if (!pricePerUnit.equals(other.pricePerUnit)) {
            return false;
        }
        if (unit != other.unit) {
            return false;
        }
        return true;
    }
}
