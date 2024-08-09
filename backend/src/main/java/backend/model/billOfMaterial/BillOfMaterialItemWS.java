package backend.model.billOfMaterial;

/**
 * A lean version of a BillOfMaterialItem that is used by the WebService to transfer object data. The main difference to
 * the regular BillOfMaterialItem is that IDs are used instead of object references.
 *
 * @author Michael
 */
public class BillOfMaterialItemWS {
    /**
     * The distinct identification number.
     */
    private Integer itemId;

    /**
     * The ID of the material needed.
     */
    private Integer materialId;

    /**
     * The quantity of the material.
     */
    private Integer quantity;

    /**
     * @return the id
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Integer id) {
        this.itemId = id;
    }

    /**
     * @return the materialId
     */
    public Integer getMaterialId() {
        return materialId;
    }

    /**
     * @param materialId the materialId to set
     */
    public void setMaterialId(final Integer materialId) {
        this.materialId = materialId;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }
}
