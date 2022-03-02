
package frontend.generated.ws.soap.employee;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für postingType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="postingType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RECEIPT"/>
 *     &lt;enumeration value="DISBURSAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "postingType")
@XmlEnum
public enum PostingType {

    RECEIPT,
    DISBURSAL;

    public String value() {
        return name();
    }

    public static PostingType fromValue(String v) {
        return valueOf(v);
    }

}
