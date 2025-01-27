package backend.model.productionOrder;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import backend.model.material.Material;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * An item of a production order representing a material in a certain quantity that is going to be produced.
 *
 * @author Michael
 */
@Table(name = "PRODUCTION_ORDER_ITEM")
@Entity
@IdClass(ProductionOrderItemId.class)
public class ProductionOrderItem {
    /**
     * The ID.
     */
    @Id
    @Column(name = "ITEM_ID")
    @NotNull(message = "{productionOrderItem.id.notNull.message}")
    @Min(value = 1, message = "{productionOrderItem.id.min.message}")
    private Integer id;

    /**
     * The production order to which the item belongs to.
     */
    @Id
    @JoinColumn(name = "PRODUCTION_ORDER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ProductionOrder productionOrder;

    /**
     * The material that is being produced.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATERIAL_ID")
    @NotNull(message = "{productionOrderItem.material.notNull.message}")
    private Material material;

    /**
     * The quantity that is being produced.
     */
    @Column(name = "QUANTITY")
    @NotNull(message = "{productionOrderItem.quantity.notNull.message}")
    @Min(value = 1, message = "{productionOrderItem.quantity.min.message}")
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
    public void setId(final Integer id) {
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
    public void setProductionOrder(final ProductionOrder productionOrder) {
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
    public void setMaterial(final Material material) {
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
    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    /**
     * Calculates the hashCode of a ProductionOrderItem.
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
     * Indicates whether some other ProductionOrderItem is "equal to" this one.
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

    /**
     * Validates the production order item.
     *
     * @throws Exception In case a general validation error occurred.
     */
    public void validate() throws Exception {
        this.validateAnnotations();
    }

    /**
     * Validates the production order item according to the annotations of the Validation Framework.
     *
     * @exception Exception In case the validation failed.
     */
    private void validateAnnotations() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ProductionOrderItem>> violations = validator.validate(this);

        for (ConstraintViolation<ProductionOrderItem> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }
}
