package be.apb.standards.smoa.schema.code.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "DiseaseCodeType",
   propOrder = {"value"}
)
public class DiseaseCodeType extends AbstractDiseaseCodeType {
   @XmlElement(
      required = true
   )
   protected String value;

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }
}
