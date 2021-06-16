package backend.model;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The metadata part of the image that is actually persisted as entity at database level.
 * 
 * @author Michael
 */
@Entity
@Table(name="IMAGE")
public class ImageMetaData extends BaseImage {

}
