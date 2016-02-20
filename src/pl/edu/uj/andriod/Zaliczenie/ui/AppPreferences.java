package pl.edu.uj.andriod.Zaliczenie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import pl.edu.uj.andriod.Zaliczenie.Preferences;
import pl.edu.uj.andriod.Zaliczenie.R;
import pl.edu.uj.andriod.Zaliczenie.timed.NotificationScheduler;

import static pl.edu.uj.andriod.Zaliczenie.R.string.notification_time;

public class AppPreferences extends PreferenceActivity {

    private NotificationTimeChangeListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        listener = new NotificationTimeChangeListener();
        Preferences.getPreferences(this).registerOnSharedPreferenceChangeListener(listener);
    }

    private class NotificationTimeChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {
        private final Context context = AppPreferences.this;

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.d("AppPrefereces", "Shared preference chaged: " + key);
            if (key.equals(context.getString(notification_time))) {
                NotificationScheduler.scheduleDailyNotification(context);
            }
        }
    }
}