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
import static pl.edu.uj.andriod.Zaliczenie.R.id.*;
import static pl.edu.uj.andriod.Zaliczenie.R.layout.task_edit;
import static pl.edu.uj.andriod.Zaliczenie.Util.getView;

public class TaskEditActivity extends Activity {
    public static final String TASK_ID = "TASK_ID";
    public static final String IS_NEW_TASK = "IS_NEW_TASK";
    private TaskDAO taskDAO;
    private EditText titleField;
    private EditText descriptionField;
    private Spinner stateField;
    private CalendarView deadlineField;
    private Date deadline = null;
    private final Calendar calendar = Calendar.getInstance();
    private Long taskId = null;
    private EditType editType;
    private CheckBox priority;

    private enum EditType {
        EDIT, CREATE
    }

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
        titleField = getView(this, editTaskTitle, EditText.class);
        descriptionField = getView(this, editTaskDescription, EditText.class);
        stateField = getView(this, editTaskState, Spinner.class);
        deadlineField = getView(this, editTaskDeadline, CalendarView.class);
        deadlineField.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                deadline = calendar.getTime();
            }
        });
        priority = getView(this, editPriority, CheckBox.class);
    }

    private void fillInData() {
        if (getIntent().getBooleanExtra(IS_NEW_TASK, false)) {
            editType = EditType.CREATE;
            return;
        }
        editType = EditType.EDIT;
        taskId = getIntent().getLongExtra(TASK_ID, -1);
        Task task = taskDAO.getSingleTask(taskId);
        if (task == null) {
            Log.e("TaskEditActivity", "invalid task id");
            finish();
            return;
        }
        titleField.setText(task.getTitle());
        descriptionField.setText(task.getDescription());
        stateField.setSelection(task.getState().ordinal());
        if (task.getDeadline() != null) {
            deadline = task.getDeadline();
            deadlineField.setDate(deadline.getTime());
        }
        priority.setChecked(task.isPriority());
    }

    private void initSpinner() {
        Spinner editState = (Spinner) findViewById(editTaskState);
        editState.setAdapter(new ArrayAdapter<>(this, simple_list_item_1, TaskState.values()));
    }

    public void onClickOK(View v) {
        final Task data = collectDataFromFields();
        switch (editType) {
            case EDIT:
                taskDAO.updateTask(data);
                break;
            case CREATE:
                taskDAO.addTask(data);
                break;
        }
        finish();
    }

    private Task collectDataFromFields() {
        return new Task(text(titleField), text(descriptionField))
                .setId(taskId)
                .setState((TaskState) stateField.getSelectedItem())
                .setDeadline(deadline)
                .setPriority(priority.isChecked());
    }

    private String text(EditText field) {
        return field.getText().toString();
    }

}