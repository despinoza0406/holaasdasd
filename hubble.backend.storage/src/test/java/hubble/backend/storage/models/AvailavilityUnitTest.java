package hubble.backend.storage.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


import static com.sun.org.apache.xerces.internal.util.PropertyState.is;

public class AvailavilityUnitTest {


    Availavility availavility = new Availavility();
    @Before
    public void initialize() {


        Threashold hour = new Threashold();
        hour.setOk(50);
        hour.setWarning(2);
        hour.setCritical(5);
        availavility.setHourThreashold(hour);

        Threashold day = new Threashold();
        day.setOk(40);
        day.setWarning(2);
        day.setCritical(5);
        availavility.setDayThreashold(day);

        Threashold week = new Threashold();
        week.setOk(30);
        week.setWarning(2);
        week.setCritical(5);
        availavility.setWeekThreashold(week);

        Threashold month = new Threashold();
        month.setOk(150);
        month.setWarning(2);
        month.setCritical(5);
        availavility.setMonthThreashold(month);

    }

    @Test
    public void it_should_not_return_a_threshold() {
        assertEquals(null,availavility.getThreashold("hola"));
    }

    @Test
    public void it_should_return_hour_threshold_when_default(){
        assertEquals(50,availavility.getThreashold("default").getOk(),0);
    }

    @Test
    public void it_should_return_week_threshold(){
        assertEquals(30,availavility.getThreashold("semana").getOk(),0);
    }

}
