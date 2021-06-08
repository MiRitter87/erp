package backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


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
}
