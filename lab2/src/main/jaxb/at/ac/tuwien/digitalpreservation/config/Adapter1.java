//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.04 at 04:07:18 PM CEST 
//


package at.ac.tuwien.digitalpreservation.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, EventTypeEnum>
{


    public EventTypeEnum unmarshal(String value) {
        return (at.ac.tuwien.digitalpreservation.config.EventTypeEnum.parseEventType(value));
    }

    public String marshal(EventTypeEnum value) {
        return (at.ac.tuwien.digitalpreservation.config.EventTypeEnum.printEventType(value));
    }

}
