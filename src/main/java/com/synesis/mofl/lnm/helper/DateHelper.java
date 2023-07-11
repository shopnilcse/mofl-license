package com.synesis.mofl.lnm.helper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Md. Emran Hossain
 * @since 25 Jan, 2022
 * @version 1.1
 */
public class DateHelper {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(DateHelper.class);

    /**
     * This method return current system date time
     * 
     * @author Md. Emran Hossain
     * @return LocalDateTime - system time
     * @since 24 Feb, 2022
     */
    public static LocalDateTime currentDateTime(){
        return LocalDateTime.now();
    }

    /**
     * This method convert Local Date to Date
     * 
     * @author Md. Emran Hossain
     * @param localDate - LocalDate
     * @return Date - Date
     * @throws Exception - Exception
     * @since 25 Jan, 2022
     * @version 1.1
     */
    public static Date convertLocalDateToDate(LocalDate localDate) throws Exception {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * This method convert Date to Local Date
     * 
     * @author Md. Emran Hossain
     * @param date - Date
     * @return LocalDate - LocalDate
     * @throws Exception - Exception
     * @since 25 Jan, 2022
     * @version 1.1
     */
    public static LocalDate convertDateToLocalDate(Date date) throws Exception {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * This method convert Local Date Time to Date
     * 
     * @author Md. Emran Hossain
     * @param localDateTime - LocalDateTime
     * @return Date - Date
     * @throws Exception - Exception
     * @since 25 Jan, 2022
     * @version 1.1
     */
    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) throws Exception {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * This method convert Date to Local Date Time
     * 
     * @author Md. Emran Hossain
     * @param date - Date
     * @return LocalDateTime - LocalDateTime
     * @throws Exception - Exception
     * @since 25 Jan, 2022
     * @version 1.1
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date) throws Exception {
          return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        //return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * This method convert Local Date to Local Date Time
     * 
     * @author Md. Emran Hossain
     * @param localDate - LocalDate
     * @param hour - integer
     * @param min - integer
     * @param sec - integer
     * @return LocalDateTime - LocalDateTime
     * @throws Exception - Exception
     * @since 24 Mar, 2022
     * @version 1.1
     */
    public static LocalDateTime convertDateToLocalDateTime(LocalDate localDate, int hour, int min, int sec) throws Exception {
        return localDate.atTime(hour, min, sec);
    }

    /**
     * This method convert String date to Date
     * 
     * @author Md. Emran Hossain
     * @param date - String
     * @return formatedDate - Date
     * @throws Exception - Exception
     * @since 24 Mar, 2022
     * @version 1.1
     */
    public static Date convertStringDateToDate(String date) throws Exception {
        LocalDate date1 = LocalDate.parse(date);
        Date formatedDate = convertLocalDateToDate(date1);
        return formatedDate;
    }

    /**
     * This method convert String date to Date with hour minute and second
     * 
     * @author Md. Emran Hossain
     * @param date - String
     * @param hour - integer
     * @param min - integer
     * @param sec - integer
     * @return convertedDate - Date
     * @throws Exception - Exception
     * @since 27 Mar, 2022
     * @version 1.1
     */
    @SuppressWarnings("deprecation")
    public static Date convertStringDateToDate(String date, int hour, int min, int sec) throws Exception {
        Date convertedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        convertedDate.setHours(hour);
        convertedDate.setMinutes(min);
        convertedDate.setSeconds(sec);
        return convertedDate;
    }
    

    /**
     * This method add an year with Local Date Time
     * 
     * @author Md. Emran Hossain
     * @param localDate - LocalDate
     * @return LocalDate - LocalDate
     * @throws Exception - Exception
     * @since 25 Jan, 2022
     * @version 1.1
     */
    public static LocalDate addYearForExpireDate(LocalDate localDate) throws Exception {
        Calendar calender = Calendar.getInstance();
        calender.setTime(convertLocalDateToDate(localDate));

        calender.add(Calendar.YEAR, 1);
        calender.add(Calendar.DATE, -1);
        Date currentDatePlusOne = calender.getTime();

        return convertDateToLocalDate(currentDatePlusOne);
    }

    /**
     * This method add year which are given in license category
     * 
     * @author Md. Emran Hossain
     * @param localDate - LocalDate
     * @param year - int
     * @return LocalDate - LocalDate
     * @throws Exception - Exception
     * @since 25 Jan, 2022
     * @version 1.1
     */
    public static LocalDate addYearForExpireDate(LocalDate localDate, int year) throws Exception {
        Calendar calender = Calendar.getInstance();
        calender.setTime(convertLocalDateToDate(localDate));

        calender.add(Calendar.YEAR, year);
        calender.add(Calendar.DATE, -1);
        Date currentDatePlusOne = calender.getTime();

        return convertDateToLocalDate(currentDatePlusOne);
    }

    /**
     * This method reassign an year with Local Date Time
     * 
     * @author Md. Emran Hossain
     * @param localDate - LocalDate
     * @return LocalDate - LocalDate
     * @throws Exception - Exception
     * @since 25 Jan, 2022
     * @version 1.1
     */
    public static LocalDate reassignYearForExpireDate(LocalDate localDate) throws Exception {
        Calendar calender = Calendar.getInstance();
        calender.setTime(convertLocalDateToDate(localDate));

        calender.add(Calendar.YEAR, 1);
        Date currentDatePlusOne = calender.getTime();

        return convertDateToLocalDate(currentDatePlusOne);
    }
}
