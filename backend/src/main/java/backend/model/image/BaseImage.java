package backend.model.image;

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
    @Column(name = "IMAGE_ID")
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
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Calculates the hashCode of a BaseImage.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * Indicates whether some other BaseImage is "equal to" this one.
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
        BaseImage other = (BaseImage) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
