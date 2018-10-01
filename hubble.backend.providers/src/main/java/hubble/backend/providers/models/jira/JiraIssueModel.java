package hubble.backend.providers.models.jira;

public class JiraIssueModel {
	private String expand;
	private int id;
	private String self;
	private String key;
	private JiraIssueFieldsModel fields;
	private String providerName;
	
	public String getExpand() {
		return expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSelf() {
		return self;
	}
	public void setSelf(String self) {
		this.self = self;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public JiraIssueFieldsModel getFields() {
		return fields;
	}
	public void setFields(JiraIssueFieldsModel fields) {
		this.fields = fields;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
}
