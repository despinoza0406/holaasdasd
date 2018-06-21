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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import hubble.backend.core.utils.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessApplicationManagerImpl implements BusinessApplicationManager {

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

    @Override
    public BusinessApplicationProfile getBusinessApplicationView(String id) {

        BusinessApplicationProfile businessView = new BusinessApplicationProfile();
        businessView.setId(id);

        //10 minuntes Availability and Performance
//        ApplicationIndicators availabilityAvg10min = availabilityService.calculateLast10MinutesAverageByApplication(id);
//        ApplicationIndicators performanceAvg10min = performanceService.calculateLast10MinutesAverageByApplication(id);
//
//        if (availabilityAvg10min == null || performanceAvg10min == null) {
//            return businessView;
//        }
//
//        businessView.setAvailabilityAverage10min(availabilityAvg10min.getAvailabilityAverageValue());
//        businessView.setPerformanceAverage10min(performanceAvg10min.getPerformanceAverageValue());
//        businessView.setStatusAvailability10min(availabilityAvg10min.getAvailabilityAverage().getStatus().toString());
//        businessView.setStatusPerformance10min(performanceAvg10min.getPerformanceAverage().getStatus().toString());
//        businessView.setMeasuresQtyAvailability10min(availabilityAvg10min.getMeasuresQtyAvailability().getQuantity());
//        businessView.setMeasuresQtyPerformance10min(performanceAvg10min.getMeasuresQtyPerformance().getQuantity());
//
//        businessView.setAvailabilityCriticalValue10min(availabilityAvg10min.getAvailabilityThreshold());
//        businessView.setPerformanceCriticalValue10min(performanceAvg10min.getCriticalThreshold());
        //1 Hour Availability and Performance
//        ApplicationIndicators availabilityAvg1Hour = availabilityService.calculateLastHourAverageByApplication(id);
//        ApplicationIndicators performanceAvg1Hour = performanceService.calculateLastHourAverageByApplication(id);
//
//        businessView.setAvailabilityAverage1hour(availabilityAvg1Hour.getAvailabilityAverageValue());
//        businessView.setPerformanceAverage1hour(performanceAvg1Hour.getPerformanceAverageValue());
//        businessView.setStatusAvailability1hour(availabilityAvg1Hour.getAvailabilityAverage().getStatus().toString());
//        businessView.setStatusPerformance1hour(performanceAvg1Hour.getPerformanceAverage().getStatus().toString());
//        businessView.setMeasuresQtyAvailability1hour(availabilityAvg1Hour.getMeasuresQtyAvailability().getQuantity());
//        businessView.setMeasuresQtyPerformance1hour(performanceAvg1Hour.getMeasuresQtyPerformance().getQuantity());
//
//        businessView.setAvailabilityCriticalValue1hour(availabilityAvg1Hour.getAvailabilityThreshold());
//        businessView.setPerformanceCriticalValue1hour(performanceAvg1Hour.getCriticalThreshold());
        //1 Day Availability and Performance
//        ApplicationIndicators availabilityAvg1Day = availabilityService.calculateLastDayAverageByApplication(id);
//        ApplicationIndicators performanceAvg1Day = performanceService.calculateLastDayAverageByApplication(id);
//
//        businessView.setAvailabilityAverage1day(availabilityAvg1Day.getAvailabilityAverageValue());
//        businessView.setPerformanceAverage1day(performanceAvg1Day.getPerformanceAverageValue());
//        businessView.setStatusAvailability1day(availabilityAvg1Day.getAvailabilityAverage().getStatus().toString());
//        businessView.setStatusPerformance1day(performanceAvg1Day.getPerformanceAverage().getStatus().toString());
//        businessView.setMeasuresQtyPerformance1day(performanceAvg1Day.getMeasuresQtyPerformance().getQuantity());
//        businessView.setMeasuresQtyAvailability1day(availabilityAvg1Day.getMeasuresQtyAvailability().getQuantity());
//
//        businessView.setAvailabilityCriticalValue1day(availabilityAvg1Day.getAvailabilityThreshold());
//        businessView.setPerformanceCriticalValue1day(performanceAvg1Day.getCriticalThreshold());
        //Issues 1 Day - Total
//        IssuesQuantity issues = issueService.calculateIssuesQuantityLastDay(id);
//        businessView.setIssuesQtyLastDay(issues.getQuantity());
//        businessView.setStatusIssuesQty(issues.getStatus().toString());
//        businessView.setIssuesQtyCriticalThreshold(issues.getCriticalThreshold());
        //WorkItems
//        WorkItemQuantity workItems = workItemService.calculateWorkItemQuantityLastWeek(id);
//        businessView.setWorkItems1day(workItems.getQuantity());
//        businessView.setStatusWorkItemsQty(workItems.getStatus().toString());
//        businessView.setWorkItemsQtyCriticalThreshold(workItems.getCriticalThreshold());
//
//        //Uptime
//        businessView.setUptime10min(uptimeService.calculateLast10MinutesUptime(id).get());
//        businessView.setUptime1hour(uptimeService.calculateLastHourUptime(id).get());
//        businessView.setUptime1day(uptimeService.calculateLastDayUptime(id).get());
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
    public BusinessApplicationFrontend getBusinessApplicationFrontend(String id) {

        BusinessApplicationFrontend businessApplicationFrontend = new BusinessApplicationFrontend();

        businessApplicationFrontend.setId(id);
        businessApplicationFrontend.setLastUpdate(DateHelper.lastExecutionDate);
        businessApplicationFrontend.setPastUpdate(DateHelper.addDaysToDate(DateHelper.lastExecutionDate, -1));
        setHealthIndex(businessApplicationFrontend, id);
        setPastHealthIndex(businessApplicationFrontend, id);
        setKPIs(businessApplicationFrontend, id);

        return businessApplicationFrontend;
    }

    @Override
    public BusinessApplicationFrontend getBusinessApplicationFrontendDistValues(String id) {
        BusinessApplicationFrontend businessApplicationFrontend = getBusinessApplicationFrontend(id);
        setDistValues(businessApplicationFrontend.getKpis(), id);
        return businessApplicationFrontend;
    }

    private void setDistValues(List<KpiFrontend> kpis, String id) {
        List<DistValues> distValues;
        for (KpiFrontend kpi : kpis) {
            distValues = getDistValuesOf(kpi.getKpiName(), id);
            kpi.setDistribution(distValues);
        }
    }

    @Override
    public List<BusinessApplicationFrontend> getBusinessApplicationsFrontend() {
        List<BusinessApplication> applications = getAllApplications();
        List<BusinessApplicationFrontend> applicationsFrontend = new ArrayList<>();

        for (BusinessApplication businessApplication : applications) {

            if (businessApplication.getId() == null) {
                continue;
            }

            BusinessApplicationFrontend currentFrontendApplication = getBusinessApplicationFrontend(businessApplication.getId());

            applicationsFrontend.add(currentFrontendApplication);
        }
        return applicationsFrontend;
    }

    public void setHealthIndex(BusinessApplicationFrontend businessApplicationFrontend, String id){
        Double availabilityKPIminutes = availabilityService.calculateLastHourKpiByApplication(id).getAvailabilityKpi().get();
        Double performanceKPIminutes = performanceService.calculateLastHourKpiByApplication(id).getPerformanceKpi().get();
        Double issuesKPIminutes = issueService.calculateHistoryLastDayKpiByApplication(id);
        Double workItemKPIday = workItemService.calculateLastDayDeflectionDaysKpi(id);
        Double eventKPIday = eventService.calculateLastHourSeverityKpi(id);

        List<Double> kpis = new ArrayList<>();
        kpis.add(availabilityKPIminutes);
        kpis.add(performanceKPIminutes);
        kpis.add(issuesKPIminutes);
        kpis.add(workItemKPIday);
        kpis.add(eventKPIday);

        double healthIndex = getKPIAverage(kpis);
        businessApplicationFrontend.setHealthIndex(healthIndex);
    }

    public void setPastHealthIndex(BusinessApplicationFrontend businessApplicationFrontend, String id) {
        double availabilityKPImonth = availabilityService.calculateLastMonthKpiByApplication(id).getAvailabilityKpi().get();
        double performanceKPIday = performanceService.calculateLastMonthKpiByApplication(id).getPerformanceKpi().get();
        double issuesKPIday = issueService.calculateHistoryDayBeforeKpiByApplication(id);
        double workItemKPIday = workItemService.calculatePastDayDeflectionDaysKpi(id);
        double eventKPIday = eventService.calculatePastDaySeverityKpi(id);

        List<Double> kpis = new ArrayList<>();
        kpis.add(availabilityKPImonth);
        kpis.add(performanceKPIday);
        kpis.add(issuesKPIday);
        kpis.add(workItemKPIday);
        kpis.add(eventKPIday);

        double pastHealthIndex = getKPIAverage(kpis);
        businessApplicationFrontend.setHealthIndexPast(pastHealthIndex);
    }

    public void setKPIs(BusinessApplicationFrontend businessApplicationFrontend, String id) {
        List<KpiFrontend> kpis = new ArrayList<>();
        KpiFrontend availabilityKpi = new KpiFrontend();
        availabilityKpi.setKpiName("Disponibilidad");
        availabilityKpi.setKpiShortName("D");
        availabilityKpi.setKpiValue(availabilityService.calculateLastHourKpiByApplication(id).getAvailabilityKpi().get());
        kpis.add(availabilityKpi);
        KpiFrontend performanceKpi = new KpiFrontend();
        performanceKpi.setKpiName("Performance");
        performanceKpi.setKpiShortName("P");
        performanceKpi.setKpiValue(performanceService.calculateLastHourKpiByApplication(id).getPerformanceKpi().get());
        kpis.add(performanceKpi);
        KpiFrontend issuesKpi = new KpiFrontend();
        issuesKpi.setKpiName("Incidencias");
        issuesKpi.setKpiShortName("I");
        issuesKpi.setKpiValue(issueService.calculateHistoryLastDayKpiByApplication(id));
        kpis.add(issuesKpi);
        KpiFrontend workitemKpi = new KpiFrontend();
        workitemKpi.setKpiName("Tareas");
        workitemKpi.setKpiShortName("T");
        workitemKpi.setKpiValue(workItemService.calculateLastDayDeflectionDaysKpi(id));
        kpis.add(workitemKpi);
        KpiFrontend eventKpi = new KpiFrontend();
        eventKpi.setKpiName("Eventos");
        eventKpi.setKpiShortName("E");
        eventKpi.setKpiValue(eventService.calculateLastHourSeverityKpi(id));
        kpis.add(eventKpi);
        businessApplicationFrontend.setKpis(kpis);
    }

    public double getKPIAverage(List<Double> kpis) {
        double average = 0;

        for(Double kpi : kpis) {
            average = average + kpi;
        }

        return average / (double) kpis.size();
    }

    private List<DistValues> getDistValuesOf(String kpiName, String id) {
        List<DistValues> distValues;
        List<Integer> distValuesInt;
        switch (kpiName) {
            case "Disponibilidad":
                distValuesInt = availabilityService.getDistValuesLastHour(id);
                break;
            case "Performance":
                distValuesInt = performanceService.getDistValuesLastHour(id);
                break;
            case "Incidencias":
                distValuesInt = issueService.getDistValuesLastDay(id);
                break;
            case "Tareas":
                distValuesInt = workItemService.getDistValuesLastDay(id);
                break;
            case "Eventos":
                distValuesInt = eventService.getDistValuesLastHour(id);
                break;
            default:
                distValuesInt = new ArrayList<>();
                break;
        }
        distValues = convertIntegerListToDistValues(distValuesInt);
        return distValues;
    }

    private List<DistValues> convertIntegerListToDistValues(List<Integer> distValuesInt) {
        List<DistValues> distValues = new ArrayList<>();
        for (Integer distValue : distValuesInt) {
            distValues.add(new DistValues(distValue));
        }
        return distValues;
    }
}
