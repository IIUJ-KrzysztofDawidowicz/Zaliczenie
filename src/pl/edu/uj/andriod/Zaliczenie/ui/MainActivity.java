package pl.edu.uj.andriod.Zaliczenie.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.sql.TaskDAO;

import java.util.Date;
import java.util.List;

import static pl.edu.uj.andriod.Zaliczenie.R.id.taksListView;
import static pl.edu.uj.andriod.Zaliczenie.R.layout.main;

public final class MainActivity extends Activity {

    private static final Task example = new Task("Pierwszy", "").setDeadline(new Date());
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(main);
        seedTable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }
    
    // Event listener
    public void newTask(View view){
        final Intent intent = new Intent(this, TaskEditActivity.class);
        intent.putExtra(TaskEditActivity.IS_NEW_TASK, true);
        startActivity(intent);
    }

    // Event listener
    public void launchDoneTasksList(View v){
        startActivity(new Intent(this, DoneTasksActivity.class));
    }

    private void seedTable() {
        TaskDAO dao = new TaskDAO(getBaseContext());
        dao.clearTable();
        dao.addTask(example);
    }

    private void loadTasks() {
        List<Task> tasks = new TaskDAO(getBaseContext()).getNotDoneTasks();
        ListView view = (ListView) findViewById(taksListView);
        view.setAdapter(new TaskListAdapter(getBaseContext(), tasks));
    }

}
