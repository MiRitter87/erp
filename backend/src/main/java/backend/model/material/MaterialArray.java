package backend.model.material;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A list of materials.
 *
 * @author Michael
 */
@XmlRootElement(name = "materials")
public class MaterialArray {
    /**
     * A list of materials.
     */
    private List<Material> materials = null;

    /**
     * @return the materials
     */
    @XmlElementWrapper(name = "materials")
    @XmlElement(name = "material")
    public List<Material> getMaterials() {
        return materials;
    }

    /**
     * @param materials the materials to set
     */
    public void setMaterials(final List<Material> materials) {
        this.materials = materials;
    }
}
