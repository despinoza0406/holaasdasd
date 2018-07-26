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
        hour.setInferior(100);
        hour.setWarning(25);
        hour.setCritical(5);
        hour.setSuperior(0);
        availavility.setHourThreashold(hour);

        Threashold day = new Threashold();
        day.setInferior(100);
        day.setWarning(30);
        day.setCritical(5);
        day.setSuperior(0);
        availavility.setDayThreashold(day);

        Threashold week = new Threashold();
        week.setInferior(100);
        week.setWarning(35);
        week.setCritical(5);
        week.setSuperior(0);
        availavility.setWeekThreashold(week);

        Threashold month = new Threashold();
        month.setInferior(100);
        month.setWarning(40);
        month.setCritical(5);
        month.setSuperior(0);
        availavility.setMonthThreashold(month);

    }

    @Test
    public void it_should_not_return_a_threshold() {
        assertEquals(null,availavility.getThreashold("hola"));
    }

    @Test
    public void it_should_return_hour_threshold_when_default(){
        assertEquals(25,availavility.getThreashold("default").getWarning(),0);
    }

    @Test
    public void it_should_return_week_threshold(){
        assertEquals(35,availavility.getThreashold("semana").getWarning(),0);
    }

}
