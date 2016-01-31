package pl.edu.uj.andriod.Zaliczenie.timed;

import android.content.Context;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static pl.edu.uj.andriod.Zaliczenie.timed.NotificationDelayCalculator.notificationDelay;

public class NotificationScheduler {
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public static void schedule(Context context) {
        executorService.schedule(new PostNotification(context), notificationDelay(context), TimeUnit.MINUTES);
    }

}
