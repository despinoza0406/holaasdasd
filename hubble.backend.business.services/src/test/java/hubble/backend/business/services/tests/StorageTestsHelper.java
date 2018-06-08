package hubble.backend.business.services.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hubble.backend.storage.models.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Assert;

public class StorageTestsHelper {

    public AvailabilityStorage getFakeAvailabilityStorage() {

        //Error
        ErrorStorage error = new ErrorStorage();
        ArrayList<ErrorStorage> errors = new ArrayList<>();
        error.setErrorMessage("fake-error-message");
        error.setFileName("fake-file-name");
        error.setLineNumber(1);
        errors.add(error);

        //Data
        Date activityTime = new Date();
        AvailabilityStorage availability = new AvailabilityStorage();
        availability.setApplicationId("1");
        availability.setApplicationName("fake-name");
        availability.setAvailabilityFailIfAbove(1);
        availability.setAvailabilityStatus("fake-status");
        availability.setErrors(errors);
        availability.setLocationId("1");
        availability.setLocationName("fake-location");
        availability.setNumberOfErrors(1);
        availability.setPerformanceStatus("fake-performance-status");
        availability.setResponseTime(1);
        availability.setScriptName("fake-script-name");
        availability.setServerName("fake-server-name");
        availability.setTimeStamp(activityTime);

        return availability;
    }

    public IssueStorage getFakeIssueStorage() {

        Date issueDate = new Date();
        IssueStorage issue = new IssueStorage();
        issue.setExternalId(1);
        issue.setBusinessApplication("Home Banking");
        issue.setBusinessApplicationId("Benchmark Home Banking");
        issue.setClosedDate(issueDate);
        issue.setCorrectedOnRelease("R1");
        issue.setDescription("fake description");
        issue.setDetectedBy("fake user 1");
        issue.setDetectedOnRelease("R1");
        issue.setId("fakeid123");
        issue.setModifiedDate(issueDate);
        issue.setPriority(1);
        issue.setProviderName("Fake Alm");
        issue.setProviderOrigin("Alm");
        issue.setRegisteredDate(issueDate);
        issue.setReproducible(true);
        issue.setSeverity(1);
        issue.setStatus("Open");
        issue.setTitle("fake title");

        return issue;
    }

    public WorkItemStorage getFakeWorkItemStorage() {

        Date workItemDate = new Date();
        WorkItemStorage workItem = new WorkItemStorage();
        workItem.setExternalId(1);
        workItem.setBusinessApplication("Home Banking");
        workItem.setBusinessApplicationId("Benchmark Home Banking");
        workItem.setDescription("fake description");
        workItem.setId("fakeid123");
        workItem.setModifiedDate(workItemDate);
        workItem.setPriority(1);
        workItem.setProviderName("Fake Alm");
        workItem.setProviderOrigin("Alm");
        workItem.setRegisteredDate(workItemDate);
        workItem.setSeverity(1);
        workItem.setStatus("Open");
        workItem.setTitle("fake title");
        workItem.setAssignee("fake-user");

        return workItem;
    }

    public EventStorage getGoodFakeEventStorage(int i){

        Date eventDate = new Date();
        EventStorage event = new EventStorage();

        event.setType("Group");
        event.setName("Andre");
        event.setUpdatedDate(eventDate);
        event.setBusinessApplication("Home Banking");
        event.setBusinessApplicationId("Benchmark Home Banking");
        event.setDescription("Andre <3");
        event.setStatus("Good");
        event.setProviderName("Fake SiteScope");
        event.setProviderOrigin("SiteScope");
        event.setId(Integer.toString(i));

        return event;
    }

    public EventStorage getWarningFakeEventStorage(int i){

        Date eventDate = new Date();
        EventStorage event = new EventStorage();

        event.setType("Group");
        event.setName("Andre");
        event.setUpdatedDate(eventDate);
        event.setBusinessApplication("Home Banking");
        event.setBusinessApplicationId("Benchmark Home Banking");
        event.setDescription("Andre <3");
        event.setStatus("Warning");
        event.setProviderName("Fake SiteScope");
        event.setProviderOrigin("SiteScope");
        event.setId(Integer.toString(i));

        return event;
    }

    public EventStorage getErrorFakeEventStorage(int i){

        Date eventDate = new Date();
        EventStorage event = new EventStorage();

        event.setType("Group");
        event.setName("Andre");
        event.setUpdatedDate(eventDate);
        event.setBusinessApplication("Home Banking");
        event.setBusinessApplicationId("Benchmark Home Banking");
        event.setDescription("Andre <3");
        event.setStatus("Error");
        event.setProviderName("Fake SiteScope");
        event.setProviderOrigin("SiteScope");
        event.setId(Integer.toString(i));

        return event;
    }

    public List<AvailabilityStorage> getFakeListOfAvailabilityStorage() {

        String fakeFileName = "/availability/fake-data-list.json";
        InputStream appPulseDataRaw = getClass().getResourceAsStream(fakeFileName);

        ObjectMapper objMapper = new ObjectMapper();
        List<AvailabilityStorage> availabilities = null;
        try {
            availabilities = objMapper.readValue(appPulseDataRaw, new TypeReference<List<AvailabilityStorage>>() {
            });
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }

        return availabilities;
    }

    public List<IssueStorage> getFakeListOfIssueStorage() {

        String fakeFileName = "/issue/fake-data-list.json";
        InputStream fakedata = getClass().getResourceAsStream(fakeFileName);

        ObjectMapper objMapper = new ObjectMapper();
        List<IssueStorage> issues = null;
        try {
            issues = objMapper.readValue(fakedata, new TypeReference<List<IssueStorage>>() {
            });
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }

        return issues;
    }

    public List<WorkItemStorage> getFakeListOfWorkItemStorage() {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);

        String fakeFileName = "/workItem/fake-data-list.json";
        InputStream fakedata = getClass().getResourceAsStream(fakeFileName);

        ObjectMapper objMapper = new ObjectMapper();
        List<WorkItemStorage> workItems = null;
        try {
            workItems = objMapper.readValue(fakedata, new TypeReference<List<WorkItemStorage>>() {
            });
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }

        return workItems;
    }

    public List<WorkItemStorage> getFakeListOfWorkItemStorageWeek() {

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);

        String fakeFileName = "/workItem/fake-data-list-week.json";
        InputStream fakedata = getClass().getResourceAsStream(fakeFileName);

        ObjectMapper objMapper = new ObjectMapper();
        List<WorkItemStorage> workItems = null;
        try {
            workItems = objMapper.readValue(fakedata, new TypeReference<List<WorkItemStorage>>() {
            });
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }

        return workItems;
    }
}
