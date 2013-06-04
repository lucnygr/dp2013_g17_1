//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �nderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.04 um 11:11:14 AM CEST 
//


package at.ac.tuwien.digitalpreservation.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse f�r keyboardEvent complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="keyboardEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="timeOffset" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="scancodes" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "keyboardEvent", propOrder = {
    "timeOffset",
    "scancodes"
})
public class KeyboardEvent
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected long timeOffset;
    @XmlElement(type = Integer.class)
    protected List<Integer> scancodes;

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

    /**
     * Gets the value of the scancodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the scancodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getScancodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getScancodes() {
        if (scancodes == null) {
            scancodes = new ArrayList<Integer>();
        }
        return this.scancodes;
    }

}