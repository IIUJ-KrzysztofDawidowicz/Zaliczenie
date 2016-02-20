package pl.edu.uj.andriod.Zaliczenie.timed;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.StringTokenizer;

import static java.util.Calendar.*;
import static pl.edu.uj.andriod.Zaliczenie.Preferences.getNotificationTime;

public class NotificationDelayCalculator {
    private static final long MILIS_PER_MINUTE = 60L * 1000;

    static long minutesTillNextDailyNotification(Context context) {
        long minutes = timeLeftInMinutes(nextDailyNotificationTime(context));
        Log.d("NotificationScheduler", "Miutes till notification: " + minutes);
        return minutes;
    }

    private static Calendar nextDailyNotificationTime(Context context) {
        String[] hoursMinutes = getNotificationTime(context).split(":");
        return asCalendar(Integer.parseInt(hoursMinutes[0]), Integer.parseInt(hoursMinutes[1]));
    }


    private static Calendar asCalendar(int hour, int minutes) {
        final Calendar calendar = getInstance();
        Log.d("HourMinute:", hour + ":" + minutes);
        calendar.set(HOUR_OF_DAY, hour);
        calendar.set(MINUTE, minutes);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        if (calendar.before(getInstance())) {
            calendar.add(DAY_OF_MONTH, 1);
        }
        return calendar;
    }

    private static long timeLeftInMinutes(Calendar calendar) {
        Log.d("calendar", String.valueOf(calendar.getTimeInMillis()));
        Log.d("now", String.valueOf(getInstance().getTimeInMillis()));
        Calendar now = getInstance();
        now.set(SECOND, 0);
        now.set(MILLISECOND, 0);
        long milisDifference = calendar.getTimeInMillis() - now.getTimeInMillis();
        Log.d("MilisDifference: ", String.valueOf(milisDifference));
        return (milisDifference / MILIS_PER_MINUTE) - 720;
    }
}
