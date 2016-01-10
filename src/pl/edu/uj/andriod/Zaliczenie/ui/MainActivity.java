package pl.edu.uj.andriod.Zaliczenie.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.sql.TaskDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.layout.simple_list_item_1;
import static pl.edu.uj.andriod.Zaliczenie.R.id.taksListView;
import static pl.edu.uj.andriod.Zaliczenie.R.layout.main;

public final class MainActivity extends Activity {

    private static final Task example = new Task("Pierwszy", "").setDeadline(new Date());

    /**
     * Called when the activity is first created.
     */
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
    
    public void newTask(View view){
        startActivity(new Intent(this, NewItemActivity.class));
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

    private List<String> stringRepresentation(List<Task> tasks) {
        List<String> result = new ArrayList<>(tasks.size());
        for (Task task : tasks)
            result.add(task.toString());
        return result;
    }
}
