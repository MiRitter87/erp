
package frontend.generated.ws.soap.employee;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r productionOrderItem complex type.
 *
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 *
 * <pre>
 * &lt;complexType name="productionOrderItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="material" type="{http://soap.webservice.backend/}material" minOccurs="0"/>
 *         &lt;element name="productionOrder" type="{http://soap.webservice.backend/}productionOrder" minOccurs="0"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "productionOrderItem", propOrder = {
    "id",
    "material",
    "productionOrder",
    "quantity"
})
public class ProductionOrderItem {

    protected Integer id;
    protected Material material;
    protected ProductionOrder productionOrder;
    protected Long quantity;

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
     * Ruft den Wert der productionOrder-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link ProductionOrder }
     *
     */
    public ProductionOrder getProductionOrder() {
        return productionOrder;
    }

    /**
     * Legt den Wert der productionOrder-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link ProductionOrder }
     *
     */
    public void setProductionOrder(ProductionOrder value) {
        this.productionOrder = value;
    }

    /**
     * Ruft den Wert der quantity-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * Legt den Wert der quantity-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setQuantity(Long value) {
        this.quantity = value;
    }

}
