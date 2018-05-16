package com.icare_clinics.app.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Girish Velivela on 08-07-2016.
 */
public class CalendarUtil {
    private final static String DATE_PATTERN_SERVICE = "yyyy-MM-dd";
    public static final String DATE_STD_PATTERN = "yyyy-MM-dd";
    public static final String DD_MM_YYYY_PATTERN = "dd-MM-yyyy";
    public static final String DD_MMM_YYYY_PATTERN = "dd-MMM-yyyy";
    public static final String MM_DD_YYYY_PATTERN = "MM-dd-yyyy";
    public static final String DATE_STD_PATTERN_MONTH = "yyyy-MM";
    public static final String MM_YYYY_PATTERN = "MM-yyyy";
    public static final String MMM_YYYY_PATTERN = "MMM-yyyy";
    public static final String MMM_PATTERN = "MMM";
    public static final String DATE_STD_PATTERN_FULLDATE = "MMM dd,yyyy";
    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_PATTERN_dd_MMM_YYYY = "dd-MMM-yyyy HH:mm:ss";
    public static final String DATE_PATTERN_dd_MM_YYYY = "dd-MM-yyyy HH:mm:ss";
    public static final String YYYY_MM_DD_FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_MMM_dd = "MMM dd";
    public static final String dd_MMM_PATTERN = "dd MMM";
    public static final String dd_MM_PATTERN = "dd MM";
    public static final String EEEE_PATTERN = "EEEE";
    public static final String SYNCH_DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm:ss aa";


    public static String getDate(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }

    public static String getDate(Date date, String pattern, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        return simpleDateFormat.format(date);
    }

    public static String getDate(String date, String parsePattern, String pattern, Locale locale) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(parsePattern);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
            return simpleDateFormat.format(parseDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDate(String strDate, String parsePattern, String pattern, long delay, Locale locale) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(parsePattern);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
            Date date = parseDateFormat.parse(strDate);
            return simpleDateFormat.format(date.getTime() - delay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getOrderDeliveryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 5);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN_dd_MM_YYYY);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static int[] getCurrentWeekYear() {
        Calendar calendar = Calendar.getInstance();
        int[] dayMonth = new int[3];
        dayMonth[0] = calendar.get(Calendar.DAY_OF_MONTH);
        dayMonth[1] = calendar.get(Calendar.MONTH);
        dayMonth[2] = calendar.get(Calendar.YEAR);
        return dayMonth;
    }

    public static int[] getCurrentDayMonthYear(String date, String pattern) {
        SimpleDateFormat parseDateFormat = new SimpleDateFormat(pattern);
        Date startDate = null;
        try {
            if (!StringUtils.isEmpty(date))
                startDate = parseDateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (startDate == null)
            startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int[] dayMonth = new int[2];
        dayMonth[0] = calendar.get(Calendar.WEEK_OF_YEAR);
        dayMonth[1] = calendar.get(Calendar.YEAR);
        return dayMonth;
    }

    public static boolean compareDate(String startDateStr, String endDateStr) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(DATE_PATTERN_dd_MM_YYYY);
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            if (startDate < endDate)
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static long getdifference(String startDateStr, String endDateStr) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(DATE_PATTERN_dd_MM_YYYY);
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            return (endDate - startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getdifference(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            long startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr).getTime();
            else
                startDate = new Date().getTime();
            long endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr).getTime();
            else
                endDate = new Date().getTime();
            return (endDate - startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCurrentMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
        return simpleDateFormat.format(new Date());
    }

    public static int getDifferenceOfWeek(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            Date startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr);
            else
                startDate = new Date();
            Date endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr);
            else
                endDate = new Date();
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            return endCalendar.get(Calendar.WEEK_OF_YEAR) - startCalendar.get(Calendar.WEEK_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getDifferenceOfMonth(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            Date startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr);
            else
                startDate = new Date();
            Date endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr);
            else
                endDate = new Date();
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            int endYear = endCalendar.get(Calendar.YEAR);
//            if(startCalendar.get(Calendar.YEAR)<endYear)
//                return ((endYear - startCalendar.get(Calendar.YEAR))*(endCalendar.get(Calendar.MONTH)+1))+(11-startCalendar.get(Calendar.MONTH));
//            return endCalendar.get(Calendar.MONTH)-startCalendar.get(Calendar.MONTH);
            return (((endYear - startCalendar.get(Calendar.YEAR)) * 12) + (endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getDifferenceDaysForRecurr(String startDateStr, String stratDayPattern, String endDateStr, String endDayPattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(stratDayPattern);
            Date startDate;
            if (!StringUtils.isEmpty(startDateStr))
                startDate = parseDateFormat.parse(startDateStr);
            else
                startDate = new Date();
            Date endDate;
            parseDateFormat = new SimpleDateFormat(endDayPattern);
            if (!StringUtils.isEmpty(endDateStr))
                endDate = parseDateFormat.parse(endDateStr);
            else
                endDate = new Date();
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            int endYear = startCalendar.get(Calendar.YEAR);
            if (startCalendar.get(Calendar.YEAR) != endYear)
                startCalendar.set(Calendar.YEAR, endYear);
            startCalendar.set(Calendar.MONTH, endCalendar.get(Calendar.MONTH));
            return endCalendar.get(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDay(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        if (day != 0)
            calendar.set(Calendar.DAY_OF_MONTH, day);
        if (month != 0)
            calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        if (day != 0)
            calendar.set(Calendar.DAY_OF_MONTH, day);
//        if(month != 0)
        calendar.set(Calendar.MONTH, month);
        if (year != 0)
            calendar.set(Calendar.YEAR, year);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DD_MMM_YYYY_PATTERN, Locale.ENGLISH);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDay(String date, String pattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(pattern);
            Date startDate;
            if (!StringUtils.isEmpty(date))
                startDate = parseDateFormat.parse(date);
            else
                startDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
            return simpleDateFormat.format(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getFullDay(String date, String pattern) {
        try {
            SimpleDateFormat parseDateFormat = new SimpleDateFormat(pattern);
            Date startDate;
            if (!StringUtils.isEmpty(date))
                startDate = parseDateFormat.parse(date);
            else
                startDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            switch (day) {
                case Calendar.SUNDAY:
                    return "Sunday";
                case Calendar.MONDAY:
                    return "Monday";
                case Calendar.TUESDAY:
                    return "Tuesday";
                case Calendar.WEDNESDAY:
                    return "Wednesday";
                case Calendar.THURSDAY:
                    return "Thursday";
                case Calendar.FRIDAY:
                    return "Friday";
                case Calendar.SATURDAY:
                    return "Saturday";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTodayWheterDate() {
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        String d = null;
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                d = "Sunday";
                break;
            case Calendar.MONDAY:
                d = "Monday";
                break;

            case Calendar.TUESDAY:
                d = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                d = "Wednesday";
                break;
            case Calendar.THURSDAY:
                d = "Thursday";
                break;
            case Calendar.FRIDAY:
                d = "Friday";
                break;
            case Calendar.SATURDAY:
                d = "Saturday";
                break;
        }
        String month = String.format(Locale.US, "%tb", calendar);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        return (sb.append(d).append(",").append(" ").append(month).append(" ").append(date).append(",").append(" ").append(year).toString());

    }

    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        String d = null;
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                d = "Sunday";
                break;
            case Calendar.MONDAY:
                d = "Monday";
                break;
            case Calendar.TUESDAY:
                d = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                d = "Wednesday";
                break;
            case Calendar.THURSDAY:
                d = "Thursday";
                break;
            case Calendar.FRIDAY:
                d = "Friday";
                break;
            case Calendar.SATURDAY:
                d = "Saturday";
                break;
        }
        String month = String.format(Locale.US, "%tb", calendar);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        return (sb.append(d).append(",").append(" ").append(month).append(" ").append(date).append(",").append(" ").append(year).toString());
    }

    public static String[] getNextSevenDays() {
        String[] dayMonth = new String[7];
        SimpleDateFormat sdf = new SimpleDateFormat("MMM");
        for(int i=0;i<7;i++){
            String day="";
            String month="";
            StringBuilder sb = new StringBuilder();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, i);
            int date=  calendar.get(Calendar.DAY_OF_MONTH);
            int month1=calendar.get(Calendar.MONTH);
//            String month1 = sdf.format(calendar.get(Calendar.MONTH));
            // String month = String.format(Locale.US, "%tb", calendar);

/*
                Calendar calendar = new GregorianCalendar();
                calendar.add(Calendar.DATE, i);
                String date = sdf.format(calendar.get(Calendar.DAY_OF_MONTH));
                String month = sdf.format(Calendar.MONTH);
                String daywek = sdf.format(calendar.get(Calendar.DAY_OF_WEEK));
                String year = sdf.format(Calendar.YEAR);

*/
           /* SimpleDateFormat mdformat = new SimpleDateFormat("MMM");
            String month = mdformat.format(calendar.get(Calendar.MONTH));*/

            int year = calendar.get(Calendar.YEAR);
            int daywek = calendar.get(Calendar.DAY_OF_WEEK);

            switch (daywek) {
                case Calendar.SUNDAY:
                    day = "Sun";
                    break;
                case Calendar.MONDAY:
                    day = "Mon";
                    break;
                case Calendar.TUESDAY:
                    day = "Tue";
                    break;
                case Calendar.WEDNESDAY:
                    day = "Wed";
                    break;
                case Calendar.THURSDAY:
                    day = "Thu";
                    break;
                case Calendar.FRIDAY:
                    day = "Fri";
                    break;
                case Calendar.SATURDAY:
                    day = "Sat";
                    break;
            }
            switch (month1){
                case 0:
                    month="Jan";
                    break;
                case 1:
                    month="Feb";
                    break;
                case 2:
                    month="Mar";
                case 3:
                    month="Apr";
                    break;
                case 4:
                    month="May";
                    break;
                case 5:
                    month="Jun";
                    break;
                case 6:
                    month="Jul";
                    break;
                case 7:
                    month="Aug";
                    break;
                case 8:
                    month="Sep";
                    break;
                case 9:
                    month="Out";
                    break;
                case 10:
                    month="Nov";
                    break;
                case 11:
                    month="Dec";
                    break;
            }
            dayMonth[i]=sb.append(day).append(",").append(date).append(",").append(month).append(",").append(year).toString();
        }

        return dayMonth;
    }
    public static String getTodayDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        StringBuilder sb = new StringBuilder();
        String d = null;
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                d = "Sunday";
                break;
            case Calendar.MONDAY:
                d = "Monday";
                break;

            case Calendar.TUESDAY:
                d = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                d = "Wednesday";
                break;
            case Calendar.THURSDAY:
                d = "Thursday";
                break;
            case Calendar.FRIDAY:
                d = "Friday";
                break;
            case Calendar.SATURDAY:
                d = "Saturday";
                break;
        }
        String month = String.format(Locale.US, "%tb", calendar);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        return (sb.append(d).append(",").append(" ").append(month).append(" ").append(date).append(",").append(" ").append(year).toString());
    }

    public static String convertToTrxDate(String fromDateFormat) {

        String formattedTime = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
            Date d = sdf.parse(fromDateFormat);
            formattedTime = output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

    public static String convertToTrxDateToShow(String fromDateFormat, Locale locale) {

        String formattedTime = "";
        try {
            if (fromDateFormat.contains("T"))
                fromDateFormat = fromDateFormat.replace("T", " ");
            /*Temp Solution*/
            if (fromDateFormat.contains("/"))
                fromDateFormat = fromDateFormat.replace("/", "-");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("dd MMM, yyyy hh:mm a", locale);
            Date d = sdf.parse(fromDateFormat);
            formattedTime = output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

    public static String convertToDeliDateToShow(String fromDateFormat, Locale locale) {

        String formattedTime = "";
        try {
            if (fromDateFormat.contains("T"))
                fromDateFormat = fromDateFormat.replace("T", " ");
            /*Temp Solution*/
            if (fromDateFormat.contains("/"))
                fromDateFormat = fromDateFormat.replace("/", "-");
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_dd_MMM_YYYY);
            SimpleDateFormat output = new SimpleDateFormat("dd MMM, yyyy", locale);
            Date d = sdf.parse(fromDateFormat);
            formattedTime = output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

    public static String convertToDeliDateToInsert(String fromDateFormat) {

        String formattedTime = "";
        try {
            if (fromDateFormat.contains("T"))
                fromDateFormat = fromDateFormat.replace("T", " ");
            /*Temp Solution*/
            if (fromDateFormat.contains("/"))
                fromDateFormat = fromDateFormat.replace("/", "-");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat(DATE_PATTERN_dd_MMM_YYYY, Locale.ENGLISH);
            Date d = sdf.parse(fromDateFormat);
            formattedTime = output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }
    public static String getServiceDate(String datetime) {
        int year = 0, month = 0, day = 0;
        String sdf = "";
        if (datetime.contains("T"))
            datetime = datetime.split("T")[0];
        if (datetime.split("-").length == 3) {
            year = StringUtils.getInt(datetime.split("-")[0]);
            month = StringUtils.getInt(datetime.split("-")[1]);
            day = StringUtils.getInt(datetime.split("-")[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            Date date = calendar.getTime();
            sdf = new SimpleDateFormat(DATE_PATTERN_SERVICE).format(date);
        }
        return sdf;
    }
    public static String getToday(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }
    public static String getlastSevenDays(){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, -7);
        Date date=cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        String start = dateFormat.format(date);
        //String endDate = dateFormat.format(theEnd.getTime());
        return start;
    }
    public static String getDateAndTime(Calendar calendar){
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        String start = dateFormat.format(calendar.getTime());
        return start;
    }
    public static String getNextSevenDays(Calendar calendar){
        Calendar theStart = (Calendar) calendar.clone();
        theStart.add(Calendar.DAY_OF_MONTH, 6);
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        String start = dateFormat.format(theStart.getTime());
        //String endDate = dateFormat.format(theEnd.getTime());
        return start;
    }
    public static String getFirstDayOfWeek(){
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        Date firstDayOfMonth = calendar.getTime();
        DateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        String start = sdf.format(firstDayOfMonth);
        return start;
    }
    public static String firstDayOfWeek(){
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        Date firstDayOfMonth = calendar.getTime();
        DateFormat sdf = new SimpleDateFormat(dd_MM_PATTERN);
        String start = sdf.format(firstDayOfMonth);
        return start;
    }
    public static String getFirstDayOfMonth(){
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        DateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        String start = sdf.format(firstDayOfMonth);
        return start;
    }
    public static String getFirstDayOfYear(){
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date firstDayOfMonth = calendar.getTime();
        DateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        String start = sdf.format(firstDayOfMonth);
        return start;
    }
    public static String getTodayformat(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy/MMM/dd");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }
    public static String getTodayformatNew(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(DD_MMM_YYYY_PATTERN);
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }
    public static String getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(DATE_STD_PATTERN);
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }
    public static String getPresentTime(){

        DateFormat dateFormat=new SimpleDateFormat("HH:mm:ss aa");
        Calendar cal=Calendar.getInstance();
        String strDateTime=dateFormat.format(cal.getTime());
        return strDateTime;
    }
    public static String getPresentDate(){

        DateFormat dateFormat=new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        Calendar cal=Calendar.getInstance();
        String strDateTime=dateFormat.format(cal.getTime());
        return strDateTime;
    }
    public static String getDateWith3letters(Calendar calendar)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
        String start = dateFormat.format(calendar.getTime());
        return start;
    }
    public static String getYear(Calendar calendar)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String start = dateFormat.format(calendar.getTime());
        return start;
    }
    public static String getStartOfDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 0, 0, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        String startTime=dateFormat.format(calendar.getTime());
        return startTime;
    }

    public static String getEndOfDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 23, 59, 59);
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        String endTime=dateFormat.format(calendar.getTime());
        return endTime;
    }

    public static String getTimeinAMorPM(){

        DateFormat dateFormat=new SimpleDateFormat("hh:mm aa");
        Calendar cal=Calendar.getInstance();
        String strDateTime=dateFormat.format(cal.getTime());
        return strDateTime;
    }
    public static int getMonthInt(){
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH)+1;
        return month;
    }

    public static Calendar stringToCalender(String selectedDate) {
        Date date = null;
        DateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_FULL_PATTERN);
        try {
             date = formatter.parse(selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
