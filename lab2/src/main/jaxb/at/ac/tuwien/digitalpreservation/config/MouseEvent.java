//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.06.04 at 04:07:18 PM CEST 
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


/**
 * <p>Java class for mouseEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mouseEvent">
 *   &lt;complexContent>
 *     &lt;extension base="{}abstractEvent">
 *       &lt;sequence>
 *         &lt;element name="xPosition" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="yPosition" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="zDelta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="wDelta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mouseButtons" type="{}mouseButton" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mouseEvent", propOrder = {
    "xPosition",
    "yPosition",
    "zDelta",
    "wDelta",
    "mouseButtons"
})
public class MouseEvent
    extends AbstractEvent
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected int xPosition;
    protected int yPosition;
    protected int zDelta;
    protected int wDelta;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter2 .class)
    protected List<MouseButtonEnum> mouseButtons;

    /**
     * Gets the value of the xPosition property.
     * 
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Sets the value of the xPosition property.
     * 
     */
    public void setXPosition(int value) {
        this.xPosition = value;
    }

    /**
     * Gets the value of the yPosition property.
     * 
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * Sets the value of the yPosition property.
     * 
     */
    public void setYPosition(int value) {
        this.yPosition = value;
    }

    /**
     * Gets the value of the zDelta property.
     * 
     */
    public int getZDelta() {
        return zDelta;
    }

    /**
     * Sets the value of the zDelta property.
     * 
     */
    public void setZDelta(int value) {
        this.zDelta = value;
    }

    /**
     * Gets the value of the wDelta property.
     * 
     */
    public int getWDelta() {
        return wDelta;
    }

    /**
     * Sets the value of the wDelta property.
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
