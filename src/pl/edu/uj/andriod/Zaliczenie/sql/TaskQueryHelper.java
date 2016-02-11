package pl.edu.uj.andriod.Zaliczenie.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.model.TaskState;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static pl.edu.uj.andriod.Zaliczenie.Util.dateFormat;
import static pl.edu.uj.andriod.Zaliczenie.sql.Cursors.*;
import static pl.edu.uj.andriod.Zaliczenie.sql.TaskTable.*;

/**
 * Extracts Tasks from the database. Extracted from the DAO due to verbose code.
 */
final class TaskQueryHelper {
    private final SQLiteOpenHelper helper;

    public TaskQueryHelper(SQLiteOpenHelper helper) {
        this.helper = helper;
    }

    public List<Task> getTasks(String selection) {
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

    private Cursor query(String selection) {
        return helper.getReadableDatabase().query(TABLE_NAME, null, selection, null, null, null, null);
    }

    private List<Task> convertTasks(Cursor cursor) {
        List<Task> tasks = new ArrayList<>();
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

    private Task task(Cursor cursor) throws ParseException {
        return new Task(getString(cursor, TITLE), getString(cursor, DESCRIPTION))
                .setId(getLong(cursor, ID))
                .setDeadline(deadline(cursor))
                .setState(state(cursor))
                .setPriority(getBoolean(cursor, PRIORITY));
    }
    
    private TaskState state(Cursor cursor) {
        return TaskState.parse(getString(cursor, STATE));
    }

    private Date deadline(Cursor cursor) throws ParseException {
        final String date = getString(cursor, DEADLINE);
        return date == null ? null : dateFormat().parse(date);
    }
}