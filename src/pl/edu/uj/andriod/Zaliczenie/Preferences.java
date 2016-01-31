package pl.edu.uj.andriod.Zaliczenie;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static pl.edu.uj.andriod.Zaliczenie.R.string.preferences;

public class Preferences {

    private static final String MIN_PRIORITY_TASKS = "MinPriorityTasks";

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(context.getString(preferences), MODE_PRIVATE);
    }

    public static int minPriorityTasks(Context context) {
        return getPreferences(context).getInt(MIN_PRIORITY_TASKS, 0);
    }
}
