package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.averages.PerformanceOperations;
import hubble.backend.business.services.interfaces.operations.kpis.PerformanceKpiOperations;
import hubble.backend.business.services.interfaces.services.PerformanceService;
import hubble.backend.business.services.models.Application;
import hubble.backend.business.services.models.DistValues;
import hubble.backend.business.services.models.DistributionValues;
import hubble.backend.business.services.models.Performance;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.core.utils.CalculationHelper;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.AvailabilityStorage;
import hubble.backend.storage.models.Threashold;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.AvailabilityRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PerformanceServiceImpl implements PerformanceService {

    @Autowired
    AvailabilityRepository availabilityRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    PerformanceOperations performanceOperation;
    @Autowired
    PerformanceKpiOperations performanceKpiOperations;
    @Autowired
    MapperConfiguration mapper;

    private double inferior;
    private double criticalThreshold;
    private double warningThreshold;
    private double superior;

    @Override
    public List<Performance> getAll() {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAll();
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public Performance getById(String id) {
        return mapper.mapToPerformanceDto(availabilityRepository.findOne(id));
    }

    @Override
    public ApplicationIndicators calculateLast10MinutesAverageByApplication(String applicationId) {
        return performanceOperation.calculateLast10MinutesAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastHourAverageByApplication(String applicationId) {
        return performanceOperation.calculateLastHourAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastDayAverageByApplication(String applicationId) {
        return performanceOperation.calculateLastDayAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastMonthAverageByApplication(String applicationId) {
        return performanceOperation.calculateLastMonthAverageByApplication(applicationId);
    }

    @Override
    public Application getApplication(String applicationId) {
        ApplicationStorage applicationStorage = applicationRepository.findApplicationById(applicationId);
        return mapper.mapToApplicationDto(applicationStorage);
    }

    @Override
    public List<Performance> getAll(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationId(applicationId);
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public List<Performance> getLast10Minutes(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.TEN_MINUTES, applicationId);
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public List<Performance> getLastHour(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.ONE_HOUR, applicationId);
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public List<Performance> getLastDay(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.ONE_DAY, applicationId);
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public List<Performance> getLastMonth(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMonths(CalendarHelper.ONE_MONTH, applicationId);
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public List<Application> getAllApplications() {
        List<ApplicationStorage> applicationsStorage = applicationRepository.findAll();
        return mapper.mapToApplicationDtoList(applicationsStorage);
    }

    @Override
    public ApplicationIndicators calculateLastDayKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setPerformanceKpi(performanceKpiOperations.calculateLastDayKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public ApplicationIndicators calculateLastMonthKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setPerformanceKpi(performanceKpiOperations.calculateLastMonthKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public double calculateHealthIndexKPILastMonth(ApplicationStorage applicationStorage) {
        Threashold lastHourThreshold = applicationStorage.getKpis().getPerformance().getMonthThreashold();
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(applicationStorage.getApplicationId(), DateHelper.getNDaysBefore(30), DateHelper.getDateNow());
        double totalPerformance = 0;
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            totalPerformance = totalPerformance + availabilityStorage.getResponseTime();
        }

        double averagePerformance = totalPerformance / (double) availabilityStorageList.size();
        inferior = lastHourThreshold.getInferior();
        superior = lastHourThreshold.getSuperior();
        criticalThreshold = lastHourThreshold.getCritical();
        warningThreshold = lastHourThreshold.getWarning();

        if (averagePerformance <= warningThreshold) {
            return CalculationHelper.calculateOkHealthIndex(averagePerformance, inferior, warningThreshold);
        }

        if (averagePerformance <= criticalThreshold && averagePerformance > warningThreshold) {
            return CalculationHelper.calculateWarningHealthIndex(averagePerformance, warningThreshold, criticalThreshold);
        }

        return CalculationHelper.calculateMinInfiniteCriticalHealthIndex(averagePerformance, criticalThreshold);//, 1000d);
    }


    @Override
    public double calculateHealthIndexKPILastHour(ApplicationStorage applicationStorage) {
        Threashold lastHourThreshold = applicationStorage.getKpis().getPerformance().getHourThreashold();
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(applicationStorage.getApplicationId(), DateHelper.getAnHourAgo(), DateHelper.getDateNow());
        double totalPerformance = 0;
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            totalPerformance = totalPerformance + availabilityStorage.getResponseTime();
        }

        double averagePerformance = totalPerformance / (double) availabilityStorageList.size();
        inferior = lastHourThreshold.getInferior();
        superior = lastHourThreshold.getSuperior();
        criticalThreshold = lastHourThreshold.getCritical();
        warningThreshold = lastHourThreshold.getWarning();

        if (averagePerformance <= warningThreshold) {
            return CalculationHelper.calculateOkHealthIndex(averagePerformance, inferior, warningThreshold);
        }

        if (averagePerformance <= criticalThreshold && averagePerformance > warningThreshold) {
            return CalculationHelper.calculateWarningHealthIndex(averagePerformance, warningThreshold, criticalThreshold);
        }

        return CalculationHelper.calculateMinInfiniteCriticalHealthIndex(averagePerformance, criticalThreshold);//, 1000d);
    }

    @Override
    public double calculateHealthIndexKPI(ApplicationStorage application, String periodo) {
        Threashold threshold = application.getKpis().getPerformance().getThreashold(periodo);

        periodo = this.calculatePeriod(periodo);

        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(application.getId(),startDate,endDate);
        double totalPerformance = 0;
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            totalPerformance = totalPerformance + availabilityStorage.getResponseTime();
        }
        double averagePerformance = totalPerformance / (double) availabilityStorageList.size();
        inferior = threshold.getInferior();
        superior = threshold.getSuperior();
        criticalThreshold = threshold.getCritical();
        warningThreshold = threshold.getWarning();

        if (averagePerformance <= warningThreshold) {
            return CalculationHelper.calculateOkHealthIndex(averagePerformance, inferior, warningThreshold);
        }

        if (averagePerformance <= criticalThreshold && averagePerformance > warningThreshold) {
            return CalculationHelper.calculateWarningHealthIndex(averagePerformance, warningThreshold, criticalThreshold);
        }

        return CalculationHelper.calculateMinInfiniteCriticalHealthIndex(averagePerformance, criticalThreshold);//, 1000d);
    }

    @Override
    public ApplicationIndicators calculateLast10MinutesKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setPerformanceKpi(performanceKpiOperations.calculateLast10MinutesKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public ApplicationIndicators calculateLastHourKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setPerformanceKpi(performanceKpiOperations.calculateLast10MinutesKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public List<Integer> getDistValuesLastHour(String id) {
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(id, DateHelper.getAnHourAgo(), DateHelper.getDateNow());
        List<Integer> distValuesInt = new ArrayList<>();
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            distValuesInt.add((int) availabilityStorage.getResponseTime());
        }
        return distValuesInt;
    }

    @Override
    public List<DistValues> getDistValues(String id, String periodo) {
        List<DistValues> distValues = new ArrayList<>();
        periodo = this.calculatePeriod(periodo);

        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(id,startDate,endDate);
        List<Integer> distValuesInt = new ArrayList<>();
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            distValuesInt.add((int) availabilityStorage.getResponseTime());
        }
        for (Integer distValueInt: distValuesInt){
            distValues.add(new DistributionValues(distValueInt));
        }
        return distValues;
    }

    public String calculatePeriod(String periodo){
        if (periodo.equals("default")){ //esto se hace por como funciona el date helper
            return "hora";
        }else {
            return periodo;
        }
    }
}
