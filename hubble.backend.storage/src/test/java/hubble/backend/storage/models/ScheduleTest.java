package hubble.backend.storage.models;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class ScheduleTest {

    @Test
    public void everyDay_9to18_daylyGeneratesCorrectExpression() {
        assertThat(
            "cron expression",
            new Schedule(Days.EVERY_DAY, HourRange._9_to_18, Frecuency.DAYLY).cronExpression(),
            is("0 0 9 * * *")
        );
    }

    @Test
    public void everyDay_9to18_hourlyGeneratesCorrectExpression() {
        assertThat(
            "cron expression",
            new Schedule(Days.EVERY_DAY, HourRange._9_to_18, Frecuency.HOURLY).cronExpression(),
            is("0 0 9-18 * * *")
        );
    }

    @Test
    public void everyDay_24hs_daylyGeneratesCorrectExpression() {
        assertThat(
            "cron expression",
            new Schedule(Days.EVERY_DAY, HourRange._24hs, Frecuency.DAYLY).cronExpression(),
            is("0 0 0 * * *")
        );
    }

    @Test
    public void everyDay_24hs_hourlyGeneratesCorrectExpression() {
        assertThat(
            "cron expression",
            new Schedule(Days.EVERY_DAY, HourRange._24hs, Frecuency.HOURLY).cronExpression(),
            is("0 0 * * * *")
        );
    }

    @Test
    public void workDay_9to18_daylyGeneratesCorrectExpression() {
        assertThat(
            "cron expression",
            new Schedule(Days.MONDAY_TO_FRIDAY, HourRange._9_to_18, Frecuency.DAYLY).cronExpression(),
            is("0 0 9 MON-FRI * *")
        );
    }

    @Test
    public void workDay_9to18_hourlyGeneratesCorrectExpression() {
        assertThat(
            "cron expression",
            new Schedule(Days.MONDAY_TO_FRIDAY, HourRange._9_to_18, Frecuency.HOURLY).cronExpression(),
            is("0 0 9-18 MON-FRI * *")
        );
    }

    @Test
    public void workDay_24hs_daylyGeneratesCorrectExpression() {
        assertThat(
            "cron expression",
            new Schedule(Days.MONDAY_TO_FRIDAY, HourRange._24hs, Frecuency.DAYLY).cronExpression(),
            is("0 0 0 MON-FRI * *")
        );
    }

    @Test
    public void workDay_24hs_hourlyGeneratesCorrectExpression() {
        assertThat(
            "cron expression",
            new Schedule(Days.MONDAY_TO_FRIDAY, HourRange._24hs, Frecuency.HOURLY).cronExpression(),
            is("0 0 * MON-FRI * *")
        );
    }
}
