package hubble.backend.providers.models.bsm;

public class BsmProviderModel {

    private String profile_name;
    private String applicationId;
    private String sztransactionname;
    private String szlocationname;
    private String szstatusname;
    private int icomponenterrorcount;
    private long time_stamp;
    private String szscriptname;
    private int dresponsetime;
    private int dgreenthreshold;
    private int dredthreshold;

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public String getSztransactionname() {
        return sztransactionname;
    }

    public void setSztransactionname(String sztransactionname) {
        this.sztransactionname = sztransactionname;
    }

    public String getSzlocationname() {
        return szlocationname;
    }

    public void setSzlocationname(String szlocationname) {
        this.szlocationname = szlocationname;
    }

    public String getSzstatusname() {
        return szstatusname;
    }

    public void setSzstatusname(String szstatusname) {
        this.szstatusname = szstatusname;
    }

    public int getIcomponenterrorcount() {
        return icomponenterrorcount;
    }

    public void setIcomponenterrorcount(int icomponenterrorcount) {
        this.icomponenterrorcount = icomponenterrorcount;
    }

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getSzscriptname() {
        return szscriptname;
    }

    public void setSzscriptname(String szscriptname) {
        this.szscriptname = szscriptname;
    }

    public int getDresponsetime() {
        return dresponsetime;
    }

    public void setDresponsetime(int dresponsetime) {
        this.dresponsetime = dresponsetime;
    }

    public int getDgreenthreshold() {
        return dgreenthreshold;
    }

    public void setDgreenthreshold(int dgreenthreshold) {
        this.dgreenthreshold = dgreenthreshold;
    }

    public int getDredthreshold() {
        return dredthreshold;
    }

    public void setDredthreshold(int dredthreshold) {
        this.dredthreshold = dredthreshold;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
