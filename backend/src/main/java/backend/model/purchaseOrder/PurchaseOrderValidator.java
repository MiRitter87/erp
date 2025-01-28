package backend.model.purchaseOrder;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

import backend.exception.DuplicateIdentifierException;
import backend.exception.NoItemsException;

/**
 * Performs attribute validations of the PurchaseOrder model.
 *
 * @author Michael
 */
public class PurchaseOrderValidator {
    /**
     * The PurchaseOrder to be validated.
     */
    private PurchaseOrder purchaseOrder;

    /**
     * Initializes the PurchaseOrderValidator.
     *
     * @param purchaseOrder The PurchaseOrder to be validated.
     */
    public PurchaseOrderValidator(final PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    /**
     * Validates the purchase order.
     *
     * @throws NoItemsException             Indicates that the purchase order has no items defined
     * @throws DuplicateIdentifierException Indicates that multiple items share the same id.
     * @throws Exception                    In case a general validation error occurred.
     */
    public void validate() throws NoItemsException, DuplicateIdentifierException, Exception {
        this.validateAnnotations();
        this.validateAdditionalCharacteristics();

        for (PurchaseOrderItem item : this.purchaseOrder.getItems()) {
            item.validate();
        }
    }

    /**
     * Validates the purchase order according to the annotations of the Validation Framework.
     *
     * @exception Exception In case the validation failed.
     */
    private void validateAnnotations() throws Exception {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .buildValidatorFactory();

        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<PurchaseOrder>> violations = validator.validate(this.purchaseOrder);

        for (ConstraintViolation<PurchaseOrder> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }

    /**
     * Validates additional characteristics of the purchase order besides annotations.
     *
     * @throws NoItemsException             Indicates that the purchase order has no items defined.
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
        if (this.purchaseOrder.getItems() == null || this.purchaseOrder.getItems().size() == 0) {
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

        for (PurchaseOrderItem item : this.purchaseOrder.getItems()) {
            isDistinctId = usedIds.add(item.getId());

            if (!isDistinctId) {
                throw new DuplicateIdentifierException(item.getId().toString());
            }
        }
    }
}
