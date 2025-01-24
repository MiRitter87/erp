package backend.model.productionOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;

/**
 * Represents an order to produce a certain amount of materials.
 *
 * @author Michael
 */
@Table(name = "PRODUCTION_ORDER")
@Entity
@SequenceGenerator(name = "productionOrderSequence", initialValue = 1, allocationSize = 1)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ProductionOrder {
    /**
     * The maximum status field length allowed.
     */
    private static final int MAX_STATUS_LENGTH = 10;

    /**
     * The ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productionOrderSequence")
    @Column(name = "PRODUCTION_ORDER_ID")
    @Min(value = 1, message = "{productionOrder.id.min.message}")
    private Integer id;

    /**
     * The order date.
     */
    @Column(name = "ORDER_DATE")
    private Date orderDate;

    /**
     * The planned execution date.
     */
    @Column(name = "PLANNED_EXECUTION_DATE")
    @NotNull(message = "{productionOrder.plannedExecutionDate.notNull.message}")
    private Date plannedExecutionDate;

    /**
     * The actual execution date.
     */
    @Column(name = "EXECUTION_DATE")
    private Date executionDate;

    /**
     * The status of the production order.
     */
    @Column(name = "STATUS", length = MAX_STATUS_LENGTH)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{productionOrder.status.notNull.message}")
    private ProductionOrderStatus status;

    /**
     * The items that are being produced.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "productionOrder")
    private List<ProductionOrderItem> items;

    /**
     * Default constructor.
     */
    public ProductionOrder() {
        this.orderDate = new Date();
        this.items = new ArrayList<ProductionOrderItem>();
    }

    /**
     * Adds a production order item to the production order.
     *
     * @param item The production order item.
     */
    public void addItem(final ProductionOrderItem item) {
        item.setProductionOrder(this);
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
     * @return the orderDate
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the plannedExecutionDate
     */
    public Date getPlannedExecutionDate() {
        return plannedExecutionDate;
    }

    /**
     * @param plannedExecutionDate the plannedExecutionDate to set
     */
    public void setPlannedExecutionDate(final Date plannedExecutionDate) {
        this.plannedExecutionDate = plannedExecutionDate;
    }

    /**
     * @return the executionDate
     */
    public Date getExecutionDate() {
        return executionDate;
    }

    /**
     * @param executionDate the executionDate to set
     */
    public void setExecutionDate(final Date executionDate) {
        this.executionDate = executionDate;
    }

    /**
     * @return the status
     */
    public ProductionOrderStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final ProductionOrderStatus status) {
        this.status = status;
        this.updateExecutionDate();
    }

    /**
     * @return the items
     */
    public List<ProductionOrderItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(final List<ProductionOrderItem> items) {
        this.items = items;
    }

    /**
     * Calculates the hashCode of a ProductionOrder.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((executionDate == null) ? 0 : executionDate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
        result = prime * result + ((plannedExecutionDate == null) ? 0 : plannedExecutionDate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    /**
     * Indicates whether some other ProductionOrder is "equal to" this one.
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
        ProductionOrder other = (ProductionOrder) obj;

        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }

        if (status != other.status) {
            return false;
        }

        if (!this.areDatesEqual(other)) {
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
     * @param other The other ProductionOrder for comparison.
     * @return true, if items are equal; false otherwise.
     */
    private boolean areItemsEqual(final ProductionOrder other) {
        if (this.items == null && other.items != null) {
            return false;
        }

        if (this.items != null && other.items == null) {
            return false;
        }

        if (this.items.size() != other.items.size()) {
            return false;
        }

        for (ProductionOrderItem tempItem : this.items) {
            ProductionOrderItem otherItem = other.getItemWithId(tempItem.getId());

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
     * Checks if the dates are equal.
     *
     * @param other The other ProductionOrder for comparison.
     * @return true, if dates are equal; false otherwise.
     */
    private boolean areDatesEqual(final ProductionOrder other) {
        if (executionDate == null && other.executionDate != null) {
            return false;
        }
        if (executionDate != null && other.executionDate == null) {
            return false;
        }
        if (executionDate != null && other.executionDate != null) {
            if (executionDate.getTime() != other.executionDate.getTime()) {
                return false;
            }
        }

        if (orderDate == null && other.orderDate != null) {
            return false;
        }
        if (orderDate != null && other.orderDate == null) {
            return false;
        }
        if (orderDate != null && other.orderDate != null) {
            if (orderDate.getTime() != other.orderDate.getTime()) {
                return false;
            }
        }

        if (plannedExecutionDate == null && other.plannedExecutionDate != null) {
            return false;
        }
        if (plannedExecutionDate != null && other.plannedExecutionDate == null) {
            return false;
        }
        if (plannedExecutionDate != null && other.plannedExecutionDate != null) {
            if (plannedExecutionDate.getTime() != other.plannedExecutionDate.getTime()) {
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
    public ProductionOrderItem getItemWithId(final Integer itemId) {
        for (ProductionOrderItem tempItem : this.items) {
            if (tempItem.getId() == itemId) {
                return tempItem;
            }
        }

        return null;
    }

    /**
     * Validates the production order.
     *
     * @throws NoItemsException             Indicates that the production order has no items defined.
     * @throws DuplicateIdentifierException Indicates that multiple items share the same id.
     * @throws Exception                    In case a general validation error occurred.
     */
    public void validate() throws NoItemsException, DuplicateIdentifierException, Exception {
        this.validateAnnotations();
        this.validateAdditionalCharacteristics();

        for (ProductionOrderItem item : this.items) {
            item.validate();
        }
    }

    /**
     * Validates the production order according to the annotations of the Validation Framework.
     *
     * @exception Exception In case the validation failed.
     */
    private void validateAnnotations() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ProductionOrder>> violations = validator.validate(this);

        for (ConstraintViolation<ProductionOrder> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }

    /**
     * Validates additional characteristics of the production order besides annotations.
     *
     * @throws NoItemsException             Indicates that the production order has no items defined.
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

        for (ProductionOrderItem item : this.items) {
            isDistinctId = usedIds.add(item.getId());

            if (!isDistinctId) {
                throw new DuplicateIdentifierException(item.getId().toString());
            }
        }
    }

    /**
     * Updates the execution date based on the current status.
     */
    private void updateExecutionDate() {
        if (this.executionDate == null && this.status == ProductionOrderStatus.FINISHED) {
            this.executionDate = new Date();
        }

        if (this.executionDate != null && this.status != ProductionOrderStatus.FINISHED) {
            this.executionDate = null;
        }
    }
}
