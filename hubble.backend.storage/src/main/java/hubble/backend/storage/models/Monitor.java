package hubble.backend.storage.models;

/**
 * Monitor de un grupo en SiteScope.
 */
public class Monitor {
    private String name;
    private String summary;

    public Monitor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
