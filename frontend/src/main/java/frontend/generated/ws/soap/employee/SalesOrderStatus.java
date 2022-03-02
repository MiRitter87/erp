
package frontend.generated.ws.soap.employee;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für salesOrderStatus.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="salesOrderStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OPEN"/>
 *     &lt;enumeration value="IN_PROCESS"/>
 *     &lt;enumeration value="FINISHED"/>
 *     &lt;enumeration value="CANCELED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "salesOrderStatus")
@XmlEnum
public enum SalesOrderStatus {

    OPEN,
    IN_PROCESS,
    FINISHED,
    CANCELED;

    public String value() {
        return name();
    }

    public static SalesOrderStatus fromValue(String v) {
        return valueOf(v);
    }

}
