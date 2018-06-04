package hubble.backend.api.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Error {

    public Integer status;
    public String error;
    public String message;
    public String timeStamp;

    public Error() {

    }

    public Error(Integer status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

}
