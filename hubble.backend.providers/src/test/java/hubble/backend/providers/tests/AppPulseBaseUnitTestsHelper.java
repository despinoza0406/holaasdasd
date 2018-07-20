package hubble.backend.providers.tests;

import hubble.backend.providers.models.apppulse.AvailabilityDataProviderModel;
import hubble.backend.providers.models.apppulse.AvailabilityProviderModel;
import hubble.backend.providers.models.apppulse.ErrorProviderModel;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class AppPulseBaseUnitTestsHelper {

    String pathLocation = "/fakes/apppulse/";

    public InputStream LoadAvailabilityFakeDataUnique() {

        String fakeFileName = pathLocation + "fake-data.json";
        InputStream appPulseDataRaw = getClass().getResourceAsStream(fakeFileName);
        return appPulseDataRaw;
    }

    public InputStream LoadAvailabilityFakeDataList1() {

        String fakeFileName = pathLocation + "fake-data-list-1.json";
        InputStream appPulseDataRaw = getClass().getResourceAsStream(fakeFileName);
        return appPulseDataRaw;
    }

    public InputStream LoadAvailabilityFakeDataList2() {

        String fakeFileName = pathLocation + "fake-data-list-2.json";
        InputStream appPulseDataRaw = getClass().getResourceAsStream(fakeFileName);
        return appPulseDataRaw;
    }

    public static AvailabilityProviderModel getFakeAppPulseProviderModel() {

        //Error
        ErrorProviderModel error = new ErrorProviderModel();
        ArrayList<ErrorProviderModel> errors = new ArrayList<>();
        error.setErrorMessage("fake-error-message");
        error.setFileName("fake-file-name");
        error.setLineNumber(1);
        errors.add(error);

        //Data
        long activityTime = 1;
        AvailabilityDataProviderModel appPulseData = new AvailabilityDataProviderModel();
        appPulseData.setApplicationId("1");
        appPulseData.setApplicationName("fake-name");
        appPulseData.setAvailabilityFailIfAbove(1);
        appPulseData.setAvailabilityStatus("fake-status");
        appPulseData.setCriticalThreshold(1);
        appPulseData.setErrors(errors);
        appPulseData.setLocationId("1");
        appPulseData.setLocationName("fake-location");
        appPulseData.setNumberOfErrors(1);
        appPulseData.setPerformanceStatus("fake-performance-status");
        appPulseData.setPoorThreshold(1);
        appPulseData.setResponseTime(1);
        appPulseData.setScriptName("fake-script-name");
        appPulseData.setServerName("fake-server-name");
        appPulseData.setTimeStamp(activityTime);

        ArrayList<AvailabilityDataProviderModel> appPulseDataList = new ArrayList<>();
        appPulseDataList.add(appPulseData);
        AvailabilityProviderModel model = new AvailabilityProviderModel();
        model.setData(appPulseDataList);

        return model;
    }

    public static JSONObject getFakeAppPulseJson() {

        JSONObject appPulseJson = new JSONObject();

        JSONArray availabilitiesData = new JSONArray();
        //TODO: mover a un archivo para levantar y pasarlo a string.
        availabilitiesData.put(new JSONObject("{\"poorThreshold\":8000,\"locationName\":\"Washington, DC AT&T\","
                + "\"responseTime\":36684,\"criticalThreshold\":12000,\"serverName\":null,"
                + "\"transactionName\":\"Despliegue Pagina Publica\",\"availabilityFailIfAbove\":25000,"
                + "\"transactionId\":\"f702ea77d5dabdb0aa984f9c3c865229\",\"numberOfErrors\":0,\"timeStamp\":1499026016,"
                + "\"locationId\":\"16ec0037849cd0b4c4d538fe3d643621\",\"performanceStatus\":\"No Status\",\"scriptName\":\"Ingreso Clientes_HB\","
                + "\"availabilityStatus\":\"Timeout\",\"applicationId\":\"e071193b8376e06554eb2344173cb66d\","
                + "\"applicationName\":\"BancoRipley - HomeBanking\",\"errors\":null}"));
        appPulseJson.put("lastRetrievedSequenceId", "1");
        appPulseJson.put("hasMoreDataToFetch", false);
        appPulseJson.put("data", availabilitiesData);

        return appPulseJson;
    }

    //Get Configuration
    public InputStream LoadConfigurationFakeUnique() {

        String fakeFileName = pathLocation + "fake-configuration-list.json";
        InputStream appPulseConfigurationRaw = getClass().getResourceAsStream(fakeFileName);
        return appPulseConfigurationRaw;
    }
}
