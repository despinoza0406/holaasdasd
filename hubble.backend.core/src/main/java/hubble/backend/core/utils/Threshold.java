package hubble.backend.core.utils;

public class Threshold {

    //Performance
    public static final int WARNING_PERFORMANCE_DEFAULT = 8000;
    public static final int CRITICAL_PERFORMANCE_DEFAULT = 12000;

    //availability
    public static final int CRITICAL_AVAILABILITY_DEFAULT = 50;
    public static final int WARNING_AVAILABILITY_DEFAULT = 70;
    public static final int TOP_AVAILABILITY_PERCENTAGE = 100;

    public static class Issues {

        public static final int WARNING_ISSUES_MONTH_DEFAULT = 100;
        public static final int CRITICAL_ISSUES_MONTH_DEFAULT = 150;

        public static final int WARNING_ISSUES_DAY_DEFAULT = 5;
        public static final int CRITICAL_ISSUES_DAY_DEFAULT = 10;

        public static final int WARNING_ISSUES_HOUR_DEFAULT = 3;
        public static final int CRITICAL_ISSUES_HOUR_DEFAULT = 5;

        public static final int WARNING_ISSUES_10_MIN_DEFAULT = 3;
        public static final int CRITICAL_ISSUES_10_MIN_DEFAULT = 5;

        public static final int TOTAL_LEVELS_OF_SEVERITY = 3;

    }

    //WorkItems
    public static class WorkItems {

        public static final int WARNING_WORKITEMS_MONTH_DEFAULT = 21;
        public static final int CRITICAL_WORKITEMS_MONTH_DEFAULT = 30;

        public static final int WARNING_WORKITEMS_WEEK_DEFAULT = 4;
        public static final int CRITICAL_WORKITEMS_WEEK_DEFAULT = 6;

        public static final int TOTAL_LEVELS_OF_SEVERITY = 3;
    }

}
