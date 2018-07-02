package hubble.backend.business.services.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.AvailabilityStorage;
import hubble.backend.storage.models.ErrorStorage;
import hubble.backend.storage.models.TransactionStorage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.Assert;

public class AvailabilityHelper {

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

    public List<AvailabilityStorage> mockData() {
        List<AvailabilityStorage> availabilityStorageList = new ArrayList();
        AvailabilityStorage availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48f");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1314);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 9));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48g");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1208);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j39sdf");
        availabilityStorage.setTransactionName("Despliegue Formulario Consumo");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 7));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48h");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(914);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 6));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 3));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf3");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f82323ar");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sdf58f8sdf48por");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 4));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y47h5sk58f8sdf409");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 6));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h4sk58f8sdf4q");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk5tyf8sdf483");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 26));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk12358f8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 28));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk5asd8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 32));

        availabilityStorageList.add(availabilityStorage);

        return availabilityStorageList;

    }

    public List<AvailabilityStorage> mockThreeDaysAgoData() {
        List<AvailabilityStorage> availabilityStorageList = new ArrayList();
        AvailabilityStorage availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48f");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1314);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 9 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48g");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1208);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j39sdf");
        availabilityStorage.setTransactionName("Despliegue Formulario Consumo");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 7 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48h");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(914);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 6 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 3 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf3");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f82323ar");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 2 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sdf58f8sdf48por");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 4 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y47h5sk58f8sdf409");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 6 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h4sk58f8sdf4q");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 3 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk5tyf8sdf483");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 5 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk12358f8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 9 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk5asd8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 2 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);

        return availabilityStorageList;

    }

    public List<AvailabilityStorage> mockTwoDaysAgoData() {
        List<AvailabilityStorage> availabilityStorageList = new ArrayList();
        AvailabilityStorage availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48f");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1314);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 9 - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48g");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1208);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j39sdf");
        availabilityStorage.setTransactionName("Despliegue Formulario Consumo");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 7 - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48h");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(914);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 6 - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 3 - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf3");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f82323ar");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 2 - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sdf58f8sdf48por");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 4 - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y47h5sk58f8sdf409");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 6 - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h4sk58f8sdf4q");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 3 - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk5tyf8sdf483");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 5 - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk12358f8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 9 - Calendar.HOUR * 24));

        availabilityStorageList.add(availabilityStorage);

        return availabilityStorageList;

    }

    public List<AvailabilityStorage> mockLowAvailabilityData() {
        List<AvailabilityStorage> availabilityStorageList = new ArrayList();
        AvailabilityStorage availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48f");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1314);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 9 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48g");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1208);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j39sdf");
        availabilityStorage.setTransactionName("Despliegue Formulario Consumo");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 7 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48h");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(914);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 6 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 3 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf3");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f82323ar");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 2 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sdf58f8sdf48por");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 4 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y47h5sk58f8sdf409");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 6 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h4sk58f8sdf4q");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 3 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk5tyf8sdf483");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 5 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk12358f8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 9 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk5asd8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 2 - Calendar.HOUR * 24 * 2));

        availabilityStorageList.add(availabilityStorage);

        return availabilityStorageList;

    }

    public List<AvailabilityStorage> mockLowPerformanceData() {
        List<AvailabilityStorage> availabilityStorageList = new ArrayList();
        AvailabilityStorage availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48f");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(131400);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 9));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48g");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(12008);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j39sdf");
        availabilityStorage.setTransactionName("Despliegue Formulario Consumo");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 7));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48h");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(91400);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 6));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(14701);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 3));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Error");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf3");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f82323ar");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(10471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 30));

        availabilityStorageList.add(availabilityStorage);

        return availabilityStorageList;

    }

    public List<AvailabilityStorage> mockADayAgoData() {
        List<AvailabilityStorage> availabilityStorageList = new ArrayList();
        AvailabilityStorage availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48f");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1314);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 9));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48g");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1208);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j39sdf");
        availabilityStorage.setTransactionName("Despliegue Formulario Consumo");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 7));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48h");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(914);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 6));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 3));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf3");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f82323ar");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 2));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sdf58f8sdf48por");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 4));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y47h5sk58f8sdf409");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 6));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h4sk58f8sdf4q");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 3));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk5tyf8sdf483");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 5));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk12358f8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 9));

        availabilityStorageList.add(availabilityStorage);
        availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk5asd8sdf48i");
        availabilityStorage.setLocationId("sdfksd637373ft");
        availabilityStorage.setLocationName("Sonda Interna - Ripley 1");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1471);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j393k");
        availabilityStorage.setTransactionName("Despliegue Zona Campana");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.HOUR * 2));

        availabilityStorageList.add(availabilityStorage);

        return availabilityStorageList;

    }

    public AvailabilityStorage mockOneAvailabilityStorage() {
        AvailabilityStorage availabilityStorage = new AvailabilityStorage();

        availabilityStorage.setApplicationId("1");
        availabilityStorage.setApplicationName("BancoRipley - HomeBanking");
        availabilityStorage.setAvailabilityFailIfAbove(90);
        availabilityStorage.setAvailabilityStatus("Success");
        availabilityStorage.setErrors(null);
        availabilityStorage.setId("h3y44h5sk58f8sdf48g");
        availabilityStorage.setLocationId("sdfksd637373f8");
        availabilityStorage.setLocationName("Washington DC, AT&T");
        availabilityStorage.setNumberOfErrors(0);
        availabilityStorage.setPerformanceStatus("Warning");
        availabilityStorage.setProviderOrigin("AppPulse Active");
        availabilityStorage.setResponseTime(1208);
        availabilityStorage.setScriptName("HomeBanking");
        availabilityStorage.setServerName("Arizona");
        availabilityStorage.setTransactionId("h5l394kdd9j39sdf");
        availabilityStorage.setTransactionName("Despliegue Formulario Consumo");
        availabilityStorage.setTimeStamp(new Date(System.currentTimeMillis() - Calendar.MINUTE * 7));

        return availabilityStorage;
    }

    public ApplicationStorage mockApplicationStorage() {
        ApplicationStorage applicationStorage = new ApplicationStorage();

        applicationStorage.setApplicationId("b566958ec4ff28028672780d15edcf56");
        applicationStorage.setApplicationName("BancoRipley - HomeBanking");
        applicationStorage.setActive(true);

        return applicationStorage;
    }

    public List<TransactionStorage> mockTransactionStorage() {
        List<TransactionStorage> transactionStorageList = new ArrayList();
        TransactionStorage transactionStorage1 = new TransactionStorage();
        TransactionStorage transactionStorage2 = new TransactionStorage();

        transactionStorage1.setTransactionId("2eae220e082697be3a0646400e5b54fa");
        transactionStorage1.setTransactionName("Auntenticacion Biometrica");
        transactionStorage1.setCriticalThreshold(12000);
        transactionStorage1.setAssigned(true);
        transactionStorage1.setTransactionType("script");
        transactionStorage1.setScriptName("Auntenticacion_Biometrica_VBIO3301");
        transactionStorage1.setOkThreshold(8000);
        transactionStorage1.setCriticalThreshold(12000);

        transactionStorage2.setTransactionId("8f0e0ec4adb5f063b1afe794e03a0ab4");
        transactionStorage2.setTransactionName("Firma Biometrica");
        transactionStorage2.setCriticalThreshold(12);
        transactionStorage2.setAssigned(true);
        transactionStorage2.setTransactionType("script");
        transactionStorage2.setScriptName("Firma_Biometrica_VBIO3101");
        transactionStorage2.setOkThreshold(8000);
        transactionStorage2.setCriticalThreshold(12000);

        transactionStorageList.add(transactionStorage1);
        transactionStorageList.add(transactionStorage2);
        return transactionStorageList;
    }
}
