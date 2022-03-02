
package frontend.generated.ws.soap.employee;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für purchaseOrderStatus.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="purchaseOrderStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OPEN"/>
 *     &lt;enumeration value="IN_PROCESS"/>
 *     &lt;enumeration value="FINISHED"/>
 *     &lt;enumeration value="CANCELED"/>
 *     &lt;enumeration value="INVOICE_RECEIPT"/>
 *     &lt;enumeration value="GOODS_RECEIPT"/>
 *     &lt;enumeration value="INVOICE_SETTLED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "purchaseOrderStatus")
@XmlEnum
public enum PurchaseOrderStatus {

    OPEN,
    IN_PROCESS,
    FINISHED,
    CANCELED,
    INVOICE_RECEIPT,
    GOODS_RECEIPT,
    INVOICE_SETTLED;

    public String value() {
        return name();
    }

    public static PurchaseOrderStatus fromValue(String v) {
        return valueOf(v);
    }

}
