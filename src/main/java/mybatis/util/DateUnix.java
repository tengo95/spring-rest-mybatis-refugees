package mybatis.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tanerali on 26/07/2017.
 */
public class DateUnix {

    public static long dateToSeconds (String time) {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long dateInSecs = date.getTime()/1000;

        return dateInSecs;
    }

    public static String secondsToDate (long timeInSecs) {

        Date dateOb = new Date(timeInSecs*1000);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String date = df.format(dateOb);

        return date;
    }

    public static String secondsToSpecificTime (long timeInMillis) {

        Date dateOb = new Date(timeInMillis*1000);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:MM:SS");
        String date = df.format(dateOb);

        return date;
    }
}
