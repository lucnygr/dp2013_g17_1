//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.04 um 03:51:32 PM CEST 
//


package at.ac.tuwien.digitalpreservation.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter2
    extends XmlAdapter<String, MouseButtonEnum>
{


    public MouseButtonEnum unmarshal(String value) {
        return (at.ac.tuwien.digitalpreservation.config.MouseButtonEnum.parseMouseButton(value));
    }

    public String marshal(MouseButtonEnum value) {
        return (at.ac.tuwien.digitalpreservation.config.MouseButtonEnum.printMouseButton(value));
    }

}
