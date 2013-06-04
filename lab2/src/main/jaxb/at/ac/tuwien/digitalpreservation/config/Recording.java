//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.04 um 10:14:39 AM CEST 
//


package at.ac.tuwien.digitalpreservation.config;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für recording complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="recording">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="keyboardEvent" type="{}keyboardEvent"/>
 *         &lt;element name="mouseEvent" type="{}mouseEvent"/>
 *       &lt;/sequence>
 *       &lt;attribute name="takeScreenshotBeforeEvent" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="takeScreenshotAfterEvent" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recording", propOrder = {
    "description",
    "keyboardEvent",
    "mouseEvent"
})
public class Recording
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected KeyboardEvent keyboardEvent;
    @XmlElement(required = true)
    protected MouseEvent mouseEvent;
    @XmlAttribute(name = "takeScreenshotBeforeEvent")
    protected Boolean takeScreenshotBeforeEvent;
    @XmlAttribute(name = "takeScreenshotAfterEvent")
    protected Boolean takeScreenshotAfterEvent;

    /**
     * Ruft den Wert der description-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Legt den Wert der description-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Ruft den Wert der keyboardEvent-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link KeyboardEvent }
     *     
     */
    public KeyboardEvent getKeyboardEvent() {
        return keyboardEvent;
    }

    /**
     * Legt den Wert der keyboardEvent-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyboardEvent }
     *     
     */
    public void setKeyboardEvent(KeyboardEvent value) {
        this.keyboardEvent = value;
    }

    /**
     * Ruft den Wert der mouseEvent-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link MouseEvent }
     *     
     */
    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    /**
     * Legt den Wert der mouseEvent-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link MouseEvent }
     *     
     */
    public void setMouseEvent(MouseEvent value) {
        this.mouseEvent = value;
    }

    /**
     * Ruft den Wert der takeScreenshotBeforeEvent-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTakeScreenshotBeforeEvent() {
        return takeScreenshotBeforeEvent;
    }

    /**
     * Legt den Wert der takeScreenshotBeforeEvent-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTakeScreenshotBeforeEvent(Boolean value) {
        this.takeScreenshotBeforeEvent = value;
    }

    /**
     * Ruft den Wert der takeScreenshotAfterEvent-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTakeScreenshotAfterEvent() {
        return takeScreenshotAfterEvent;
    }

    /**
     * Legt den Wert der takeScreenshotAfterEvent-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTakeScreenshotAfterEvent(Boolean value) {
        this.takeScreenshotAfterEvent = value;
    }

}
