
package frontend.generated.ws.soap.employee;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für purchaseOrderItem complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="purchaseOrderItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="material" type="{http://soap.webservice.backend/}material" minOccurs="0"/>
 *         &lt;element name="priceTotal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="purchaseOrder" type="{http://soap.webservice.backend/}purchaseOrder" minOccurs="0"/>
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
@XmlType(name = "purchaseOrderItem", propOrder = {
    "id",
    "material",
    "priceTotal",
    "purchaseOrder",
    "quantity"
})
public class PurchaseOrderItem {

    protected Integer id;
    protected Material material;
    protected BigDecimal priceTotal;
    protected PurchaseOrder purchaseOrder;
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
     * Ruft den Wert der priceTotal-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    /**
     * Legt den Wert der priceTotal-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPriceTotal(BigDecimal value) {
        this.priceTotal = value;
    }

    /**
     * Ruft den Wert der purchaseOrder-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseOrder }
     *     
     */
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    /**
     * Legt den Wert der purchaseOrder-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseOrder }
     *     
     */
    public void setPurchaseOrder(PurchaseOrder value) {
        this.purchaseOrder = value;
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
