package backend.model.billOfMaterial;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(billOfMaterial, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BillOfMaterialItemId other = (BillOfMaterialItemId) obj;
        return Objects.equals(billOfMaterial, other.billOfMaterial) && Objects.equals(id, other.id);
    }
}
