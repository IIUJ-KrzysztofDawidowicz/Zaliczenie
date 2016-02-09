package pl.edu.uj.andriod.Zaliczenie.timed;

import android.content.Context;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static pl.edu.uj.andriod.Zaliczenie.Preferences.getHoursBetweenNotifications;
import static pl.edu.uj.andriod.Zaliczenie.timed.NotificationDelayCalculator.minutesTillNextDailyNotification;
import static pl.edu.uj.andriod.Zaliczenie.timed.PostNotification.RepeatPeriod.DAILY;
import static pl.edu.uj.andriod.Zaliczenie.timed.PostNotification.RepeatPeriod.HOURLY;

public class NotificationScheduler {
    private static final ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(1);
    private static final long MINUTES_PER_DAY = 60 * 24;
    private static ScheduledFuture dailyNotification = null;
    private static ScheduledFuture hourlyNotification = null;

    static {
        executorService.setRemoveOnCancelPolicy(true);
    }

    public static void scheduleDailyNotification(Context context) {
        if (dailyNotification != null) {
            dailyNotification.cancel(false);
        }
        dailyNotification = doScheduleDailyNotification(context);
    }

    private static ScheduledFuture doScheduleDailyNotification(Context context) {
        return executorService.scheduleAtFixedRate(
                new PostNotification(context, DAILY),
                minutesTillNextDailyNotification(context), // first notification delay
                MINUTES_PER_DAY,                           // repeat every day
                TimeUnit.MINUTES);
    }

    public static void scheduleHourlyNotification(Context context) {
        cancelHourlyNotification();
        long hoursBetweenNotifications = getHoursBetweenNotifications(context);
        if (hoursBetweenNotifications > 0)
            hourlyNotification = doScheduleHourlyNotification(context, hoursBetweenNotifications);
    }

    private static ScheduledFuture doScheduleHourlyNotification(Context context, long hoursBetweenNotifications) {
        return executorService.scheduleAtFixedRate(
                new PostNotification(context, HOURLY), 
                hoursBetweenNotifications, 
                hoursBetweenNotifications, 
                TimeUnit.HOURS);
    }

    public static void cancelHourlyNotification() {
        if (hourlyNotification != null) {
            hourlyNotification.cancel(false);
            hourlyNotification = null;
        }
    }

}
