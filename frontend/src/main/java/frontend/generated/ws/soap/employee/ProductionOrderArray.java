
package frontend.generated.ws.soap.employee;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für productionOrderArray complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="productionOrderArray">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="productionOrders" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="productionOrder" type="{http://soap.webservice.backend/}productionOrder" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "productionOrderArray", propOrder = {
    "productionOrders"
})
public class ProductionOrderArray {

    protected ProductionOrderArray.ProductionOrders productionOrders;

    /**
     * Ruft den Wert der productionOrders-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ProductionOrderArray.ProductionOrders }
     *     
     */
    public ProductionOrderArray.ProductionOrders getProductionOrders() {
        return productionOrders;
    }

    /**
     * Legt den Wert der productionOrders-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductionOrderArray.ProductionOrders }
     *     
     */
    public void setProductionOrders(ProductionOrderArray.ProductionOrders value) {
        this.productionOrders = value;
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
     *         &lt;element name="productionOrder" type="{http://soap.webservice.backend/}productionOrder" maxOccurs="unbounded" minOccurs="0"/>
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
        "productionOrder"
    })
    public static class ProductionOrders {

        protected List<ProductionOrder> productionOrder;

        /**
         * Gets the value of the productionOrder property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the productionOrder property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProductionOrder().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProductionOrder }
         * 
         * 
         */
        public List<ProductionOrder> getProductionOrder() {
            if (productionOrder == null) {
                productionOrder = new ArrayList<ProductionOrder>();
            }
            return this.productionOrder;
        }

    }

}
