package hubble.backend.api.models;

public class NoSuchKPIException extends Exception{

    public NoSuchKPIException(){}

    public NoSuchKPIException(String message){
        super(message);
    }
}
