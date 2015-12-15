package cc.ycn.common.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by andy on 6/22/15.
 */
public final class DateTool {

    private static final Map<String, SimpleDateFormat> DATE_FORMATTER_HOLDER = new ConcurrentHashMap<String, SimpleDateFormat>();
    private static final Map<String, DecimalFormat> NUMBERIC_FORMATTER_HOLDER = new ConcurrentHashMap<String, DecimalFormat>();

    public static String formatTime(String format) {
        return formatTime(new Date().getTime(), format);
    }

    public static String formatTime(long ts, String format) {
        if (!DATE_FORMATTER_HOLDER.containsKey(format)) {
            DATE_FORMATTER_HOLDER.put(format, new SimpleDateFormat(format));
        }
        return DATE_FORMATTER_HOLDER.get(format).format(ts);
    }

    public static String formatNumber(double num, String format) {
        if (!NUMBERIC_FORMATTER_HOLDER.containsKey(format)) {
            NUMBERIC_FORMATTER_HOLDER.put(format, new DecimalFormat(format));
        }
        return NUMBERIC_FORMATTER_HOLDER.get(format).format(num);
    }

    public static String getDateStr(Date date) {
        if (date == null)
            date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }

    public static Date getTodayDate(int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }

    public static Date getZeroTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        if (timestamp > 0) cal.setTime(new Date(timestamp));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        try {
            return sdf.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static long getYesterdayTime() {
        return getYesterdayTime(0);
    }

    public static long getTodayTime() {
        return getTodayTime(0);
    }

    public static long getTomorrowTime() {
        return getTomorrowTime(0);
    }

    public static long getAfterTomorrowTime() {
        return getAfterTomorrowTime(0);
    }

    public static long getYesterdayTime(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getZeroTime(timestamp));
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return cal.getTimeInMillis();
    }

    public static long getTodayTime(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getZeroTime(timestamp));
        return cal.getTimeInMillis();
    }

    public static long getTomorrowTime(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getZeroTime(timestamp));
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTimeInMillis();
    }

    public static long getAfterTomorrowTime(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getZeroTime(timestamp));
        cal.add(Calendar.DAY_OF_YEAR, 2);
        return cal.getTimeInMillis();
    }

    public static int getCurrentTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static int diffCurrentTime(long timestamp) {
        return (int) ((timestamp - System.currentTimeMillis()) / 1000);
    }

    public static String prettyDateTime(long timeStamp) {
        String pretty = "";

        if (timeStamp <= 0) {
            timeStamp = new Date().getTime();
        }

        long todayZeroTime = DateTool.getTodayTime();
        long tomorrowZeroTime = DateTool.getTomorrowTime();
        long afterTomorrowZeroTime = DateTool.getAfterTomorrowTime();

        if (timeStamp >= todayZeroTime && timeStamp < tomorrowZeroTime) {
            pretty = "今日";
        } else if (timeStamp >= tomorrowZeroTime && timeStamp < afterTomorrowZeroTime) {
            pretty = "明日";
        } else {
            pretty = "MM月dd日";
        }
        String dateFormat = pretty + "HH:mm";

        pretty = DateTool.formatTime(timeStamp, dateFormat);

        return pretty;
    }

    public static long getTimestamp(String dateStr) {
        Date date = null;
        if (StringTool.isEmpty(dateStr))
            return 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException ignore) {
        }

        return date == null ? 0 : date.getTime();
    }
}
