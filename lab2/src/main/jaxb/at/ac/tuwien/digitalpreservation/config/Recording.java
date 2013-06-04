//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.04 at 06:19:55 PM CEST 
//


package at.ac.tuwien.digitalpreservation.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recording complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recording">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="keyboardEvent" type="{}keyboardEvent"/>
 *           &lt;element name="mouseEvent" type="{}mouseEvent"/>
 *           &lt;element name="screenshotEvent" type="{}screenshotEvent"/>
 *         &lt;/choice>
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
    "keyboardEventOrMouseEventOrScreenshotEvent"
})
public class Recording
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String description;
    @XmlElements({
        @XmlElement(name = "keyboardEvent", type = KeyboardEvent.class),
        @XmlElement(name = "mouseEvent", type = MouseEvent.class),
        @XmlElement(name = "screenshotEvent", type = ScreenshotEvent.class)
    })
    protected List<AbstractEvent> keyboardEventOrMouseEventOrScreenshotEvent;
    @XmlAttribute(name = "takeScreenshotBeforeEvent")
    protected Boolean takeScreenshotBeforeEvent;
    @XmlAttribute(name = "takeScreenshotAfterEvent")
    protected Boolean takeScreenshotAfterEvent;

    /**
     * Gets the value of the description property.
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
     * Sets the value of the description property.
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
     * Gets the value of the keyboardEventOrMouseEventOrScreenshotEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyboardEventOrMouseEventOrScreenshotEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyboardEventOrMouseEventOrScreenshotEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyboardEvent }
     * {@link MouseEvent }
     * {@link ScreenshotEvent }
     * 
     * 
     */
    public List<AbstractEvent> getKeyboardEventOrMouseEventOrScreenshotEvent() {
        if (keyboardEventOrMouseEventOrScreenshotEvent == null) {
            keyboardEventOrMouseEventOrScreenshotEvent = new ArrayList<AbstractEvent>();
        }
        return this.keyboardEventOrMouseEventOrScreenshotEvent;
    }

    /**
     * Gets the value of the takeScreenshotBeforeEvent property.
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
     * Sets the value of the takeScreenshotBeforeEvent property.
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
     * Gets the value of the takeScreenshotAfterEvent property.
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
     * Sets the value of the takeScreenshotAfterEvent property.
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
