
package frontend.generated.ws.soap.employee;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r employeeHeadQueryParameter.
 *
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="employeeHeadQueryParameter">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="HEAD_ONLY"/>
 *     &lt;enumeration value="NO_HEAD_ONLY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "employeeHeadQueryParameter")
@XmlEnum
public enum EmployeeHeadQueryParameter {

    ALL,
    HEAD_ONLY,
    NO_HEAD_ONLY;

    public String value() {
        return name();
    }

    public static EmployeeHeadQueryParameter fromValue(String v) {
        return valueOf(v);
    }

}
