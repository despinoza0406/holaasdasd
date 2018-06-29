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
}
