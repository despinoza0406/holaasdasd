package hubble.backend.core.utils;

public class CalculationHelper {
    private static double criticalIndex = 6;
    private static double warningIndex = 8;
    private final static double minCriticalIndex = 1;
    private final static double okIndex = 10;


    public static double calculateOkHealthIndex(double n, double maxThreshold, double minThreshold){
        return ( (n - minThreshold) / (maxThreshold - minThreshold) ) * (okIndex - warningIndex) + warningIndex;
    }

    public static double calculateWarningHealthIndex(double n, double maxThreshold, double minThreshold) {
        return ( (n - minThreshold) / (maxThreshold - minThreshold) ) * (warningIndex - criticalIndex) + criticalIndex;
    }

    public static double calculateCriticalHealthIndex(double n, double maxThreshold, double minThreshold) {
        return ( (n - minThreshold) / (maxThreshold - minThreshold) ) * (criticalIndex - minCriticalIndex) + minCriticalIndex;
    }

    public static double calculateMinInfiniteCriticalHealthIndex(double n, double maxThreshold, double escale) {
        return  ( Math.atan((n - maxThreshold) / escale) / (n - maxThreshold) * escale) * ((criticalIndex - minCriticalIndex)) + minCriticalIndex;
    }

    public static double calculateMinInfiniteCriticalHealthIndex(double n, double maxThreshold) {
        return  calculateMinInfiniteCriticalHealthIndex(n, maxThreshold, 1d);
    }

    public static double calculateDispOkHealthIndex(double n, double maxThreshold, double minThreshold) {
        return ( (n - minThreshold) / (maxThreshold - minThreshold) ) * (okIndex - warningIndex) + warningIndex;
    }

    public static double calculateDispWarningHealthIndex(double n, double maxThreshold, double minThreshold) {
        return ( (n - minThreshold) / (maxThreshold - minThreshold) ) * (warningIndex - criticalIndex) + criticalIndex;
    }

    public static double calculateDispCriticalHealthIndex(double n, double maxThreshold, double minThreshold) {
        return ( (n - minThreshold) / (maxThreshold - minThreshold) ) * (criticalIndex - minCriticalIndex) + minCriticalIndex;
    }

    public static double getCriticalIndex() {
        return criticalIndex;
    }

    public static double getWarningIndex() {
        return warningIndex;
    }

    public static void setCriticalIndex(double criticalIndex) {
        CalculationHelper.criticalIndex = criticalIndex;
    }

    public static void setWarningIndex(double warningIndex) {
        CalculationHelper.warningIndex = warningIndex;
    }
}
