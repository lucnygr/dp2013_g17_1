//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2013.06.04 um 10:14:39 AM CEST 
//


package at.ac.tuwien.digitalpreservation.config;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the at.ac.tuwien.digitalpreservation.config package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GCAP_QNAME = new QName("", "GCAP");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: at.ac.tuwien.digitalpreservation.config
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GCAP }
     * 
     */
    public GCAP createGCAP() {
        return new GCAP();
    }

    /**
     * Create an instance of {@link MouseEvent }
     * 
     */
    public MouseEvent createMouseEvent() {
        return new MouseEvent();
    }

    /**
     * Create an instance of {@link Recording }
     * 
     */
    public Recording createRecording() {
        return new Recording();
    }

    /**
     * Create an instance of {@link KeyboardEvent }
     * 
     */
    public KeyboardEvent createKeyboardEvent() {
        return new KeyboardEvent();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GCAP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "GCAP")
    public JAXBElement<GCAP> createGCAP(GCAP value) {
        return new JAXBElement<GCAP>(_GCAP_QNAME, GCAP.class, null, value);
    }

}
