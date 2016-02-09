package pl.edu.uj.andriod.Zaliczenie.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import pl.edu.uj.andriod.Zaliczenie.R;


public class AppPreferences extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}