package pl.edu.uj.andriod.Zaliczenie.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import pl.edu.uj.andriod.Zaliczenie.model.Task;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static pl.edu.uj.andriod.Zaliczenie.R.layout.task;

final class TaskListAdapter extends ArrayAdapter<Task> {
    private final LayoutInflater inflater;

    public TaskListAdapter(Context context, List<Task> tasks) {
        super(context, task, tasks);
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(task, null);
        }
        TaskView taskView = new TaskView(convertView, getContext());
        taskView.fillInData(getItem(position));
        return convertView;
    }
}
