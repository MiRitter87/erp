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
    private static final long serialVersionUID = -6642671110903639551L;

    /**
     * BillOfMaterial item id.
     */
    private Integer id;

    /**
     * The BillOfMaterial.
     */
    private Integer billOfMaterial;

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
     * @return the billOfMaterial
     */
    public Integer getBillOfMaterial() {
        return billOfMaterial;
    }

    /**
     * @param billOfMaterial the billOfMaterial to set
     */
    public void setBillOfMaterial(final Integer billOfMaterial) {
        this.billOfMaterial = billOfMaterial;
    }

    /**
     * Calculates the hashCode of a BillOfMaterialItemId.
     */
    @Override
    public int hashCode() {
        return Objects.hash(billOfMaterial, id);
    }

    /**
     * Indicates whether some other BillOfMaterialItemId is "equal to" this one.
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
        BillOfMaterialItemId other = (BillOfMaterialItemId) obj;
        return Objects.equals(billOfMaterial, other.billOfMaterial) && Objects.equals(id, other.id);
    }
}
