package hubble.backend.providers.transports.implementations.bsm;

import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.providers.configurations.environments.BsmProviderEnvironment;
import hubble.backend.providers.transports.interfaces.BsmTransport;
import java.util.Calendar;
import java.util.Date;
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

import hubble.backend.storage.models.Frecuency;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component()
public class BsmTransportImpl implements BsmTransport {

    @Autowired
    private BsmProviderEnvironment bsmProviderEnvironment;
    @Autowired
    private ProvidersRepository providersRepository;

    SOAPMessage message = null;
    String query = EMPTY;
    private final Logger logger = LoggerFactory.getLogger(BsmTransportImpl.class);
    private String error = "";
    Results.RESULTS  result = Results.RESULTS.SUCCESS;

    public BsmTransportImpl() {
    }

    @Override
    public SOAPBody getData() {

        StringBuilder queryBuilder = new StringBuilder();

        Date from = this.getStartDate();
        Date to = DateHelper.getDateNow();


        long since = from.getTime() / 1000;
        long now = to.getTime() / 1000;

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
            String myNamespaceURI = bsmProviderEnvironment.getSoapEndpointUrl();

            // SOAP Envelope
            SOAPEnvelope envelope = soapPart.getEnvelope();

            envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            SOAPBody soapBody = envelope.getBody();
            SOAPElement soapBodyElem = soapBody.addChildElement("getDataWebService", myNamespace);
            SOAPElement soapBodyUser = soapBodyElem.addChildElement("user", myNamespace);
            SOAPElement soapBodyPassword = soapBodyElem.addChildElement("password", myNamespace);
            SOAPElement soapBodyQuery = soapBodyElem.addChildElement("query", myNamespace);
            soapBodyUser.addTextNode(bsmProviderEnvironment.getUserName());
            soapBodyPassword.addTextNode(bsmProviderEnvironment.getPassword());
            soapBodyQuery.addTextNode(this.query);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", bsmProviderEnvironment.getSoapAction());

            soapMessage.saveChanges();

            this.message = soapMessage;
        } catch (SOAPException ex) {
            error = ex.getMessage();
            result = Results.RESULTS.FAILURE;
            logger.error(ex.getMessage());
        }

        return this.message;
    }

    public SOAPMessage createMessage(String query, String soapEndpointUri, String username, String password, String soapAction) {

        this.query = query;
        try {

            //Creaate Request
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();

            //CREATE SOAPENVELOPE
            SOAPPart soapPart = soapMessage.getSOAPPart();

            String myNamespace = "gdew";
            String myNamespaceURI = soapEndpointUri;

            // SOAP Envelope
            SOAPEnvelope envelope = soapPart.getEnvelope();

            envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            SOAPBody soapBody = envelope.getBody();
            SOAPElement soapBodyElem = soapBody.addChildElement("getDataWebService", myNamespace);
            SOAPElement soapBodyUser = soapBodyElem.addChildElement("user", myNamespace);
            SOAPElement soapBodyPassword = soapBodyElem.addChildElement("password", myNamespace);
            SOAPElement soapBodyQuery = soapBodyElem.addChildElement("query", myNamespace);
            soapBodyUser.addTextNode(username);
            soapBodyPassword.addTextNode(password);
            soapBodyQuery.addTextNode(this.query);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", soapAction);

            soapMessage.saveChanges();

            this.message = soapMessage;
        } catch (SOAPException ex) {
            error = ex.getMessage();
            result = Results.RESULTS.FAILURE;
            logger.error(ex.getMessage());
        }

        return this.message;
    }

    public boolean testConnection(String soapEndpointUri, String username, String password, String soapAction){
        StringBuilder queryBuilder = new StringBuilder();
        Date from = DateHelper.getAnHourAgo();
        Date to = DateHelper.getDateNow();


        long since = from.getTime() / 1000;
        long now = to.getTime() / 1000;

        queryBuilder.append(" select profile_name, szTransactionName, szLocationName,szStatusName, iComponentErrorCount, time_stamp,szScriptName, dResponseTime, dGreenThreshold, dRedThreshold");
        queryBuilder.append(" from trans_t  where time_stamp>=").append(Long.toString(since));
        queryBuilder.append(" and time_stamp<=").append(Long.toString(now));

        this.createMessage(queryBuilder.toString(), soapEndpointUri, username, password, soapAction);
        SOAPBody body = call(soapEndpointUri);

        return body != null;
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
        //queryBuilder.append(" where time_stamp>=").append(Long.toString(since));
        //queryBuilder.append(" and time_stamp<=").append(Long.toString(now));

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
            logger.error("The message is null");
            return null;
        }

        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(this.message, bsmProviderEnvironment.getSoapEndpointUrl());

            soapConnection.close();

            if (soapResponse == null) {
                logger.error("There is no response from BSM: {}", bsmProviderEnvironment.getSoapEndpointUrl());
                error = "There is no response from BSM: " + bsmProviderEnvironment.getSoapEndpointUrl();
                result = Results.RESULTS.FAILURE;
                return null;
            }

            return soapResponse.getSOAPBody();
        } catch (SOAPException ex) {
            logger.debug(ex.toString());
            error ="Error de conexion";
            result = Results.RESULTS.FAILURE;
        }

        return null;
    }

    public SOAPBody call(String soapEndpointUri) {

        if (this.message == null) {
            logger.error("The message is null");
            return null;
        }

        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(this.message, soapEndpointUri);

            soapConnection.close();

            if (soapResponse == null) {
                logger.error("There is no response from BSM: {}", soapEndpointUri);
                error = "There is no response from BSM: " + soapEndpointUri;
                result = Results.RESULTS.FAILURE;
                return null;
            }

            return soapResponse.getSOAPBody();
        } catch (SOAPException ex) {
            logger.debug(ex.toString());
            error ="Error de conexion";
            result = Results.RESULTS.FAILURE;
        }

        return null;
    }

    private Date getStartDate(){
        Frecuency frecuency = providersRepository.bsm().getTaskRunner().getSchedule().getFrecuency();
        switch (frecuency){
            case DAYLY:
                return DateHelper.getYesterday();

            case HOURLY:
                return DateHelper.getAnHourAgo();

            case EVERY_30_MINUTES:
                return DateHelper.getNMinutesAgo(30);

            case EVERY_15_MINUTES:
                return DateHelper.getNMinutesAgo(15);

            case EVERY_5_MINUTES:
                return DateHelper.getNMinutesAgo(5);

            default:
                return DateHelper.getAnHourAgo();

        }
    }

    public Results.RESULTS getResult() {
        return result;
    }

    public void setResult(Results.RESULTS result) { this.result = result; }

    public String getError() {
        return error;
    }

    public void setError(String error) { this.error = error; }
}
