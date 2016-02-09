package pl.edu.uj.andriod.Zaliczenie;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static pl.edu.uj.andriod.Zaliczenie.R.string.*;

public class Preferences {

    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    public static int minPriorityTasks(Context context) {
        return getPreferences(context).getInt(context.getString(min_priority_tasks), 0);
    }

    public static String getNotificationTime(Context context) {
        return getPreferences(context).getString(context.getString(notification_time), null);
    }
    
    public static long getHoursBetweenNotifications(Context context){
        return getPreferences(context).getLong(context.getString(hours_between_notifications), -1);
    }
}
