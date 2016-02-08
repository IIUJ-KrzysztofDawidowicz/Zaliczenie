package pl.edu.uj.andriod.Zaliczenie.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import pl.edu.uj.andriod.Zaliczenie.Util;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.model.TaskState;
import pl.edu.uj.andriod.Zaliczenie.sql.TaskDAO;

import static pl.edu.uj.andriod.Zaliczenie.R.id.*;
import static pl.edu.uj.andriod.Zaliczenie.ui.TaskEditActivity.TASK_ID;

final class TaskView {
    private final TextView title;
    private final TextView description;
    private final TextView deadline;
    private final Button edit;
    private final Button status;
    private final Button delete;
    private final TaskListAdapter adapter;

    private Task task;

    TaskView(View view) {
        view.setTag(this);
        title = Util.getView(view, taskTitle, TextView.class);
        description = Util.getView(view, taskDescription, TextView.class);
        deadline = Util.getView(view, taskDeadline, TextView.class);
        edit = Util.getView(view, taskEditButton, Button.class);
        status = Util.getView(view, statusButton, Button.class);
        delete = Util.getView(view, taskDeleteButton, Button.class);
        
        adapter =  (TaskListAdapter)((ListView) view.getParent()).getAdapter();

        setOnClickListeners();
    }

    void fillInData(Task task) {
        this.task = task;
        title.setText(task.getTitle());
        description.setText(task.getDescription());
        if (task.getDeadline() != null) {
            deadline.setText(Util.dateFormat.format(task.getDeadline()));
        }
        status.setText(task.getState().toString());
        if (task.getState() == TaskState.DONE) status.setEnabled(false);

    }

    private void updateStatus(View v) {
        if (task.nextState())
            new TaskDAO(v.getContext()).updateTask(task);
        status.setText(task.getState().toString());
    }

    private void launchEditWindow(View view) {
        final Context context = view.getContext();
        Intent intent = new Intent(context, TaskEditActivity.class);
        intent.putExtra(TASK_ID, task.getId());
        context.startActivity(intent);
    }
    
    private void deleteTask(View view) {
    	final Context context = view.getContext();
    	TaskDAO taskDAO = new TaskDAO(context);
    	taskDAO.deleteTask(task);
    	adapter.remove(task);
    }

    private void setOnClickListeners() {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchEditWindow(view);
            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus(v);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deleteTask(v);
			}
		});
    }
}
