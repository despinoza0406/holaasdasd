package hubble.backend.core.utils.tests;

import hubble.backend.core.utils.CalculationHelper;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class CalculationHelperUnitTest {

    @Test
    public void should_calculate_minInfiniteCriticalHealthIndex() {
        double healthIndex = CalculationHelper.calculateMinInfiniteCriticalHealthIndex(9000d, 8000d, 1000d);
        assertTrue( healthIndex <= 6);
    }

    @Test
    public void should_calculate_okHealthIndex(){
        double healthIndex = CalculationHelper.calculateOkHealthIndex(3000, 1, 4000);
        assertTrue(healthIndex > 8);
    }

    @Test
    public void should_calculate_critical_disp_healthIndex() {
        double healthIndex = CalculationHelper.calculateDispCriticalHealthIndex(42.85714d, 90d, 1d);
        assertTrue(healthIndex > 1d && healthIndex < 6d);
    }

    @Test
    public void should_calculate_critical_performance_healthIndex() {
        double healthIndex = CalculationHelper.calculateMinInfiniteCriticalHealthIndex(8000, 8010);
        assertTrue(healthIndex > 1d && healthIndex < 6d);
    }

    @Test
    public void should_calculate_critical_performance_with_escale_healthIndex() {
        double healthIndex = CalculationHelper.calculateMinInfiniteCriticalHealthIndex(8000, 14000,1000d);
        assertTrue(healthIndex > 1d && healthIndex < 6d);
    }
}
