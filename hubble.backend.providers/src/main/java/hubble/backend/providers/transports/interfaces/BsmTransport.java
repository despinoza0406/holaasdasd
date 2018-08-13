package hubble.backend.providers.transports.interfaces;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;

public interface BsmTransport extends Transport<SOAPBody> {

    public String getQuery();

    public void setQuery(String query);

    public String getResult();

    public SOAPMessage getMessage();

    public SOAPMessage createMessage(String query);

    public SOAPBody getApplications();

    public SOAPBody call();
}
