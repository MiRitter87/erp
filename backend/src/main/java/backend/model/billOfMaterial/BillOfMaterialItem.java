package backend.model.billOfMaterial;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import backend.model.material.Material;

/**
 * A bill of material item defines a material in a specific quantity that is needed for creation of another material.
 *
 * @author Michael
 */
@Table(name = "BILL_OF_MATERIAL_ITEM")
@Entity
@IdClass(BillOfMaterialItemId.class)
public class BillOfMaterialItem {
    /**
     * The distinct identification number.
     */
    @Id
    @Column(name = "ITEM_ID")
    @NotNull(message = "{billOfMaterialItem.id.notNull.message}")
    @Min(value = 1, message = "{billOfMaterialItem.id.min.message}")
    private Integer id;

    /**
     * The BillOfMaterial to which the item belongs to.
     */
    @Id
    @JoinColumn(name = "BILL_OF_MATERIAL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private BillOfMaterial billOfMaterial;

    /**
     * The material needed.
     */
    @OneToOne
    @JoinColumn(name = "MATERIAL_ID")
    @NotNull(message = "{billOfMaterialItem.material.notNull.message}")
    private Material material;

    /**
     * The quantity of the material.
     */
    @Column(name = "QUANTITY")
    @NotNull(message = "{billOfMaterialItem.quantity.notNull.message}")
    @Min(value = 1, message = "{billOfMaterialItem.quantity.min.message}")
    private Integer quantity;

    /**
     * Default constructor.
     */
    public BillOfMaterialItem() {

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
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the billOfMaterial
     */
    public BillOfMaterial getBillOfMaterial() {
        return billOfMaterial;
    }

    /**
     * @param billOfMaterial the billOfMaterial to set
     */
    public void setBillOfMaterial(final BillOfMaterial billOfMaterial) {
        this.billOfMaterial = billOfMaterial;
    }

    /**
     * Calculates the hashCode of a BillOfMaterialItem.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((material == null) ? 0 : material.hashCode());
        result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
        return result;
    }

    /**
     * Indicates whether some other BillOfMaterialItem is "equal to" this one.
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
        BillOfMaterialItem other = (BillOfMaterialItem) obj;
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

    /**
     * Validates the BillOfMaterialItem.
     *
     * @throws Exception In case a general validation error occurred.
     */
    public void validate() throws Exception {
        this.validateAnnotations();
    }

    /**
     * Validates the BillOfMaterialItem according to the annotations of the Validation Framework.
     *
     * @exception Exception In case the validation failed.
     */
    private void validateAnnotations() throws Exception {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .buildValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<BillOfMaterialItem>> violations = validator.validate(this);

        for (ConstraintViolation<BillOfMaterialItem> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }
}
