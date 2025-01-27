package backend.model.purchaseOrder;

import java.math.BigDecimal;
import java.util.Set;

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
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * An item of a purchase order representing a product in a certain quantity.
 *
 * @author Michael
 */
@Table(name = "PURCHASE_ORDER_ITEM")
@Entity
@IdClass(PurchaseOrderItemId.class)
public class PurchaseOrderItem {
    /**
     * The ID.
     */
    @Id
    @Column(name = "ITEM_ID")
    @NotNull(message = "{purchaseOrderItem.id.notNull.message}")
    @Min(value = 1, message = "{purchaseOrderItem.id.min.message}")
    private Integer id;

    /**
     * The PurchaseOrder to which the item belongs to.
     */
    @Id
    @JoinColumn(name = "PURCHASE_ORDER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private PurchaseOrder purchaseOrder;

    /**
     * The material that is being ordered.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATERIAL_ID")
    @NotNull(message = "{purchaseOrderItem.material.notNull.message}")
    private Material material;

    /**
     * The quantitiy that is being ordered.
     */
    @Column(name = "QUANTITY")
    @NotNull(message = "{purchaseOrderItem.quantity.notNull.message}")
    @Min(value = 1, message = "{purchaseOrderItem.quantity.min.message}")
    private Long quantity;

    /**
     * The total price of the purchase order item at the time of order issuance.
     */
    @Column(name = "PRICE_TOTAL")
    private BigDecimal priceTotal;

    /**
     * Default constructor.
     */
    public PurchaseOrderItem() {

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
     * @return the purchaseOrder
     */
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    /**
     * @param purchaseOrder the purchaseOrder to set
     */
    public void setPurchaseOrder(final PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
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
        this.updatePriceTotal();
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
        this.updatePriceTotal();
    }

    /**
     * @return the priceTotal
     */
    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    /**
     * @param priceTotal the priceTotal to set
     */
    public void setPriceTotal(final BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    /**
     * Calculates the hashCode of a PurchaseOrderItem.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((material == null) ? 0 : material.hashCode());
        result = prime * result + ((priceTotal == null) ? 0 : priceTotal.hashCode());
        result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
        return result;
    }

    /**
     * Indicates whether some other PurchaseOrderItem is "equal to" this one.
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
        PurchaseOrderItem other = (PurchaseOrderItem) obj;
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
        if (priceTotal == null) {
            if (other.priceTotal != null) {
                return false;
            }
        } else if (!priceTotal.equals(other.priceTotal)) {
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
     * Updates the total price of the purchase order item based on quantity and price per unit of the material.
     */
    private void updatePriceTotal() {
        if (this.material == null || this.material.getPricePerUnit() == null || this.quantity == null) {
            return;
        }

        this.priceTotal = this.material.getPricePerUnit().multiply(new BigDecimal(this.quantity));
    }

    /**
     * Validates the purchase order item.
     *
     * @throws Exception In case a general validation error occurred.
     */
    public void validate() throws Exception {
        this.validateAnnotations();
    }

    /**
     * Validates the purchase order item according to the annotations of the Validation Framework.
     *
     * @exception Exception In case the validation failed.
     */
    private void validateAnnotations() throws Exception {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .buildValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(this);

        for (ConstraintViolation<PurchaseOrderItem> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }
}
