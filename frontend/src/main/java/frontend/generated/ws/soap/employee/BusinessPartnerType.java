
package frontend.generated.ws.soap.employee;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für businessPartnerType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="businessPartnerType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CUSTOMER"/>
 *     &lt;enumeration value="VENDOR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "businessPartnerType")
@XmlEnum
public enum BusinessPartnerType {

    CUSTOMER,
    VENDOR;

    public String value() {
        return name();
    }

    public static BusinessPartnerType fromValue(String v) {
        return valueOf(v);
    }

}
