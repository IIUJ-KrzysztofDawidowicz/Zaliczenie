package pl.edu.uj.andriod.Zaliczenie.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import pl.edu.uj.andriod.Zaliczenie.model.Task;

import java.util.List;

import static pl.edu.uj.andriod.Zaliczenie.R.string.get_priority_count;
import static pl.edu.uj.andriod.Zaliczenie.Util.dateFormat;
import static pl.edu.uj.andriod.Zaliczenie.sql.TaskTable.*;

public final class TaskDAO {
    private final TaskQueryHelper taskQueryHelper;
    private final SQLiteOpenHelper helper;
    private final Context context;

    public TaskDAO(Context context) {
        this.context = context;
        helper = new TaskSqlHelper(context);
        taskQueryHelper = new TaskQueryHelper(helper);
    }

    public Task getSingleTask(long taskId) {
        List<Task> result = taskQueryHelper.getTasks("id = " + taskId);
        return result.isEmpty() ? null : result.get(0);
    }

    public long getPriorityTaskCount() {
        return helper.getReadableDatabase()
                .compileStatement(context.getString(get_priority_count))
                .simpleQueryForLong();
    }

    public List<Task> getDoneTasks() {
        return taskQueryHelper.getTasks("state = 'done'");
    }

    public List<Task> getNotDoneTasks() {
        return taskQueryHelper.getTasks("state != 'done'");
    }
    
    public List<Task> getAllTasks(){
        return taskQueryHelper.getTasks(null);
    }

    public TaskDAO addTask(Task task) {
        final ContentValues values = contentValues(task);
        values.put(ID.sqlName, (String) null);
        helper.getWritableDatabase().insert(TABLE_NAME, null, values);
        return this;
    }

    public TaskDAO updateTask(Task task) {
        if (task.getId() == null) throw new IllegalStateException(String.format("%s missing id, cannot update", task));
        helper.getWritableDatabase().update(TABLE_NAME, contentValues(task), "id = " + task.getId(), null);
        return this;
    }
    
    public TaskDAO deleteTask(Long taskId){
    	if (taskId == null) throw new IllegalStateException("missing id, cannot remove");
    	helper.getWritableDatabase().delete(TABLE_NAME, "id = " + taskId, null);
    	return this;
    }

    private ContentValues contentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TITLE.sqlName, task.getTitle());
        values.put(DESCRIPTION.sqlName, task.getDescription());
        if (task.getDeadline() != null)
            values.put(DEADLINE.sqlName, dateFormat().format(task.getDeadline()));
        values.put(STATE.sqlName, task.getState().getSqlName());
        values.put(PRIORITY.sqlName, task.isPriority());
        return values;
    }

    public void clearTable() {
        helper.getWritableDatabase().delete(TABLE_NAME, null, null);
    }
}
