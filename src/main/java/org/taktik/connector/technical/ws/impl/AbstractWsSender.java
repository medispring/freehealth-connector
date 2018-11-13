package org.taktik.connector.technical.ws.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.taktik.connector.technical.exception.RetryNextEndpointException;
import org.taktik.connector.technical.exception.TechnicalConnectorException;
import org.taktik.connector.technical.exception.TechnicalConnectorExceptionValues;
import org.taktik.connector.technical.utils.ConfigurableFactoryHelper;
import org.taktik.connector.technical.utils.ConnectorIOUtils;
import org.taktik.connector.technical.ws.domain.GenericRequest;
import org.taktik.connector.technical.ws.domain.GenericResponse;
import org.taktik.connector.technical.ws.impl.strategy.InvokeStrategy;
import org.taktik.connector.technical.ws.impl.strategy.NoRetryInvokeStrategy;
import be.fgov.ehealth.technicalconnector.bootstrap.bcp.EndpointDistributor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.activation.DataHandler;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.commons.lang.exception.ExceptionUtils;

public abstract class AbstractWsSender {
   public static final String MESSAGECONTEXT_ENDPOINT_ADDRESS = "javax.xml.ws.service.endpoint.address";
   public static final String PROP_RETRY_STRATEGY = "org.taktik.connector.technical.ws.genericsender.invokestrategy";
   private static final Log log = LogFactory.getLog(AbstractWsSender.class);
   private static MessageFactory mf;
   private static SOAPConnectionFactory scf;
   private static ConfigurableFactoryHelper<InvokeStrategy> invokeStrategyHelper = new ConfigurableFactoryHelper("org.taktik.connector.technical.ws.genericsender.invokestrategy", NoRetryInvokeStrategy.class.getName());

   public GenericResponse send(GenericRequest genericRequest) throws TechnicalConnectorException {
      InvokeStrategy strategy = (InvokeStrategy)invokeStrategyHelper.getImplementation();
      log.debug("Using invoke strategy [" + strategy.getClass() + "]");
      return strategy.invoke(genericRequest);
   }

   protected GenericResponse call(GenericRequest genericRequest) throws TechnicalConnectorException {
      SOAPMessageContext request = this.createSOAPMessageCtx(genericRequest);
      request.putAll(genericRequest.getRequestMap());
      request.put("javax.xml.ws.handler.message.outbound", true);
      Handler<?>[] chain = genericRequest.getHandlerchain();
      SOAPConnection conn = null;
      SOAPMessageContext reply;

      try {
         URL endpoint = generateEndpoint(request);
         executeHandlers(chain, request);
         SOAPMessage msgToSend = request.getMessage();

         if (log.isDebugEnabled()) {
            log.debug("Sending SOAP message: " + msgToSend);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
               msgToSend.writeTo(bos);
               log.debug("SOAP message details " + new String(bos.toByteArray(), StandardCharsets.UTF_8));
            } catch (IOException e) {
               log.debug("Error while logging message", e);
            }
         }
         conn = scf.createConnection();
         reply = createSOAPMessageCtx(conn.call(msgToSend, endpoint));
      } catch (UnsupportedOperationException | SOAPException | MalformedURLException ex) {
         throw translate(ex);
      } finally {
         ConnectorIOUtils.closeQuietly(conn);
      }

      reply.put("javax.xml.ws.handler.message.outbound", false);
      executeHandlers(chain, reply);

      if (log.isDebugEnabled()) {
         log.debug("Received SOAP message: " + reply.getMessage());
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         try {
            reply.getMessage().writeTo(bos);
            log.debug("SOAP message details " + new String(bos.toByteArray(), StandardCharsets.UTF_8));
         } catch (IOException | SOAPException e) {
            log.debug("Error while logging message", e);
         }
      }

      return new GenericResponse(reply.getMessage(), request.getMessage());
   }

   private static SOAPMessageContext createSOAPMessageCtx(SOAPMessage msg) {
      return new SOAPMessageContextImpl(msg);
   }

   private static TechnicalConnectorException translate(Exception e) {
      if (e instanceof SOAPException) {
         return new RetryNextEndpointException(e);
      } else {
         Throwable reason = ExceptionUtils.getRootCause(e);
         log.error("Cannot send SOAP message. Reason [" + reason + "]", e);
         return new TechnicalConnectorException(TechnicalConnectorExceptionValues.ERROR_WS, e, new Object[]{reason});
      }
   }

   private static void executeHandlers(Handler[] handlers, SOAPMessageContext ctx) throws TechnicalConnectorException {
      Handler[] arr$ = handlers;
      int len$ = handlers.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         Handler handler = arr$[i$];
         if (!handler.handleMessage(ctx)) {
            TechnicalConnectorExceptionValues errorValue = TechnicalConnectorExceptionValues.ERROR_WS;
            log.error(MessageFormat.format(errorValue.getMessage(), "Error while executing handler " + handler.getClass()));
            throw new TechnicalConnectorException(errorValue, new Object[]{"Error while executing handler."});
         }
      }

   }

   protected String getCurrentEndpoint(GenericRequest genericRequest) {
      return (String)genericRequest.getRequestMap().get("javax.xml.ws.service.endpoint.address");
   }

   private static URL generateEndpoint(final SOAPMessageContext request) throws MalformedURLException {
      String requestedTarget = (String)request.get("javax.xml.ws.service.endpoint.address");
      String target = EndpointDistributor.getInstance().getActiveEndpoint(requestedTarget);
      request.put("javax.xml.ws.service.endpoint.address", target);
      URL targetURL = new URL(target);
      StringBuffer context = new StringBuffer();
      context.append(targetURL.getProtocol());
      context.append("://");
      context.append(targetURL.getHost());
      if (targetURL.getPort() != -1) {
         context.append(":");
         context.append(targetURL.getPort());
      }

      URL endpoint = new URL(new URL(context.toString()), targetURL.getFile(), new URLStreamHandler() {
         protected URLConnection openConnection(URL url) throws IOException {
            URL target = new URL(url.toString());
            URLConnection connection = target.openConnection();
            connection.setConnectTimeout(Integer.parseInt((String)request.get("connector.soaphandler.connection.connection.timeout")));
            connection.setReadTimeout(Integer.parseInt((String)request.get("connector.soaphandler.connection.request.timeout")));
            return connection;
         }
      });
      return endpoint;
   }

   protected SOAPMessageContext createSOAPMessageCtx(GenericRequest genericRequest) throws TechnicalConnectorException {
      try {
         SOAPMessage soapMessage = mf.createMessage();
         SOAPPart soapPart = soapMessage.getSOAPPart();
         if (genericRequest.isXopEnabled()) {
            soapMessage.getMimeHeaders().addHeader("Content-Type", "application/xop+xml");
            soapPart.addMimeHeader("Content-ID", "<root.message@ehealth.fgov.be>");
            soapPart.addMimeHeader("Content-Transfer-Encoding", "8bit");
         }

         SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
         SOAPBody soapBody = soapEnvelope.getBody();
         soapBody.addDocument(genericRequest.getPayload());
         Map<String, DataHandler> handlers = genericRequest.getDataHandlerMap();

         AttachmentPart part;
         for(Iterator i$ = handlers.entrySet().iterator(); i$.hasNext(); soapMessage.addAttachmentPart(part)) {
            Entry<String, DataHandler> handlerEntry = (Entry)i$.next();
            DataHandler handler = (DataHandler)handlerEntry.getValue();
            part = soapMessage.createAttachmentPart(handler);
            part.setContentType(handler.getContentType());
            if (genericRequest.isXopEnabled()) {
               part.addMimeHeader("Content-Transfer-Encoding", "binary");
               part.setContentId("<" + (String)handlerEntry.getKey() + ">");
            } else {
               part.setContentId((String)handlerEntry.getKey());
            }
         }

         return createSOAPMessageCtx(soapMessage);
      } catch (SOAPException var11) {
         throw translate(var11);
      }
   }

   static {
      Object is = null;

      try {
         mf = MessageFactory.newInstance();
         scf = SOAPConnectionFactory.newInstance();
      } catch (UnsupportedOperationException var6) {
         throw new IllegalArgumentException(var6);
      } catch (SOAPException var7) {
         throw new IllegalArgumentException(var7);
      } finally {
         ConnectorIOUtils.closeQuietly(is);
      }

   }
}
