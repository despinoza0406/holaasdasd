package hubble.backend.storage.models;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Estas son las transacciones de BSM.
 */
@Document
public class TransactionStorage {

    private String id;
    private String transactionId;
    private String transactionName;
    private String transactionType;
    private String scriptName;
    private int okThreshold;
    private int criticalThreshold;
    private boolean assigned;

    public TransactionStorage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

     public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public int getOkThreshold() {
        return okThreshold;
    }

    public void setOkThreshold(int okThreshold) {
        this.okThreshold = okThreshold;
    }

    public int getCriticalThreshold() {
        return criticalThreshold;
    }

    public void setCriticalThreshold(int criticalThreshold) {
        this.criticalThreshold = criticalThreshold;
    }
}
