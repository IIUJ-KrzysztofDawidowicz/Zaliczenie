package pl.edu.uj.andriod.Zaliczenie.timed;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

import static java.util.Calendar.*;
import static pl.edu.uj.andriod.Zaliczenie.Preferences.getPreferences;

public class NotificationDelayCalculator {
    private static final String HOUR = "NotificationHour";
    private static final String MINUTES = "NotificationMinutes";
    private static final int MILIS_PER_MINUTE = 60 * 1000;

    static long notificationDelay(Context context) {
        return timeLeftInMinutes(nextNotificationTime(context));
    }

    private static Calendar nextNotificationTime(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);
        int hour = sharedPreferences.getInt(HOUR, 8);
        int minutes = sharedPreferences.getInt(MINUTES, 0);
        return asCalendar(hour, minutes);
    }

    private static Calendar asCalendar(int hour, int minutes) {
        final Calendar calendar = getInstance();
        calendar.set(HOUR_OF_DAY, hour);
        calendar.set(MINUTE, minutes);
        if (calendar.before(getInstance())) {
            calendar.roll(DAY_OF_MONTH, 1);
        }
        return calendar;
    }

    private static long timeLeftInMinutes(Calendar calendar) {
        return (calendar.getTimeInMillis() - getInstance().getTimeInMillis()) / MILIS_PER_MINUTE;
    }
}
