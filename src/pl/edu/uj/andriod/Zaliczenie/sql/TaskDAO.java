package pl.edu.uj.andriod.Zaliczenie.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import pl.edu.uj.andriod.Zaliczenie.model.Task;

import java.text.DateFormat;
import java.util.List;

public final class TaskDAO {
    private static final String TABLE = "tasks";
    private final TaskQueryHelper taskQueryHelper;
    private final SQLiteOpenHelper helper;
    private final DateFormat dateFormat = DateFormat.getDateInstance();

    public TaskDAO(Context context) {
        helper = new TaskSqlHelper(context);
        taskQueryHelper = new TaskQueryHelper(helper);
    }

    public Task getSingleTask(long taskId) {
        List<Task> result = taskQueryHelper.getTasks("id = " + taskId);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Task> getDoneTasks() {
        return taskQueryHelper.getTasks("state = 'done'");
    }

    public List<Task> getNotDoneTasks() {
        return taskQueryHelper.getTasks("state != 'done'");
    }

    public TaskDAO addTask(Task task) {
        final ContentValues values = contentValues(task);
        values.put("id", (String) null);
        helper.getWritableDatabase().insert(TABLE, null, values);
        return this;
    }

    public TaskDAO updateTask(Task task) {
        if (task.getId() == null) throw new IllegalStateException(String.format("%s missing id, cannot update", task));
        helper.getWritableDatabase().update(TABLE, contentValues(task), "id = " + task.getId(), null);
        return this;
    }

    private ContentValues contentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        values.put("description", task.getDescription());
        if (task.getDeadline() != null)
            values.put("deadline", dateFormat.format(task.getDeadline()));
        values.put("state", task.getState().getSqlName());
        return values;
    }

    public void clearTable() {
        helper.getWritableDatabase().delete(TABLE, null, null);
    }
}
