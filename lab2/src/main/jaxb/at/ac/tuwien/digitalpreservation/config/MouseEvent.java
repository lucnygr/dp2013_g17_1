//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import at.ac.tuwien.digitalpreservation.MouseButtonEnum;


/**
 * <p>Java-Klasse für mouseEvent complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="mouseEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="timeOffset" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="xPosition" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="yPosition" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="zDelta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="wDelta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mouseButtons" type="{}mouseButton" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mouseEvent", propOrder = {
    "timeOffset",
    "xPosition",
    "yPosition",
    "zDelta",
    "wDelta",
    "mouseButtons"
})
public class MouseEvent
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected long timeOffset;
    protected int xPosition;
    protected int yPosition;
    protected int zDelta;
    protected int wDelta;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    protected List<MouseButtonEnum> mouseButtons;

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
     * Ruft den Wert der xPosition-Eigenschaft ab.
     * 
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Legt den Wert der xPosition-Eigenschaft fest.
     * 
     */
    public void setXPosition(int value) {
        this.xPosition = value;
    }

    /**
     * Ruft den Wert der yPosition-Eigenschaft ab.
     * 
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * Legt den Wert der yPosition-Eigenschaft fest.
     * 
     */
    public void setYPosition(int value) {
        this.yPosition = value;
    }

    /**
     * Ruft den Wert der zDelta-Eigenschaft ab.
     * 
     */
    public int getZDelta() {
        return zDelta;
    }

    /**
     * Legt den Wert der zDelta-Eigenschaft fest.
     * 
     */
    public void setZDelta(int value) {
        this.zDelta = value;
    }

    /**
     * Ruft den Wert der wDelta-Eigenschaft ab.
     * 
     */
    public int getWDelta() {
        return wDelta;
    }

    /**
     * Legt den Wert der wDelta-Eigenschaft fest.
     * 
     */
    public void setWDelta(int value) {
        this.wDelta = value;
    }

    /**
     * Gets the value of the mouseButtons property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mouseButtons property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMouseButtons().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<MouseButtonEnum> getMouseButtons() {
        if (mouseButtons == null) {
            mouseButtons = new ArrayList<MouseButtonEnum>();
        }
        return this.mouseButtons;
    }

}
