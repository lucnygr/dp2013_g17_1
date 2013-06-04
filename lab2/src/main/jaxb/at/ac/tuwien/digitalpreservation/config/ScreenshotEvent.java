//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.04 um 11:11:14 AM CEST 
//


package at.ac.tuwien.digitalpreservation.config;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für screenshotEvent complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="screenshotEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="timeOffset" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "screenshotEvent", propOrder = {
    "timeOffset"
})
public class ScreenshotEvent
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected long timeOffset;

    /**
     * Ruft den Wert der timeOffset-Eigenschaft ab.
     * 
     */
    public long getTimeOffset() {
        return timeOffset;
    }

    /**
     * Legt den Wert der timeOffset-Eigenschaft fest.
     * 
     */
    public void setTimeOffset(long value) {
        this.timeOffset = value;
    }

}
