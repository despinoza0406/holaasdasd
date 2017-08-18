package hubble.backend.providers.transports.implementations.bsm;

import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.LoggingOutputStream;
import hubble.backend.providers.transports.interfaces.BsmTransport;
import java.io.IOException;
import java.util.Calendar;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import static org.apache.commons.lang.StringUtils.EMPTY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component()
public class BsmTransportImpl implements BsmTransport {

    SOAPMessage message = null;
    String query = EMPTY;
    String soapEndpointUrl = "http://t-srvbacapplsar.tsoftglobal.com/topaz/gdeopenapi/services/GdeWsOpenAPI?wsdl";
    String soapAction = "http://t-srvbacapplsar.tsoftglobal.com/topaz/gdeopenapi/services/GdeWsOpenAPI";
    String user = "admin";
    String password = "admin";
    Logger logger;
    LoggingOutputStream logging;

    public BsmTransportImpl() {
        logger = LoggerFactory.getLogger(BsmTransportImpl.class);
        logging = new LoggingOutputStream(logger, LoggingOutputStream.LogLevel.INFO);
    }

    @Override
    public SOAPBody getData() {

        StringBuilder queryBuilder = new StringBuilder();

        Calendar from = CalendarHelper.getNow();
        Calendar to = CalendarHelper.getNow();

        from.add(Calendar.HOUR, -1);

        long since = from.getTimeInMillis() / 1000;
        long now = to.getTimeInMillis() / 1000;

        queryBuilder.append(" select profile_name, szTransactionName, szLocationName,szStatusName, iComponentErrorCount, time_stamp,szScriptName, dResponseTime, dGreenThreshold, dRedThreshold");
        queryBuilder.append(" from trans_t  where time_stamp>=").append(Long.toString(since));
        queryBuilder.append(" and time_stamp<=").append(Long.toString(now));

        this.createMessage(queryBuilder.toString());
        return call();
    }

    @Override
    public SOAPMessage createMessage(String query) {

        this.query = query;
        try {

            //Creaate Request
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();

            //CREATE SOAPENVELOPE
            SOAPPart soapPart = soapMessage.getSOAPPart();

            String myNamespace = "gdew";
            String myNamespaceURI = soapEndpointUrl;

            // SOAP Envelope
            SOAPEnvelope envelope = soapPart.getEnvelope();

            envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            SOAPBody soapBody = envelope.getBody();
            SOAPElement soapBodyElem = soapBody.addChildElement("getDataWebService", myNamespace);
            SOAPElement soapBodyUser = soapBodyElem.addChildElement("user", myNamespace);
            SOAPElement soapBodyPassword = soapBodyElem.addChildElement("password", myNamespace);
            SOAPElement soapBodyQuery = soapBodyElem.addChildElement("query", myNamespace);
            soapBodyUser.addTextNode(this.user);
            soapBodyPassword.addTextNode(this.password);
            soapBodyQuery.addTextNode(this.query);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", soapAction);

            soapMessage.saveChanges();

            logger.info("Request SOAP Message:");
            soapMessage.writeTo(logging);

            this.message = soapMessage;
        } catch (IOException | SOAPException ex) {
            logger.error(ex.toString());
        }

        return this.message;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public SOAPBody getApplications() {
        //Assign
        Calendar from = CalendarHelper.getNow();
        Calendar to = CalendarHelper.getNow();

        from.add(Calendar.HOUR, -1);

        long since = from.getTimeInMillis() / 1000;
        long now = to.getTimeInMillis() / 1000;

        StringBuilder queryBuilder = new StringBuilder("SELECT distinct(profile_name), dGreenThreshold, dRedThreshold from trans_t");
        queryBuilder.append(" where time_stamp>=").append(Long.toString(since));
        queryBuilder.append(" and time_stamp<=").append(Long.toString(now));

        //Act
        createMessage(queryBuilder.toString());
        return call();
    }

    @Override
    public SOAPMessage getMessage() {
        return this.message;
    }

    @Override
    public SOAPBody call() {

        if (this.message == null) {
            return null;
        }

        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(this.message, soapEndpointUrl);

            // Print the SOAP Response
            logger.debug("Response SOAP Message:");
            soapResponse.writeTo(logging);

            soapConnection.close();

            return soapResponse.getSOAPBody();
        } catch (SOAPException | IOException ex) {
            logger.debug(ex.toString());
        }

        return null;
    }

}
