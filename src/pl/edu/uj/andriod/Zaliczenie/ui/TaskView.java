package pl.edu.uj.andriod.Zaliczenie.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import pl.edu.uj.andriod.Zaliczenie.Util;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.model.TaskState;
import pl.edu.uj.andriod.Zaliczenie.sql.TaskDAO;

import static android.R.drawable.btn_star_big_off;
import static android.R.drawable.btn_star_big_on;
import static pl.edu.uj.andriod.Zaliczenie.R.id.*;
import static pl.edu.uj.andriod.Zaliczenie.Util.dateFormat;
import static pl.edu.uj.andriod.Zaliczenie.Util.getView;
import static pl.edu.uj.andriod.Zaliczenie.ui.TaskEditActivity.TASK_ID;

final class TaskView {
    private final TextView title;
    private final TextView description;
    private final TextView deadline;
    private final Button edit;
    private final Button status;
    private final ToggleButton priority;

    private Task task;

    TaskView(View view) {
        view.setTag(this);
        title = Util.getView(view, taskTitle, TextView.class);
        description = Util.getView(view, taskDescription, TextView.class);
        deadline = Util.getView(view, taskDeadline, TextView.class);
        edit = Util.getView(view, taskEditButton, Button.class);
        status = Util.getView(view, statusButton, Button.class);
        priority = getView(view, priorityButton, ToggleButton.class);
        

        setOnClickListeners();
    }

    void fillInData(Task task) {
        this.task = task;
        title.setText(task.getTitle());
        description.setText(task.getDescription());
        if (task.getDeadline() != null) {
            deadline.setText(dateFormat.format(task.getDeadline()));
        }
        status.setText(task.getState().toString());
        if (task.getState() == TaskState.DONE) status.setEnabled(false);
        priority.setChecked(task.isPriority());

    }

    private void updateStatus(View v) {
        task.nextState();
        new TaskDAO(v.getContext()).updateTask(task);
        status.setText(task.getState().toString());
    }

    private void setOnClickListeners() {
        edit.setOnClickListener(new LaunchEditWindow());
        status.setOnClickListener(new UpdateStatus());
        priority.setOnCheckedChangeListener(new TogglePriority());
    }

    private class LaunchEditWindow implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final Context context = view.getContext();
            Intent intent = new Intent(context, TaskEditActivity.class);
            intent.putExtra(TASK_ID, task.getId());
            context.startActivity(intent);
        }
    }

    private class UpdateStatus implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            updateStatus(v);
        }
    }

    private class TogglePriority implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            buttonView.setText("");
            if (isChecked) {
                buttonView.setButtonDrawable(btn_star_big_on);
                task.setPriority(true);
            } else {
                buttonView.setButtonDrawable(btn_star_big_off);
                task.setPriority(false);
            }
        }
    }
}
