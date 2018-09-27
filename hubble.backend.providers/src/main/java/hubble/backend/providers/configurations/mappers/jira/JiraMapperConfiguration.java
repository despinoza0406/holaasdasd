package hubble.backend.providers.configurations.mappers.jira;

import hubble.backend.providers.configurations.JiraConfiguration;
import hubble.backend.providers.models.jira.JiraApplicationProviderModel;
import hubble.backend.providers.models.jira.JiraIssueModel;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.IssueStorage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
public class JiraMapperConfiguration {
    
    @Autowired
    private JiraConfiguration jiraConfig;
    private ModelMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(JiraMapperConfiguration.class);

    public JiraMapperConfiguration() {
            this.mapper = new ModelMapper();
            this.mapper.addMappings(new IssuePropertyMap());
    }

    public ModelMapper getMapper() {
        return this.mapper;
    }

    public IssueStorage mapToIssueStorage(JiraIssueModel jiraModel) {
        if (jiraModel == null)
            return null;
        jiraModel.setProviderName(jiraConfig.getProviderName());
        IssueStorage issueStorage = mapper.map(jiraModel, IssueStorage.class);
        issueStorage.setBusinessApplicationId(resolveApplicationId(issueStorage.getBusinessApplication()));
        return issueStorage;
    }

    public ApplicationStorage mapToApplicationStorage(JiraApplicationProviderModel jiraModel) {
        if (jiraModel == null)
            return null;

        return mapper.map(jiraModel, ApplicationStorage.class);
    }

    public JiraApplicationProviderModel mapToApplicationModel(JSONObject data) {
        JiraApplicationProviderModel jiraModel = new JiraApplicationProviderModel();
        jiraModel.setName(getValue(data, jiraConfig.getApplicationFieldName()));
        jiraModel.setApplicationId(resolveApplicationId(jiraModel.getName()));
        
        return jiraModel;
    }

    public String getValue(JSONObject data, String fieldName) {  
        if (data.has("issues")) {
            JSONArray issues = data.getJSONArray("issues");
            JSONObject issue = issues.getJSONObject(0);
            JSONObject fields = issue.getJSONObject("fields");
            
            if (fields != null && fields.has(fieldName)) {
                JSONObject project = fields.getJSONObject(fieldName);
                return project.has("name") ? project.get("name").toString() : null;
            }
        }
        return null;
    }

    public String resolveApplicationId(String applicationName) {
        HashMap<String,String> applicationsIdMap = jiraConfig.getValuesToIdMap();
        Set<String> keySet = applicationsIdMap.keySet();
        for (String key : keySet) {
            if (applicationName.equals(applicationsIdMap.get(key))) {
                return key;
            }
        }
        logger.error("Jira: could not found application id in values passed over configuration file");
        return null;
    } 
}

