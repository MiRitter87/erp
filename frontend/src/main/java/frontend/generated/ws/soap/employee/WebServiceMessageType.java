
package frontend.generated.ws.soap.employee;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für webServiceMessageType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="webServiceMessageType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="I"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="E"/>
 *     &lt;enumeration value="W"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "webServiceMessageType")
@XmlEnum
public enum WebServiceMessageType {

    I,
    S,
    E,
    W;

    public String value() {
        return name();
    }

    public static WebServiceMessageType fromValue(String v) {
        return valueOf(v);
    }

}
