package backend.model.billOfMaterial;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * A list of BillOfMaterials.
 *
 * @author Michael
 */
public class BillOfMaterialArray {
    /**
     * A list of BillOfMaterials.
     */
    private List<BillOfMaterial> billOfMaterials = null;

    /**
     * @return the billOfMaterials
     */
    @XmlElementWrapper(name = "billOfMaterials")
    @XmlElement(name = "billOfMaterial")
    public List<BillOfMaterial> getBillOfMaterials() {
        return billOfMaterials;
    }

    /**
     * @param billOfMaterials the billOfMaterials to set
     */
    public void setBillOfMaterials(final List<BillOfMaterial> billOfMaterials) {
        this.billOfMaterials = billOfMaterials;
    }
}
