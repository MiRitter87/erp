package backend.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;


/**
 * A graphical image.
 * 
 * @author Michael
 */
@MappedSuperclass
@SequenceGenerator(name = "imageSequence", initialValue = 1, allocationSize = 1)
public class BaseImage {
	/**
	 * The distinct identification number.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imageSequence")
	@Column(name="IMAGE_ID")
	private Integer id;
		
	
	/**
	 * Default constructor.
	 */
	public BaseImage() {
		
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
}
