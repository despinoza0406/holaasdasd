package hubble.backend.providers.tests.configurations.mappers;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.configurations.mappers.jira.JiraMapperConfiguration;
import hubble.backend.providers.models.jira.IssueStatus;
import hubble.backend.providers.models.jira.JiraIssueFieldsModel;
import hubble.backend.providers.models.jira.JiraIssueModel;
import hubble.backend.providers.models.jira.JiraUserModel;
import hubble.backend.providers.models.jira.Priority;
import hubble.backend.providers.models.jira.Project;
import hubble.backend.storage.models.IssueStorage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class JiraMapperConfigurationUnitTest {

   @Autowired
   JiraMapperConfiguration jiraMapper;
    
   @Test
   public void jira_mapper_should_map_issue_to_issue_storage() {
       //Assign
       JiraIssueModel jiraModel = mock(JiraIssueModel.class);
       JiraIssueFieldsModel jiraFieldsModel = mock(JiraIssueFieldsModel.class);
       JiraUserModel jiraUserModelAssignee = mock(JiraUserModel.class);
       JiraUserModel jiraUserModelCreator = mock(JiraUserModel.class);
       Priority jiraPriority = mock(Priority.class);
       IssueStatus jiraStatus = mock(IssueStatus.class);
       Project jiraProject = mock(Project.class);
       String strDate = "2017-11-21T12:52:20.000+0003";
       
       //Act
       when(jiraModel.getFields()).thenReturn(jiraFieldsModel);
       when(jiraModel.getFields().getAssignee()).thenReturn(jiraUserModelAssignee);
       when(jiraModel.getFields().getCreator()).thenReturn(jiraUserModelCreator);
       when(jiraModel.getFields().getPriority()).thenReturn(jiraPriority);
       when(jiraModel.getFields().getStatus()).thenReturn(jiraStatus);
       when(jiraModel.getFields().getProject()).thenReturn(jiraProject);
       when(jiraModel.getFields().getAssignee().getName()).thenReturn("Developer");
       when(jiraModel.getId()).thenReturn(5);
       when(jiraModel.getFields().getDescription()).thenReturn("unit test");
       when(jiraModel.getFields().getCreator().getName()).thenReturn("Tester");
       when(jiraModel.getFields().getPriority().getName()).thenReturn("1");
       when(jiraModel.getFields().getStatus().getName()).thenReturn("Open");
       when(jiraModel.getFields().getSummary()).thenReturn("Major error");
       when(jiraModel.getFields().getResolutiondate()).thenReturn(strDate);
       when(jiraModel.getFields().getCreated()).thenReturn(strDate);
       when(jiraModel.getFields().getUpdated()).thenReturn(strDate);
       when(jiraModel.getFields().getProject().getName()).thenReturn("hubble");
       
       IssueStorage issueStorage = jiraMapper.mapToIssueStorage(jiraModel);
       DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
       Date parsedDate = new Date();     
            try {
                parsedDate = format.parse(strDate);
            } catch(ParseException e) {
                //Log
            }
            
       //Assert
       assertEquals("Developer", issueStorage.getAssignee());
       assertEquals(5, issueStorage.getExternalId());
       assertEquals("unit test", issueStorage.getDescription());
       assertEquals("Tester", issueStorage.getDetectedBy());
       assertEquals("Open", issueStorage.getStatus());
       assertEquals("Major error", issueStorage.getTitle());
       assertEquals(parsedDate, issueStorage.getClosedDate());
       assertEquals(parsedDate, issueStorage.getModifiedDate());
       assertEquals(parsedDate, issueStorage.getRegisteredDate());
       assertEquals("hubble", issueStorage.getBusinessApplication());
   }
}
