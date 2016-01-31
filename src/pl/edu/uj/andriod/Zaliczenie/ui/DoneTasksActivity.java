package pl.edu.uj.andriod.Zaliczenie.ui;

import android.app.ListActivity;
import android.os.Bundle;
import pl.edu.uj.andriod.Zaliczenie.R;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.sql.TaskDAO;

import java.util.ArrayList;
import java.util.List;

public final class DoneTasksActivity extends ListActivity {

    private TaskDAO taskDAO;
    private final List<Task> data = new ArrayList<>();
    private TaskListAdapter taskListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.done_tasks);
        taskDAO = new TaskDAO(this);
        taskListAdapter = new TaskListAdapter(this, data);
        setListAdapter(taskListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        data.clear();
        data.addAll(taskDAO.getDoneTasks());
        taskListAdapter.notifyDataSetChanged();
    }
}