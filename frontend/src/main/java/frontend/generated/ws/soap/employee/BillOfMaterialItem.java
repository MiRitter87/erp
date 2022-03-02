
package frontend.generated.ws.soap.employee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für billOfMaterialItem complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="billOfMaterialItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="billOfMaterial" type="{http://soap.webservice.backend/}billOfMaterial" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="material" type="{http://soap.webservice.backend/}material" minOccurs="0"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "billOfMaterialItem", propOrder = {
    "billOfMaterial",
    "id",
    "material",
    "quantity"
})
public class BillOfMaterialItem {

    protected BillOfMaterial billOfMaterial;
    protected Integer id;
    protected Material material;
    protected Integer quantity;

    /**
     * Ruft den Wert der billOfMaterial-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BillOfMaterial }
     *     
     */
    public BillOfMaterial getBillOfMaterial() {
        return billOfMaterial;
    }

    /**
     * Legt den Wert der billOfMaterial-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BillOfMaterial }
     *     
     */
    public void setBillOfMaterial(BillOfMaterial value) {
        this.billOfMaterial = value;
    }

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Ruft den Wert der material-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Material }
     *     
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Legt den Wert der material-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Material }
     *     
     */
    public void setMaterial(Material value) {
        this.material = value;
    }

    /**
     * Ruft den Wert der quantity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Legt den Wert der quantity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQuantity(Integer value) {
        this.quantity = value;
    }

}
