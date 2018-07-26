package hubble.backend.api.implementations;

import hubble.backend.api.configurations.mappers.ApplicationMapper;
import hubble.backend.api.configurations.mappers.UptimeMapper;
import hubble.backend.api.interfaces.BusinessApplicationManager;
import hubble.backend.api.models.*;
import hubble.backend.business.services.interfaces.services.*;
import hubble.backend.business.services.interfaces.services.kpis.KpiAveragesService;
import hubble.backend.business.services.models.Application;
import hubble.backend.business.services.models.Availability;
import hubble.backend.business.services.models.measures.Uptime;
import hubble.backend.core.enums.MonitoringFields;
import hubble.backend.core.utils.CalendarHelper;

import java.util.*;
import java.util.stream.Collectors;

import hubble.backend.core.utils.DateHelper;

import static hubble.backend.storage.models.KPITypes.*;
import static java.lang.Double.NaN;
import static java.util.stream.Collectors.toList;

import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.KPITypes;
import hubble.backend.storage.models.KPIs;
import hubble.backend.storage.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessApplicationManagerImpl implements BusinessApplicationManager {

    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    AvailabilityService availabilityService;
    @Autowired
    PerformanceService performanceService;
    @Autowired
    IssueService issueService;
    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    UptimeDowntimeService uptimeService;
    @Autowired
    WorkItemService workItemService;
    @Autowired
    EventService eventService;
    @Autowired
    UptimeMapper uptimeMapper;
    @Autowired
    KpiAveragesService kpiAverageService;
    @Autowired
    ApplicationService applicationService;

    @Override
    public BusinessApplicationProfile getBusinessApplicationView(String id) {

        BusinessApplicationProfile businessView = new BusinessApplicationProfile();
        businessView.setId(id);
        //Availability Kpi
        businessView.setAvailabilityLast10MinKpi(availabilityService.calculateLast10MinutesKpiByApplication(id).getAvailabilityKpi().get());
        businessView.setAvailabilityLastHourKpi(availabilityService.calculateLastHourKpiByApplication(id).getAvailabilityKpi().get());
        businessView.setAvailabilityLastDayKpi(availabilityService.calculateLastDayKpiByApplication(id).getAvailabilityKpi().get());
        businessView.setAvailabilityLastMonthKpi(availabilityService.calculateLastMonthKpiByApplication(id).getAvailabilityKpi().get());

        //Performance Kpi
        businessView.setPerformanceLast10MinKpi(performanceService.calculateLast10MinutesKpiByApplication(id).getPerformanceKpi().get());
        businessView.setPerformanceLastHourKpi(performanceService.calculateLastHourKpiByApplication(id).getPerformanceKpi().get());
        businessView.setPerformanceLastDayKpi(performanceService.calculateLastDayKpiByApplication(id).getPerformanceKpi().get());
        businessView.setPerformanceLastMonthKpi(performanceService.calculateLastMonthKpiByApplication(id).getPerformanceKpi().get());

        //Issues Kpi
        businessView.setIssuesKpiLastDay(issueService.calculateLastDayKpiByApplication(id).get());
        businessView.setIssuesKpiLastMonth(issueService.calculateLastMonthKpiByApplication(id).get());

        //Tareas Kpi
        businessView.setWorkItemsKpiLastDay(workItemService.calculateLastDayKpiByApplication(id).get());
        businessView.setWorkItemsKpiLastMonth(workItemService.calculateLastMonthKpiByApplication(id).get());

        //Events Kpi
        /* No tiene sentido porque al final termina pidiendole un campo a mongo que no esta. Registered date. Se tendria que agregar
        businessView.setEventsKpiLastDay(eventService.calculateLastDayKpiByApplication(id).get());
        businessView.setEventsKpiLastMonth(eventService.calculateLastMonthKpiByApplication(id).get());
         */
        //Kpi Averages
        businessView.setHealthIndex(kpiAverageService.getStandardHealthIndex(id));
        //TODO: Eventos Kpi.
        return businessView;
    }

    @Override
    public List<BusinessApplication> getAllApplications() {
        List<Application> applicationDtoList = availabilityService.getAllApplications();
        return applicationMapper.mapToBusinessApplicationList(applicationDtoList);
    }

    @Override
    public List<ApplicationUptime> getUptimeLastMonth(String applicationId) {

        Calendar startCalendar = CalendarHelper.getNow();
        startCalendar.add(Calendar.MONTH, -1);
        Date startDate = startCalendar.getTime();

        Calendar endCalendar = CalendarHelper.getNow();
        endCalendar.add(Calendar.HOUR, 24);
        Date endDate = endCalendar.getTime();

        Uptime uptimeDto = uptimeService.getUptimeByApplication(applicationId, MonitoringFields.FRECUENCY.DAY, startDate, endDate);

        List<ApplicationUptime> uptimes = uptimeMapper.mapToUptimeList(uptimeDto);
        return uptimes;
    }

    @Override
    public List<Availability> getAvailabilityLast10Minutes(String applicationId) {
        List<Availability> availabilityList = availabilityService.getLast10Minutes(applicationId);
        availabilityList.sort(Comparator.comparing(Availability::getTimeStamp));
        return availabilityList;
    }

    @Override
    public List<Availability> getAvailabilityLastHour(String applicationId) {
        List<Availability> availabilityList = availabilityService.getLastHour(applicationId);
        availabilityList.sort(Comparator.comparing(Availability::getTimeStamp));
        return availabilityList;
    }

    @Override
    public List<BusinessApplicationProfile> getBusinessApplicationsPageLimit(int page, int limit) {

        List<BusinessApplication> applications = getAllApplications();
        List<BusinessApplicationProfile> applicationsProfiles = new ArrayList<>();

        for (BusinessApplication businessApplication : applications) {

            if (businessApplication.getId() == null) {
                continue;
            }

            BusinessApplicationProfile currentBusinessApplicationProfile = getBusinessApplicationView(businessApplication.getId());

            applicationsProfiles.add(currentBusinessApplicationProfile);

        }

        return applicationsProfiles;
    }

    @Override
    public BusinessApplicationFrontend getBusinessApplicationFrontend(String id,String periodo) {
        ApplicationStorage applicationStorage = applicationRepository.findApplicationById(id);

        BusinessApplicationFrontend businessApplicationFrontend = new BusinessApplicationFrontend();

        businessApplicationFrontend.setId(applicationStorage.getId());
        businessApplicationFrontend.setLastUpdate(DateHelper.lastExecutionDate);
        businessApplicationFrontend.setPastUpdate(DateHelper.addDaysToDate(DateHelper.lastExecutionDate, -1));
        setKPIs(businessApplicationFrontend, applicationStorage, periodo);
        setHealthIndex(businessApplicationFrontend, businessApplicationFrontend.getKpis());
        setPastHealthIndex(businessApplicationFrontend, applicationStorage);

        return businessApplicationFrontend;
    }

    @Override
    public BusinessApplicationFrontend getBusinessApplicationFrontendDistValues(String id,String period) {
        BusinessApplicationFrontend businessApplicationFrontend = getBusinessApplicationFrontend(id,period);
        setDistValues(businessApplicationFrontend.getKpis(), id,period);
        return businessApplicationFrontend;
    }

    private void setDistValues(List<KpiFrontend> kpis, String id,String period) {
        List<DistValues> distValues;
        for (KpiFrontend kpi : kpis) {
            distValues = getDistValuesOf(kpi.getKpiName(), id,period);
            kpi.setDistribution(distValues);
        }
    }

    @Override
    public List<BusinessApplicationFrontend> getBusinessApplicationsFrontend(boolean includeInactives,String periodo) {
        return getAllApplications()
            .stream()
            .filter((a) -> includeInactives || a.isActive())
            .map((a) -> getBusinessApplicationFrontend(a.getId(),periodo))
            .collect(toList());
    }

    public void setHealthIndex(BusinessApplicationFrontend businessApplicationFrontend, List<KpiFrontend> kpisFront) {

        List<Double> kpis;
        kpis = kpisFront.stream().map(x -> x.getKpiValue()).collect(Collectors.toList()); //me lo mapea a los valores de los kpi

        double healthIndex = getKPIAverage(kpis);
        businessApplicationFrontend.setHealthIndex(healthIndex);
    }

    public void setPastHealthIndex(BusinessApplicationFrontend businessApplicationFrontend, ApplicationStorage application) {
        double availabilityKPImonth = availabilityService.calculateHealthIndexKPILastHour(application);
        double performanceKPIday = performanceService.calculateHealthIndexKPILastMonth(application);
        double issuesKPIday = issueService.calculateHistoryDayBeforeKpiByApplication(application);
        double workItemKPIday = workItemService.calculatePastDayDeflectionDaysKpi(application);
        double eventKPIday = eventService.calculatePastHourSeverityKpi(application);

        List<Double> kpis = new ArrayList<>();
        kpis.add(availabilityKPImonth);
        kpis.add(performanceKPIday);
        kpis.add(issuesKPIday);
        kpis.add(workItemKPIday);
        kpis.add(eventKPIday);

        double pastHealthIndex = getKPIAverage(kpis);
        businessApplicationFrontend.setHealthIndexPast(pastHealthIndex);
    }

    public void setKPIs(BusinessApplicationFrontend businessApplicationFrontend, ApplicationStorage application,String periodo) {
        List<KpiFrontend> kpis = new ArrayList<>();
        Set<KPITypes> kpiTypes = application.getKpis().getEnabledKPIs();

        if (kpiTypes.contains(AVAILABILITY)) {
            KpiFrontend availabilityKpi = new KpiFrontend();
            availabilityKpi.setKpiName("Disponibilidad");
            availabilityKpi.setKpiShortName("D");
            availabilityKpi.setKpiValue(availabilityService.calculateHealthIndexKPI(application,periodo));
            if(Double.isNaN(availabilityKpi.getKpiValue())) {
                availabilityKpi.setKpiComment("No hay datos de availability");
            }
            kpis.add(availabilityKpi);
        }

        if (kpiTypes.contains(PERFORMANCE)){
            KpiFrontend performanceKpi = new KpiFrontend();
            performanceKpi.setKpiName("Performance");
            performanceKpi.setKpiShortName("P");
            performanceKpi.setKpiValue(performanceService.calculateHealthIndexKPI(application,periodo));
            if(Double.isNaN(performanceKpi.getKpiValue())) {
                performanceKpi.setKpiComment("No hay datos de performance");
            }
            kpis.add(performanceKpi);
        }
        if(kpiTypes.contains(DEFECTS)) {
            KpiFrontend issuesKpi = new KpiFrontend();
            issuesKpi.setKpiName("Incidencias");
            issuesKpi.setKpiShortName("I");
            issuesKpi.setKpiValue(issueService.calculateHistoryKPIByApplication(application,periodo));
            if(Double.isNaN(issuesKpi.getKpiValue())) {
                issuesKpi.setKpiComment("No hay datos de issues");
            }
            kpis.add(issuesKpi);
        }
        if(kpiTypes.contains(TASKS)) {
            KpiFrontend workitemKpi = new KpiFrontend();
            workitemKpi.setKpiName("Tareas");
            workitemKpi.setKpiShortName("T");
            workitemKpi.setKpiValue(workItemService.calculateDeflectionDaysKPI(application,periodo));
            if(Double.isNaN(workitemKpi.getKpiValue())){
                workitemKpi.setKpiComment("No hay datos de tareas");
            }
            kpis.add(workitemKpi);
        }
        if(kpiTypes.contains(EVENTS)) {
            KpiFrontend eventKpi = new KpiFrontend();
            eventKpi.setKpiName("Eventos");
            eventKpi.setKpiShortName("E");
            eventKpi.setKpiValue(eventService.calculateSeverityKPI(application,periodo));
            if(Double.isNaN(eventKpi.getKpiValue())) {
                eventKpi.setKpiComment("No hay datos de eventos");
            }
            kpis.add(eventKpi);
        }
        businessApplicationFrontend.setKpis(kpis);
    }

    public double getKPIAverage(List<Double> kpis) {
        double average = 0;

        for (Double kpi : kpis) {
            if(!Double.isNaN(kpi)) {
                average = average + kpi;
            }
        }

        return average / (double) kpis.size();
    }

    private List<DistValues> getDistValuesOf(String kpiName, String id,String period) {
        List<DistValues> distValues;
        List<Integer> distValuesInt;
        switch (kpiName) {
            case "Disponibilidad":
                distValuesInt = availabilityService.getDistValues(id,period);
                break;
            case "Performance":
                distValuesInt = performanceService.getDistValues(id,period);
                break;
            case "Incidencias":
                distValuesInt = issueService.getDistValues(id,period);
                break;
            case "Tareas":
                distValuesInt = workItemService.getDistValues(id,period);
                break;
            case "Eventos":
                distValuesInt = eventService.getDistValues(id,period);
                break;
            default:
                distValuesInt = new ArrayList<>();
                break;
        }
        distValues = convertIntegerListToDistValues(distValuesInt);
        return distValues;
    }

    @Override
    public KPIs getKPIs(String id) {
        ApplicationStorage applicationStorage = applicationRepository.findApplicationById(id);
        return applicationStorage.getKpis();
    }

    private List<DistValues> convertIntegerListToDistValues(List<Integer> distValuesInt) {
        List<DistValues> distValues = new ArrayList<>();
        for (Integer distValue : distValuesInt) {
            distValues.add(new DistValues(distValue));
        }
        return distValues;
    }

    @Override
    public List<BusinessApplicationLigth> getApplicationsLigth(boolean includeInactives) {
        
        List<ApplicationStorage> applicationDtoList = applicationService.getAll().stream().filter((a) -> includeInactives || a.isActive()).collect(toList());
        return applicationMapper.mapToBusinessApplicationLigthList(applicationDtoList);
        
    }
}
