package backend.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * The metadata part of the image that is actually persisted as entity at database level.
 * 
 * @author Michael
 */
@Entity
@Table(name="IMAGE")
public class ImageMetaData extends BaseImage {
	/**
	 * The file type as defined by the file extension of the file name.
	 */
	@Column(name="FILE_TYPE", length = 3)
	@Size(min = 1, max = 3, message = "{image.fileType.size.message}")
	@NotNull(message = "{image.fileType.notNull.message}")
	private String fileType;

	
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	/**
	 * Validates the image meta data.
	 * 
	 * @throws Exception In case a general validation error occurred.
	 */
	public void validate() throws Exception {
		this.validateAnnotations();		
	}
	
	
	/**
	 * Validates the image meta data according to the annotations of the Validation Framework.
	 * 
	 * @exception Exception In case the validation failed.
	 */
	private void validateAnnotations() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ImageMetaData>> violations = validator.validate(this);
		
		for(ConstraintViolation<ImageMetaData> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}
}
