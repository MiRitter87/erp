package backend.model.salesOrder;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;
import backend.exception.QuantityExceedsInventoryException;

/**
 * Performs attribute validations of the SalesOrder model.
 *
 * @author Michael
 */
public class SalesOrderValidator {
    /**
     * The SalesOrder to be validated.
     */
    private SalesOrder salesOrder;

    /**
     * Initializes the SalesOrderValidator.
     *
     * @param salesOrder The SalesOrder to be validated.
     */
    public SalesOrderValidator(final SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    /**
     * Validates the sales order.
     *
     * @throws NoItemsException                  Indicates that the sales order has no items defined.
     * @throws DuplicateIdentifierException      Indicates that multiple items share the same id.
     * @throws QuantityExceedsInventoryException Indicates that the ordered quantity exceeds the inventory.
     * @throws Exception                         In case a general validation error occurred.
     */
    public void validate()
            throws NoItemsException, DuplicateIdentifierException, QuantityExceedsInventoryException, Exception {
        this.validateAnnotations();
        this.validateAdditionalCharacteristics();

        for (SalesOrderItem item : this.salesOrder.getItems()) {
            item.validate();
        }
    }

    /**
     * Validates the sales order according to the annotations of the Validation Framework.
     *
     * @exception Exception In case the validation failed.
     */
    private void validateAnnotations() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SalesOrder>> violations = validator.validate(this.salesOrder);

        for (ConstraintViolation<SalesOrder> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }

    /**
     * Validates additional characteristics of the sales order besides annotations.
     *
     * @throws NoItemsException             Indicates that the sales order has no items defined.
     * @throws DuplicateIdentifierException Indicates that multiple items share the same id.
     */
    private void validateAdditionalCharacteristics() throws NoItemsException, DuplicateIdentifierException {
        this.validateItemsDefined();
        this.validateDistinctItemIds();
    }

    /**
     * Checks if any item ID is used multiple times.
     *
     * @throws DuplicateIdentifierException Indicates that an item ID is used multiple times.
     */
    private void validateDistinctItemIds() throws DuplicateIdentifierException {
        Set<Integer> usedIds = new HashSet<Integer>();
        boolean isDistinctId;

        for (SalesOrderItem item : this.salesOrder.getItems()) {
            isDistinctId = usedIds.add(item.getId());

            if (!isDistinctId) {
                throw new DuplicateIdentifierException(item.getId().toString());
            }
        }
    }

    /**
     * Checks if items are defined.
     *
     * @throws NoItemsException If no items are defined
     */
    private void validateItemsDefined() throws NoItemsException {
        if (this.salesOrder.getItems() == null || this.salesOrder.getItems().size() == 0) {
            throw new NoItemsException();
        }
    }
}
