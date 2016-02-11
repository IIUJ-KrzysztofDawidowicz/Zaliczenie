package pl.edu.uj.andriod.Zaliczenie.file;

import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.model.TaskState;

import java.text.ParseException;
import java.util.Date;

import static java.lang.Boolean.parseBoolean;
import static pl.edu.uj.andriod.Zaliczenie.Util.dateFormat;


public class CSVTaskSerializer {
    static String serialize(Task task) {
        return String.format("%d,%s,%s,%s,%s,%s",
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getState(),
                task.isPriority(),
                dateFormat().format(task.getDeadline()));
    }

    static Task parse(String line) {
        String[] tokens = line.split(",");
        Long id = Long.valueOf(tokens[0]);
        String title = tokens[1];
        String description = tokens[2];
        TaskState state = TaskState.parse(tokens[3]);
        boolean priority = parseBoolean(tokens[4]);
        Date deadline = tryParse(tokens[5]);
        return new Task(title, description)
                .setId(id)
                .setState(state)
                .setPriority(priority)
                .setDeadline(deadline);
    }

    private static Date tryParse(String token) {
        try {
            return dateFormat().parse(token);
        } catch (ParseException e) {
            return null;
        }
    }
}
