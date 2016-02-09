package pl.edu.uj.andriod.Zaliczenie.timed;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import pl.edu.uj.andriod.Zaliczenie.Preferences;
import pl.edu.uj.andriod.Zaliczenie.sql.TaskDAO;

import static pl.edu.uj.andriod.Zaliczenie.R.drawable.ic_launcher;
import static pl.edu.uj.andriod.Zaliczenie.timed.NotificationScheduler.cancelHourlyNotification;
import static pl.edu.uj.andriod.Zaliczenie.timed.NotificationScheduler.scheduleHourlyNotification;

class PostNotification implements Runnable {
    private final Context context;
    private final RepeatPeriod repeatPeriod;

    public PostNotification(Context context, RepeatPeriod repeatPeriod) {
        this.repeatPeriod = repeatPeriod;
        this.context = context.getApplicationContext();
    }
    
    enum RepeatPeriod{
        DAILY, HOURLY
    }

    @Override
    public void run() {
        if (needToMarkMorePriorityTasks(context)) {
            context.getSystemService(NotificationManager.class).notify(0, notification());
            if (repeatPeriod == RepeatPeriod.DAILY)
                scheduleHourlyNotification(context);
        } else {
            cancelHourlyNotification();
        }
    }

    private static boolean needToMarkMorePriorityTasks(Context context) {
        return priorityTaskCount(context) < Preferences.minPriorityTasks(context);
    }

    private static long priorityTaskCount(Context context) {
        final long priorityTaskCount = new TaskDAO(context).getPriorityTaskCount();
        Log.i("NotificationScheduler", "Priority task count: " + priorityTaskCount);
        return priorityTaskCount;
    }

    private Notification notification() {
        return new Notification.Builder(context)
                .setSmallIcon(ic_launcher)
                .setContentTitle("Zadania")
                .setContentText("Należy ustalić więcej zadań priorytetowych")
                .build();
    }
}
