package pl.edu.uj.andriod.Zaliczenie.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import pl.edu.uj.andriod.Zaliczenie.R;
import pl.edu.uj.andriod.Zaliczenie.timed.NotificationScheduler;

import static pl.edu.uj.andriod.Zaliczenie.R.string.notification_time;

public class AppPreferences extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        getPreferences(MODE_PRIVATE).registerOnSharedPreferenceChangeListener(listener());
    }

    private NotificationTimeChangeListener listener() {
        return new NotificationTimeChangeListener(this.getApplicationContext());
    }

    private class NotificationTimeChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {
        private final Context context;

        public NotificationTimeChangeListener(Context context) {
            this.context = context;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(context.getString(notification_time))) {
                NotificationScheduler.scheduleDailyNotification(context);
            }
        }
    }
}