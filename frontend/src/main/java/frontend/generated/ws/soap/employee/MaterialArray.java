
package frontend.generated.ws.soap.employee;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r materialArray complex type.
 *
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 *
 * <pre>
 * &lt;complexType name="materialArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="materials" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="material" type="{http://soap.webservice.backend/}material" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "materialArray", propOrder = {
    "materials"
})
public class MaterialArray {

    protected MaterialArray.Materials materials;

    /**
     * Ruft den Wert der materials-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link MaterialArray.Materials }
     *
     */
    public MaterialArray.Materials getMaterials() {
        return materials;
    }

    /**
     * Legt den Wert der materials-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link MaterialArray.Materials }
     *
     */
    public void setMaterials(MaterialArray.Materials value) {
        this.materials = value;
    }


    /**
     * <p>Java-Klasse f�r anonymous complex type.
     *
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="material" type="{http://soap.webservice.backend/}material" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "material"
    })
    public static class Materials {

        protected List<Material> material;

        /**
         * Gets the value of the material property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the material property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMaterial().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Material }
         *
         *
         */
        public List<Material> getMaterial() {
            if (material == null) {
                material = new ArrayList<Material>();
            }
            return this.material;
        }

    }

}
