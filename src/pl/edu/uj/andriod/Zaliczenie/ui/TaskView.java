package pl.edu.uj.andriod.Zaliczenie.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.sql.TaskDAO;

import static pl.edu.uj.andriod.Zaliczenie.R.id.*;
import static pl.edu.uj.andriod.Zaliczenie.ui.TaskEditActivity.TASK_ID;

final class TaskView {
    public final TextView title;
    public final TextView description;
    public final Button edit;
    public final Button status;

    private Task task;

    TaskView(View view) {
        view.setTag(this);
        title = (TextView) view.findViewById(taskTitle);
        description = (TextView) view.findViewById(taskDescription);
        edit = (Button) view.findViewById(taskEditButton);
        status = (Button) view.findViewById(statusButton);

        setOnClickListeners();
    }

    void fillInData(Task task) {
        this.task = task;
        title.setText(task.getTitle());
        description.setText(task.getDescription());
        status.setText(task.getState().toString());
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
    }
}
