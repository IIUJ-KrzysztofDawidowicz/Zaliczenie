package pl.edu.uj.andriod.Zaliczenie;

import android.app.Activity;
import android.view.View;

import java.text.DateFormat;

public class Util {
    public static final DateFormat dateFormat = DateFormat.getDateInstance();

    @SuppressWarnings("unchecked")
    public static <T> T getView(View parent, int id, Class<T> returnType) {
        return (T) parent.findViewById(id);
    }
    @SuppressWarnings("unchecked")
    public static <T> T getView(Activity parent, int id, Class<T> returnType) {
        return (T) parent.findViewById(id);
    }
}
