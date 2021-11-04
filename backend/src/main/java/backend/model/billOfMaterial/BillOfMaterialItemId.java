package backend.model.billOfMaterial;

import java.io.Serializable;

/**
 * The composite primary key of the BillOfMaterialItem.
 * 
 * @author Michael
 */
public class BillOfMaterialItemId implements Serializable {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = -2012815539436218532L;

	/**
	 * The BillOfMaterial.
	 */
	public BillOfMaterial billOfMaterial;
	
	/**
	 * BillOfMaterial item id.
	 */
	public Integer id;
}
