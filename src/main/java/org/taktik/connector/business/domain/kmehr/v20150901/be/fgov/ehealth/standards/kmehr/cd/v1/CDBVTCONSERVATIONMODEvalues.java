//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.11.10 à 11:53:46 AM CET 
//


package org.taktik.connector.business.domain.kmehr.v20150901.be.fgov.ehealth.standards.kmehr.cd.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour CD-BVT-CONSERVATIONMODEvalues.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="CD-BVT-CONSERVATIONMODEvalues">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="minus20degreesc"/>
 *     &lt;enumeration value="minus80degreesc"/>
 *     &lt;enumeration value="minus120degreesorcolder"/>
 *     &lt;enumeration value="liquidnitrogen"/>
 *     &lt;enumeration value="inclusioninparaffin"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CD-BVT-CONSERVATIONMODEvalues")
@XmlEnum
public enum CDBVTCONSERVATIONMODEvalues {

    @XmlEnumValue("minus20degreesc")
    MINUS_20_DEGREESC("minus20degreesc"),
    @XmlEnumValue("minus80degreesc")
    MINUS_80_DEGREESC("minus80degreesc"),
    @XmlEnumValue("minus120degreesorcolder")
    MINUS_120_DEGREESORCOLDER("minus120degreesorcolder"),
    @XmlEnumValue("liquidnitrogen")
    LIQUIDNITROGEN("liquidnitrogen"),
    @XmlEnumValue("inclusioninparaffin")
    INCLUSIONINPARAFFIN("inclusioninparaffin"),
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    CDBVTCONSERVATIONMODEvalues(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CDBVTCONSERVATIONMODEvalues fromValue(String v) {
        for (CDBVTCONSERVATIONMODEvalues c: CDBVTCONSERVATIONMODEvalues.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
