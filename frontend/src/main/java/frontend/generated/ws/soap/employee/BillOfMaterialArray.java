
package frontend.generated.ws.soap.employee;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für billOfMaterialArray complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="billOfMaterialArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billOfMaterials" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="billOfMaterial" type="{http://soap.webservice.backend/}billOfMaterial" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "billOfMaterialArray", propOrder = {
    "billOfMaterials"
})
public class BillOfMaterialArray {

    protected BillOfMaterialArray.BillOfMaterials billOfMaterials;

    /**
     * Ruft den Wert der billOfMaterials-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BillOfMaterialArray.BillOfMaterials }
     *     
     */
    public BillOfMaterialArray.BillOfMaterials getBillOfMaterials() {
        return billOfMaterials;
    }

    /**
     * Legt den Wert der billOfMaterials-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BillOfMaterialArray.BillOfMaterials }
     *     
     */
    public void setBillOfMaterials(BillOfMaterialArray.BillOfMaterials value) {
        this.billOfMaterials = value;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="billOfMaterial" type="{http://soap.webservice.backend/}billOfMaterial" maxOccurs="unbounded" minOccurs="0"/>
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
        "billOfMaterial"
    })
    public static class BillOfMaterials {

        protected List<BillOfMaterial> billOfMaterial;

        /**
         * Gets the value of the billOfMaterial property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the billOfMaterial property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBillOfMaterial().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BillOfMaterial }
         * 
         * 
         */
        public List<BillOfMaterial> getBillOfMaterial() {
            if (billOfMaterial == null) {
                billOfMaterial = new ArrayList<BillOfMaterial>();
            }
            return this.billOfMaterial;
        }

    }

}
