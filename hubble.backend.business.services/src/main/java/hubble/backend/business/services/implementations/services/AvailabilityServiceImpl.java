package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.averages.AvailabilityOperations;
import hubble.backend.business.services.interfaces.operations.kpis.AvailabilityKpiOperations;
import hubble.backend.business.services.interfaces.services.AvailabilityService;
import hubble.backend.business.services.models.Application;
import hubble.backend.business.services.models.Availability;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.AvailabilityStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.AvailabilityRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityServiceImpl implements AvailabilityService {

    @Autowired
    AvailabilityRepository availabilityRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    AvailabilityOperations availavilityOperation;
    @Autowired
    AvailabilityKpiOperations availavilityKpiOperation;

    @Autowired
    MapperConfiguration mapper;

    @Override
    public List<Availability> getAll() {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAll();
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public Availability get(String id) {
        return mapper.mapToAvailabilityDto(availabilityRepository.findOne(id));
    }

    @Override
    public List<Availability> getLast10Minutes(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.TEN_MINUTES, applicationId);
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public List<Availability> getLastHour(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.ONE_HOUR, applicationId);
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public List<Availability> getLastDay(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.ONE_DAY, applicationId);
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public List<Availability> getLastMonth(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMonths(CalendarHelper.ONE_MONTH, applicationId);
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public List<Availability> getAll(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationId(applicationId);
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public Application getApplication(String applicationId) {
        ApplicationStorage applicationStorage = applicationRepository.findApplicationById(applicationId);
        return mapper.mapToApplicationDto(applicationStorage);
    }

    @Override
    public ApplicationIndicators calculateLast10MinutesAverageByApplication(String applicationId) {
        return availavilityOperation.calculateLast10MinutesAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastHourAverageByApplication(String applicationId) {
        return availavilityOperation.calculateLastHourAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastDayAverageByApplication(String applicationId) {
        return availavilityOperation.calculateLastDayAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastMonthAverageByApplication(String applicationId) {
        return availavilityOperation.calculateLastMonthAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLast10MinutesKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setAvailabilityKpi(availavilityKpiOperation.calculateLast10MinutesKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public ApplicationIndicators calculateLastHourKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setAvailabilityKpi(availavilityKpiOperation.calculateLastHourKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public ApplicationIndicators calculateLastDayKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setAvailabilityKpi(availavilityKpiOperation.calculateLastDayKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public ApplicationIndicators calculateLastMonthKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setAvailabilityKpi(availavilityKpiOperation.calculateLastMonthKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public List<Application> getAllApplications() {
        //TODO: Mover a un servicio de aplicación.
        List<ApplicationStorage> applicationsStorage = applicationRepository.findAllApplications();
        return mapper.mapToApplicationDtoList(applicationsStorage);
    }

    @Override
    public List<Integer> getDistValuesLastHour(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesBydAndPeriod(DateHelper.getDateNow(), DateHelper.getYesterday());
        List<Integer> distValuesInt = new ArrayList<>();
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            distValuesInt.add(Integer.parseInt(availabilityStorage.getAvailabilityStatus()));
        }
        return distValuesInt;
    }
}
