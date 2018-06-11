package hubble.backend.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateHelper {

    public static Date lastExecutionDate = new Date();

    public static long getDateDiff(Date dateFrom, Date dateTo, TimeUnit timeUnit) {
        long diffInMillies = dateTo.getTime() - dateFrom.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    
    public static Date addDaysToDate(Date date, int daysQty){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, daysQty);
        return calendar.getTime();
    }

    public static Date getDateNow() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static Date getYesterday(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date getAnHourAgo(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        return cal.getTime();
    }
}
