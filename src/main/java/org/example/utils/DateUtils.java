package org.example.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    /**
     * @param date               Date to process
     * @param timeZone           Timezone used in the date param
     * @param biggestField2Erase Biggest field number to erase, as in {@link Calendar}
     * @return date with fields erased
     */
    public static Date eraseDateRange(Date date, TimeZone timeZone, int biggestField2Erase) {
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(date);
        return eraseDateRange(calendar, biggestField2Erase).getTime();
    }

    public static Calendar eraseDateRange(Calendar date, int biggestField2Erase) {
        switch (biggestField2Erase) {
            case Calendar.MONTH:
                date.set(Calendar.MONTH, 0);
            case Calendar.DATE:
                date.set(Calendar.DATE, 1);
            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
                date.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.MINUTE:
                date.set(Calendar.MINUTE, 0);
            case Calendar.SECOND:
                date.set(Calendar.SECOND, 0);
            case Calendar.MILLISECOND:
                date.set(Calendar.MILLISECOND, 0);
                break;
            default:
                throw new IllegalArgumentException("Invalid field to erase");
        }
        return date;
    }
}
