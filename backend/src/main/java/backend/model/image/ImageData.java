package backend.model.image;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;

/**
 * The data part of the image that is actually persisted as entity at database level.
 *
 * @author Michael
 */
@Entity
@Table(name = "IMAGE")
public class ImageData extends BaseImage {
    /**
     * The image data.
     */
    @Lob
    @Column(name = "DATA", nullable = false, columnDefinition = "BLOB")
    @NotNull(message = "{image.data.notNull.message}")
    private byte[] data;

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(final byte[] data) {
        this.data = data;
    }

    /**
     * Validates the image.
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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BaseImage>> violations = validator.validate(this);

        for (ConstraintViolation<BaseImage> violation : violations) {
            throw new Exception(violation.getMessage());
        }
    }
}
