package be.vdab.training.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by admin on 15/10/2017.
 */
public final class DateUtility {
    // this is a utility class with class-methods only,
    // we don't wanMyApplicationt this class to be instantiated
    private DateUtility() {
    }

    public final static String convertLocalDateTimeToEuropeanDateAsString(LocalDateTime localDateTime) {
        return String.format("%td/%tm/%tY", localDateTime, localDateTime, localDateTime);
    }

    public final static String convertLocalDateTimeToEuropeanDateTimeAsString(LocalDateTime localDateTime) {
        return String.format("%td/%tm/%tY %tH:%tM:%tS", localDateTime, localDateTime, localDateTime, localDateTime, localDateTime, localDateTime);
    }

    public final static Date addNumberOfDaysToDate(Date date, int numberOfDays) {
        LocalDate localDate = convertDateToLocalDate(date);
        localDate = localDate.plusDays(numberOfDays);
        return convertLocalDateToDate(localDate);
    }

    public final static LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public final static LocalDate convertDateToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
//        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public final static long convertLocalDateToEpoch(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
        return localDate.atStartOfDay(zoneId).toEpochSecond();
    }

    public final static Date convertLocalDateToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

}
