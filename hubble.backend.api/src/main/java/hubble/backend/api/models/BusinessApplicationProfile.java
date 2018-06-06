package hubble.backend.api.models;

public class BusinessApplicationProfile extends ApiResponseBase {

    private String id;
    private Integer issuesQtyLastDay;
    private Integer issuesQtyCriticalThreshold;
    private String statusIssuesQty;
    private Double availabilityAverage10min;
    private Double performanceAverage10min;
    private Integer measuresQtyAvailability10min;
    private Integer measuresQtyPerformance10min;
    private Double availabilityAverage1hour;
    private Double performanceAverage1hour;
    private Integer measuresQtyAvailability1hour;
    private Integer measuresQtyPerformance1hour;
    private Double availabilityAverage1day;
    private Double performanceAverage1day;
    private Integer measuresQtyAvailability1day;
    private Integer measuresQtyPerformance1day;
    private Double availabilityCriticalValue10min;
    private Double performanceCriticalValue10min;
    private Double availabilityCriticalValue1hour;
    private Double performanceCriticalValue1hour;
    private Double availabilityCriticalValue1day;
    private Double performanceCriticalValue1day;
    private String statusPerformance10min;
    private String statusAvailability10min;
    private String statusPerformance1hour;
    private String statusAvailability1hour;
    private String statusPerformance1day;
    private String statusAvailability1day;
    private Double uptime10min;
    private Double uptime1hour;
    private Double uptime1day;
    private Integer workItems1day;
    private Integer workItemsQtyCriticalThreshold;
    private String statusWorkItemsQty;
    private Double performanceLast10MinKpi;
    private Double availabilityLast10MinKpi;
    private Double performanceLastHourKpi;
    private Double availabilityLastHourKpi;
    private Double performanceLastDayKpi;
    private Double availabilityLastDayKpi;
    private Double performanceLastMonthKpi;
    private Double availabilityLastMonthKpi;
    private Double issuesKpiLastDay;
    private Double issuesKpiLastMonth;
    private Double workItemsKpiLastDay;
    private Double workItemsKpiLastMonth;
    private Double healthIndex;
    private Double eventsKpiLastDay;
    private Double eventsKpiLastMonth;

    public BusinessApplicationProfile() {
        performanceLast10MinKpi = 0d;
        performanceLast10MinKpi = 0d;
        performanceLast10MinKpi = 0d;
        performanceLast10MinKpi = 0d;
        performanceLast10MinKpi = 0d;
        performanceLast10MinKpi = 0d;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAvailabilityAverage10min() {
        return availabilityAverage10min;
    }

    public void setAvailabilityAverage10min(Double availabilityAverage) {
        this.availabilityAverage10min = availabilityAverage;
    }

    public Double getPerformanceAverage10min() {
        return performanceAverage10min;
    }

    public void setPerformanceAverage10min(Double performanceAverage) {
        this.performanceAverage10min = performanceAverage;
    }

    public Integer getMeasuresQtyAvailability10min() {
        return measuresQtyAvailability10min;
    }

    public void setMeasuresQtyAvailability10min(Integer measuresQtyAvailability) {
        this.measuresQtyAvailability10min = measuresQtyAvailability;
    }

    public Integer getMeasuresQtyPerformance10min() {
        return measuresQtyPerformance10min;
    }

    public void setMeasuresQtyPerformance10min(Integer measuresQtyPerformance) {
        this.measuresQtyPerformance10min = measuresQtyPerformance;
    }

    public void setAvailabilityAverage1hour(Double availabilityAverage1hour) {
        this.availabilityAverage1hour = availabilityAverage1hour;
    }

    public Double getPerformanceAverage1hour() {
        return performanceAverage1hour;
    }

    public void setPerformanceAverage1hour(Double performanceAverage1hour) {
        this.performanceAverage1hour = performanceAverage1hour;
    }

    public Integer getMeasuresQtyAvailability1hour() {
        return measuresQtyAvailability1hour;
    }

    public void setMeasuresQtyAvailability1hour(Integer measuresQtyAvailability1hour) {
        this.measuresQtyAvailability1hour = measuresQtyAvailability1hour;
    }

    public Integer getMeasuresQtyPerformance1hour() {
        return measuresQtyPerformance1hour;
    }

    public void setMeasuresQtyPerformance1hour(Integer measuresQtyPerformance1hour) {
        this.measuresQtyPerformance1hour = measuresQtyPerformance1hour;
    }

    public Double getAvailabilityAverage1day() {
        return availabilityAverage1day;
    }

    public void setAvailabilityAverage1day(Double availabilityAverage1day) {
        this.availabilityAverage1day = availabilityAverage1day;
    }

    public Double getPerformanceAverage1day() {
        return performanceAverage1day;
    }

    public void setPerformanceAverage1day(Double performanceAverage1day) {
        this.performanceAverage1day = performanceAverage1day;
    }

    public Integer getMeasuresQtyAvailability1day() {
        return measuresQtyAvailability1day;
    }

    public void setMeasuresQtyAvailability1day(Integer measuresQtyAvailability1day) {
        this.measuresQtyAvailability1day = measuresQtyAvailability1day;
    }

    public Integer getMeasuresQtyPerformance1day() {
        return measuresQtyPerformance1day;
    }

    public void setMeasuresQtyPerformance1day(Integer measuresQtyPerformance1day) {
        this.measuresQtyPerformance1day = measuresQtyPerformance1day;
    }

    public String getStatusPerformance10min() {
        return statusPerformance10min;
    }

    public void setStatusPerformance10min(String statusPerformance10min) {
        this.statusPerformance10min = statusPerformance10min;
    }

    public String getStatusAvailability10min() {
        return statusAvailability10min;
    }

    public void setStatusAvailability10min(String statusAvailability10min) {
        this.statusAvailability10min = statusAvailability10min;
    }

    public String getStatusPerformance1hour() {
        return statusPerformance1hour;
    }

    public void setStatusPerformance1hour(String statusPerformance1hour) {
        this.statusPerformance1hour = statusPerformance1hour;
    }

    public String getStatusAvailability1hour() {
        return statusAvailability1hour;
    }

    public void setStatusAvailability1hour(String statusAvailability1hour) {
        this.statusAvailability1hour = statusAvailability1hour;
    }

    public String getStatusPerformance1day() {
        return statusPerformance1day;
    }

    public void setStatusPerformance1day(String statusPerformance1day) {
        this.statusPerformance1day = statusPerformance1day;
    }

    public String getStatusAvailability1day() {
        return statusAvailability1day;
    }

    public void setStatusAvailability1day(String statusAvailability1day) {
        this.statusAvailability1day = statusAvailability1day;
    }

    public Double getAvailabilityAverage1hour() {
        return availabilityAverage1hour;
    }

    public Double getAvailabilityCriticalValue10min() {
        return availabilityCriticalValue10min;
    }

    public void setAvailabilityCriticalValue10min(Double availabilityCriticalValue10min) {
        this.availabilityCriticalValue10min = availabilityCriticalValue10min;
    }

    public Double getPerformanceCriticalValue10min() {
        return performanceCriticalValue10min;
    }

    public void setPerformanceCriticalValue10min(Double performanceCriticalValue10min) {
        this.performanceCriticalValue10min = performanceCriticalValue10min;
    }

    public Double getAvailabilityCriticalValue1hour() {
        return availabilityCriticalValue1hour;
    }

    public void setAvailabilityCriticalValue1hour(Double availabilityCriticalValue1hour) {
        this.availabilityCriticalValue1hour = availabilityCriticalValue1hour;
    }

    public Double getPerformanceCriticalValue1hour() {
        return performanceCriticalValue1hour;
    }

    public void setPerformanceCriticalValue1hour(Double performanceCriticalValue1hour) {
        this.performanceCriticalValue1hour = performanceCriticalValue1hour;
    }

    public Double getAvailabilityCriticalValue1day() {
        return availabilityCriticalValue1day;
    }

    public void setAvailabilityCriticalValue1day(Double availabilityCriticalValue1day) {
        this.availabilityCriticalValue1day = availabilityCriticalValue1day;
    }

    public Double getPerformanceCriticalValue1day() {
        return performanceCriticalValue1day;
    }

    public void setPerformanceCriticalValue1day(Double performanceCriticalValue1day) {
        this.performanceCriticalValue1day = performanceCriticalValue1day;
    }

    public Integer getIssuesQtyLastDay() {
        return issuesQtyLastDay;
    }

    public void setIssuesQtyLastDay(Integer issuesQtyLastDay) {
        this.issuesQtyLastDay = issuesQtyLastDay;
    }

    public Integer getIssuesQtyCriticalThreshold() {
        return issuesQtyCriticalThreshold;
    }

    public void setIssuesQtyCriticalThreshold(Integer issuesQtyCriticalThreshold) {
        this.issuesQtyCriticalThreshold = issuesQtyCriticalThreshold;
    }

    public String getStatusIssuesQty() {
        return statusIssuesQty;
    }

    public void setStatusIssuesQty(String statusIssuesQty) {
        this.statusIssuesQty = statusIssuesQty;
    }

    public Double getUptime10min() {
        return uptime10min;
    }

    public void setUptime10min(Double uptime10min) {
        this.uptime10min = uptime10min;
    }

    public Double getUptime1hour() {
        return uptime1hour;
    }

    public void setUptime1hour(Double uptime1hour) {
        this.uptime1hour = uptime1hour;
    }

    public Double getUptime1day() {
        return uptime1day;
    }

    public void setUptime1day(Double uptime1day) {
        this.uptime1day = uptime1day;
    }

    public Integer getWorkItems1day() {
        return workItems1day;
    }

    public void setWorkItems1day(Integer workItems1day) {
        this.workItems1day = workItems1day;
    }

    public Integer getWorkItemsQtyCriticalThreshold() {
        return workItemsQtyCriticalThreshold;
    }

    public void setWorkItemsQtyCriticalThreshold(Integer workItemsQtyCriticalThreshold) {
        this.workItemsQtyCriticalThreshold = workItemsQtyCriticalThreshold;
    }

    public String getStatusWorkItemsQty() {
        return statusWorkItemsQty;
    }

    public void setStatusWorkItemsQty(String statusWorkItemsQty) {
        this.statusWorkItemsQty = statusWorkItemsQty;
    }

    public Double getPerformanceLast10MinKpi() {
        return performanceLast10MinKpi;
    }

    public void setPerformanceLast10MinKpi(Double performanceLast10MinKpi) {
        this.performanceLast10MinKpi = performanceLast10MinKpi;
    }

    public Double getAvailabilityLast10MinKpi() {
        return availabilityLast10MinKpi;
    }

    public void setAvailabilityLast10MinKpi(Double availabilityLast10MinKpi) {
        this.availabilityLast10MinKpi = availabilityLast10MinKpi;
    }

    public Double getPerformanceLastHourKpi() {
        return performanceLastHourKpi;
    }

    public void setPerformanceLastHourKpi(Double performanceLastHourKpi) {
        this.performanceLastHourKpi = performanceLastHourKpi;
    }

    public Double getAvailabilityLastHourKpi() {
        return availabilityLastHourKpi;
    }

    public void setAvailabilityLastHourKpi(Double availabilityLastHourKpi) {
        this.availabilityLastHourKpi = availabilityLastHourKpi;
    }

    public Double getPerformanceLastDayKpi() {
        return performanceLastDayKpi;
    }

    public void setPerformanceLastDayKpi(Double performanceLastDayKpi) {
        this.performanceLastDayKpi = performanceLastDayKpi;
    }

    public Double getAvailabilityLastDayKpi() {
        return availabilityLastDayKpi;
    }

    public void setAvailabilityLastDayKpi(Double availabilityLastDayKpi) {
        this.availabilityLastDayKpi = availabilityLastDayKpi;
    }

    public Double getPerformanceLastMonthKpi() {
        return performanceLastMonthKpi;
    }

    public void setPerformanceLastMonthKpi(Double performanceLastMonthKpi) {
        this.performanceLastMonthKpi = performanceLastMonthKpi;
    }

    public Double getAvailabilityLastMonthKpi() {
        return availabilityLastMonthKpi;
    }

    public void setAvailabilityLastMonthKpi(Double availabilityLastMonthKpi) {
        this.availabilityLastMonthKpi = availabilityLastMonthKpi;
    }

    public Double getIssuesKpiLastDay() {
        return issuesKpiLastDay;
    }

    public void setIssuesKpiLastDay(Double issuesKpiLastDay) {
        this.issuesKpiLastDay = issuesKpiLastDay;
    }

    public Double getIssuesKpiLastMonth() {
        return issuesKpiLastMonth;
    }

    public void setIssuesKpiLastMonth(Double issuesKpiLastMonth) {
        this.issuesKpiLastMonth = issuesKpiLastMonth;
    }

    public Double getWorkItemsKpiLastDay() {
        return workItemsKpiLastDay;
    }

    public void setWorkItemsKpiLastDay(Double workItemspiLastDay) {
        this.workItemsKpiLastDay = workItemspiLastDay;
    }

    public Double getWorkItemsKpiLastMonth() {
        return workItemsKpiLastMonth;
    }

    public void setWorkItemsKpiLastMonth(Double workItemsKpiLastMonth) {
        this.workItemsKpiLastMonth = workItemsKpiLastMonth;
    }

    public Double getHealthIndex() {
        return healthIndex;
    }

    public void setHealthIndex(Double healthIndex) {
        this.healthIndex = healthIndex;
    }

    public Double getEventsKpiLastDay() {
        return eventsKpiLastDay;
    }

    public void setEventsKpiLastDay(Double eventsKpiLastDay) {
        this.eventsKpiLastDay = eventsKpiLastDay;
    }

    public Double getEventsKpiLastMonth() {
        return eventsKpiLastMonth;
    }

    public void setEventsKpiLastMonth(Double eventsKpiLastMonth) {
        this.eventsKpiLastMonth = eventsKpiLastMonth;
    }
}
