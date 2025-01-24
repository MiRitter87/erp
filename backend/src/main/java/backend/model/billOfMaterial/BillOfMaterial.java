package backend.model.billOfMaterial;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;
import backend.model.material.Material;

/**
 * A bill of material defines a list of materials that are necessary to create another material.
 *
 * @author Michael
 */
@Table(name = "BILL_OF_MATERIAL")
@Entity
@SequenceGenerator(name = "billOfMaterialSequence", initialValue = 1, allocationSize = 1)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class BillOfMaterial {
    /**
     * The maximum name field length allowed.
     */
    private static final int MAX_NAME_LENGTH = 50;

    /**
     * The maximum description field length allowed.
     */
    private static final int MAX_DESCRIPTION_LENGTH = 250;

    /**
     * The distinct identification number.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billOfMaterialSequence")
    @Column(name = "BILL_OF_MATERIAL_ID")
    @Min(value = 1, message = "{billOfMaterial.id.min.message}")
    private Integer id;

    /**
     * The name.
     */
    @Column(name = "NAME", length = MAX_NAME_LENGTH)
    @Size(min = 1, max = MAX_NAME_LENGTH, message = "{billOfMaterial.name.size.message}")
    @NotNull(message = "{billOfMaterial.name.notNull.message}")
    private String name;

    /**
     * The description.
     */
    @Column(name = "DESCRIPTION", length = MAX_DESCRIPTION_LENGTH)
    @Size(min = 0, max = MAX_DESCRIPTION_LENGTH, message = "{billOfMaterial.description.size.message}")
    private String description;

    /**
     * The material whose parts are listed.
     */
    @OneToOne
    @JoinColumn(name = "MATERIAL_ID")
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
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(final Material material) {
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
    public void setItems(final List<BillOfMaterialItem> items) {
        this.items = items;
    }

    /**
     * Calculates the hashCode of a BillOfMaterial.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        result = prime * result + ((material == null) ? 0 : material.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * Indicates whether some other BillOfMaterial is "equal to" this one.
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
        BillOfMaterial other = (BillOfMaterial) obj;
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
        if (material == null) {
            if (other.material != null) {
                return false;
            }
        } else if (!material.equals(other.material)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }

        if (!this.areItemsEqual(other)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the list of items is equal.
     *
     * @param other The other BillOfMaterial for comparison.
     * @return true, if items are equal; false otherwise.
     */
    private boolean areItemsEqual(final BillOfMaterial other) {
        if (this.items == null && other.items != null) {
            return false;
        }

        if (this.items != null && other.items == null) {
            return false;
        }

        if (this.items.size() != other.items.size()) {
            return false;
        }

        for (BillOfMaterialItem tempItem : this.items) {
            BillOfMaterialItem otherItem = other.getItemWithId(tempItem.getId());

            if (otherItem == null) {
                return false;
            }

            if (!tempItem.equals(otherItem)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the item with the given id.
     *
     * @param itemId The id of the item.
     * @return The item with the given id, if found.
     */
    public BillOfMaterialItem getItemWithId(final Integer itemId) {
        for (BillOfMaterialItem tempItem : this.items) {
            if (tempItem.getId() == itemId) {
                return tempItem;
            }
        }

        return null;
    }

    /**
     * Validates the BillOfMaterial.
     *
     * @throws NoItemsException             Indicates that the BillOfMaterial has no items defined.
     * @throws DuplicateIdentifierException Indicates that multiple items share the same id.
     * @throws Exception                    In case a general validation error occurred.
     */
    public void validate() throws NoItemsException, DuplicateIdentifierException, Exception {
        this.validateAnnotations();
        this.validateAdditionalCharacteristics();

        for (BillOfMaterialItem item : this.items) {
            item.validate();
        }
    }

    /**
     * Validates the BillOfMaterial according to the annotations of the Validation Framework.
     *
     * @exception Exception In case the validation failed.
     */
    private void validateAnnotations() throws Exception {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .buildValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<BillOfMaterial>> violations = validator.validate(this);

        for (ConstraintViolation<BillOfMaterial> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }

    /**
     * Validates additional characteristics of the BillOfMaterial besides annotations.
     *
     * @throws NoItemsException             Indicates that the BillOfMaterial has no items defined.
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
        if (this.items == null || this.items.size() == 0) {
            throw new NoItemsException();
        }
    }

    /**
     * Checks if any item ID is used multiple times.
     *
     * @throws DuplicateIdentifierException Indicates that an item ID is used multiple times.
     */
    private void validateDistinctItemIds() throws DuplicateIdentifierException {
        Set<Integer> usedIds = new HashSet<Integer>();
        boolean isDistinctId;

        for (BillOfMaterialItem item : this.items) {
            isDistinctId = usedIds.add(item.getId());

            if (!isDistinctId) {
                throw new DuplicateIdentifierException(item.getId().toString());
            }
        }
    }
}
