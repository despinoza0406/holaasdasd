package hubble.backend.core.utils;

import org.joda.time.DateTimeComparator;

import java.text.DateFormat;
import java.text.ParseException;
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

    public static Date getEndDate(String periodo){
        return getDateNow();
    }

    public static Date getStartDate(String periodo){
        switch (periodo){
            case "hora":
                return getAnHourAgo();
            case "dia":
                return getYesterday();
            case "semana":
                return getNDaysBefore(7);
            case "mes":
                return getMonth();
            default:
                return getYesterday();
        }
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

    public static Date getNDaysBefore(int n){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -n);
        return cal.getTime();
    }

    public static Date getMonth(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();
        return result;
    }


    public static Date getAnHourAgo(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        return cal.getTime();
    }

    public static Date getNHoursAgo(int hours){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -hours);
        return cal.getTime();
    }

    public static Date parseDateTime(String dateString) {
        if (dateString == null) return null;
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dateString.contains("T")) dateString = dateString.replace('T', ' ');
        if (dateString.contains("-0400")) dateString = dateString.replace(".000-0400", "");
        if (dateString.contains("Z")) dateString = dateString.replace("Z", "");
        else
            dateString = dateString.substring(0, dateString.lastIndexOf(':')) + dateString.substring(dateString.lastIndexOf(':')+1);
        try {
            return fmt.parse(dateString);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean areSameDate(Date date1, Date date2) {
        DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
        return dateTimeComparator.compare(date1, date2) == 0;
    }
}
