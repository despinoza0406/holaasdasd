package hubble.backend.storage.models;

import hubble.backend.core.enums.Results;

import java.util.Date;

public class TaskRunnerExecution {
    private String id;
    private String provider;
    private String applicationId;
    private Results.RESULTS result;
    private String description;
    private Date timestamp;

    public TaskRunnerExecution(String provider,String applicationId, Results.RESULTS result, String description, Date timestamp){
        this.provider = provider;
        this.applicationId = applicationId;
        this.result = result;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Results.RESULTS getResult() {
        return result;
    }

    public void setResult(Results.RESULTS result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
