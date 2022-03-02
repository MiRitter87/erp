
package frontend.generated.ws.soap.employee;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für unitOfMeasurement.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="unitOfMeasurement">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ST"/>
 *     &lt;enumeration value="L"/>
 *     &lt;enumeration value="KG"/>
 *     &lt;enumeration value="T"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "unitOfMeasurement")
@XmlEnum
public enum UnitOfMeasurement {

    ST,
    L,
    KG,
    T;

    public String value() {
        return name();
    }

    public static UnitOfMeasurement fromValue(String v) {
        return valueOf(v);
    }

}
