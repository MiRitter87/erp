package backend.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;


/**
 * A graphical image.
 * 
 * @author Michael
 */
@Table(name="IMAGE")
@Entity
@SequenceGenerator(name = "imageSequence", initialValue = 1, allocationSize = 1)
public class Image {
	/**
	 * The distinct identification number.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imageSequence")
	@Column(name="IMAGE_ID")
	private Integer id;
	
	/**
	 * The image data.
	 */
	@Lob
    @Column(name="DATA", nullable=false, columnDefinition="BLOB")
	@NotNull(message = "{image.data.notNull.message}")
    private byte[] data;
	
	
	/**
	 * Default constructor.
	 */
	public Image() {
		
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
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(byte[] data) {
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
		Set<ConstraintViolation<Image>> violations = validator.validate(this);
		
		for(ConstraintViolation<Image> violation:violations) {
			throw new Exception(violation.getMessage());
		}
	}
}
