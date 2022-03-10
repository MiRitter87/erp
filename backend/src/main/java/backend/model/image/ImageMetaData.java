package backend.model.image;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Size;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;


/**
 * The metadata part of the image that is actually persisted as entity at database level.
 * 
 * @author Michael
 */
@Entity
@Table(name="IMAGE")
public class ImageMetaData extends BaseImage {
	/**
	 * The MIME type of the file. For example: "image/png".
	 */
	@Column(name="MIME_TYPE", length = 25)
	@Size(min = 1, max = 25, message = "{image.mimeType.size.message}")
	private String mimeType;

	
	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	
	/**
	 * @param mimeType the fileType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mimeType == null) ? 0 : mimeType.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ImageMetaData other = (ImageMetaData) obj;
		if (mimeType == null) {
			if (other.mimeType != null) {
				return false;
			}
		} else if (!mimeType.equals(other.mimeType)) {
			return false;
		}
		return true;
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
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)   
                .configure().constraintExpressionLanguageFeatureLevel(ExpressionLanguageFeatureLevel.BEAN_METHODS)
                .buildValidatorFactory();

		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<ImageMetaData>> violations = validator.validate(this);
		
		for(ConstraintViolation<ImageMetaData> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}
}
