package pl.edu.uj.andriod.Zaliczenie.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import pl.edu.uj.andriod.Zaliczenie.R;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.sql.TaskDAO;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public final class NewItemActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);
    }
    
    public void addItem(View view){
        new TaskDAO(getBaseContext()).addTask(createItem());
        end();
    }

    private void end() {
        startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), new Bundle());
    }

    private Task createItem() {
        String title = getTextFieldContent(R.id.titleText);
        String description = getTextFieldContent(R.id.descriptionText);
        Date deadline = null;
        try {
            deadline = DateFormat.getDateInstance().parse(getTextFieldContent(R.id.deadlineText));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Task(title, description).setDeadline(deadline);
    }

    private String getTextFieldContent(int id) {
        return ((TextView) findViewById(id)).getText().toString();
    }
}