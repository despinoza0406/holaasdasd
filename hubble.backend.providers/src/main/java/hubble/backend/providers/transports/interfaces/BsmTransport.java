package hubble.backend.providers.transports.interfaces;

import hubble.backend.core.enums.Results;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;

public interface BsmTransport extends Transport<SOAPBody> {

    public String getQuery();

    public void setQuery(String query);

    public Results.RESULTS getResult();

    public String getError();

    public SOAPMessage getMessage();

    public SOAPMessage createMessage(String query);

    public SOAPBody getApplications();

    public SOAPBody call();
}
