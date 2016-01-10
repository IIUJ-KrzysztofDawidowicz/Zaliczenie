package pl.edu.uj.andriod.Zaliczenie.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.model.TaskState;
import pl.edu.uj.andriod.Zaliczenie.sql.TaskDAO;

import java.util.Calendar;
import java.util.Date;

import static android.R.layout.simple_list_item_1;
import static java.util.Calendar.*;
import static pl.edu.uj.andriod.Zaliczenie.R.id.*;
import static pl.edu.uj.andriod.Zaliczenie.R.layout.task_edit;

public class TaskEditActivity extends Activity {
    public static final String TASK_ID = "TASK_ID";
    private TaskDAO taskDAO;
    private EditText titleField;
    private EditText descriptionField;
    private Spinner stateField;
    private DatePicker deadlineField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(task_edit);
        initSpinner();

        initFields();
        fillInData();
    }

    private void initFields() {
        taskDAO = new TaskDAO(this);
        titleField = (EditText) findViewById(editTaskTitle);
        descriptionField = (EditText) findViewById(editTaskDescription);
        stateField = (Spinner) findViewById(editTaskState);
        deadlineField = (DatePicker) findViewById(editTaskDeadline);
    }

    private void fillInData() {
        Task task = taskDAO.getSingleTask(getIntent().getLongExtra(TASK_ID, -1));
        if (task == null) {
            Log.e("TaskEditActivity", "invalid task id");
            finish();
            return;
        }
        titleField.setText(task.getTitle());
        descriptionField.setText(task.getDescription());
        stateField.setSelection(task.getState().ordinal());
        if (task.getDeadline() != null) {
            final Calendar calendar = task.getDeadlineCalendar();
            deadlineField.updateDate(calendar.get(YEAR), calendar.get(MONTH), calendar.get(DAY_OF_MONTH));
        }
    }

    private void initSpinner() {
        Spinner editState = (Spinner) findViewById(editTaskState);
        editState.setAdapter(new ArrayAdapter<>(this, simple_list_item_1, TaskState.values()));
    }

    public void onClickOK(View v) {
        Task task = new Task(text(titleField), text(descriptionField))
                .setState((TaskState) stateField.getSelectedItem());
    }

    private String text(EditText field) {
        return field.getText().toString();
    }

}