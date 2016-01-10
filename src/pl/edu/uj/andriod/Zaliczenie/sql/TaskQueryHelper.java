package pl.edu.uj.andriod.Zaliczenie.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.model.TaskState;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Extracts Tasks from the database. Extracted from the DAO due to verbose code.
 */
public class TaskQueryHelper {
    private static final String TABLE = "tasks";
    private static final int ID = 0;
    private static final int TITLE = 1;
    private static final int DESCRIPTION = 2;
    private static final int DEADLINE = 3;
    private static final int STATE = 4;
    private final SQLiteOpenHelper helper;
    private final DateFormat dateFormat = DateFormat.getDateInstance();

    public TaskQueryHelper(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    List<Task> getTasks(String selection) {
        Cursor cursor = null;
        try {
            cursor = query(selection);
            return convertTasks(cursor);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    Cursor query(String selection) {
        return helper.getReadableDatabase().query(TABLE, null, selection, null, null, null, null);
    }

    List<Task> convertTasks(Cursor cursor) {
        List<Task> tasks = new ArrayList<Task>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            try {
                tasks.add(task(cursor));
                cursor.moveToNext();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return tasks;
    }

    Task task(Cursor cursor) throws ParseException {
        return new Task(cursor.getString(TITLE), cursor.getString(DESCRIPTION))
                .setId(cursor.getLong(ID))
                .setDeadline(deadline(cursor))
                .setState(state(cursor));
    }
    
    TaskState state(Cursor cursor) {
        return TaskState.parse(cursor.getString(STATE));
    }

    Date deadline(Cursor cursor) throws ParseException {
        final String date = cursor.getString(DEADLINE);
        return date == null ? null : dateFormat.parse(date);
    }
}