package be.fgov.ehealth.recipe.protocol.v4;

import be.fgov.ehealth.recipe.core.v4.SecuredContentType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "ListFeedbacksRequestType",
   propOrder = {"securedListFeedbacksRequest"}
)
@XmlRootElement(
   name = "ListFeedbacksRequest"
)
public class ListFeedbacksRequest extends RequestType {
   @XmlElement(
      name = "SecuredListFeedbacksRequest",
      required = true
   )
   protected SecuredContentType securedListFeedbacksRequest;

   public SecuredContentType getSecuredListFeedbacksRequest() {
      return this.securedListFeedbacksRequest;
   }

   public void setSecuredListFeedbacksRequest(SecuredContentType value) {
      this.securedListFeedbacksRequest = value;
   }
}
