package backend.model.billOfMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * A lean version of a BillOfMaterial that is used by the WebService to transfer object data. The main difference to the
 * regular BillOfMaterial is that IDs are used instead of object references.
 *
 * @author Michael
 */
public class BillOfMaterialWS {
    /**
     * The distinct identification number.
     */
    private Integer billOfMaterialId;

    /**
     * The name.
     */
    private String name;

    /**
     * The description.
     */
    private String description;

    /**
     * The material whose parts are listed.
     */
    private Integer materialId;

    /**
     * The items needed to create a material.
     */
    private List<BillOfMaterialItemWS> items;

    /**
     * Constructor.
     */
    public BillOfMaterialWS() {
        this.items = new ArrayList<BillOfMaterialItemWS>();
    }

    /**
     * Adds the given item to the BillOfMaterial.
     *
     * @param billOfMaterialItem The item to be added.
     */
    public void addItem(final BillOfMaterialItemWS billOfMaterialItem) {
        this.items.add(billOfMaterialItem);
    }

    /**
     * @return the billOfMaterialId
     */
    public Integer getBillOfMaterialId() {
        return billOfMaterialId;
    }

    /**
     * @param billOfMaterialId the billOfMaterialId to set
     */
    public void setBillOfMaterialId(final Integer billOfMaterialId) {
        this.billOfMaterialId = billOfMaterialId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
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
     * @return the items
     */
    public List<BillOfMaterialItemWS> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(final List<BillOfMaterialItemWS> items) {
        this.items = items;
    }
}
