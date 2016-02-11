package pl.edu.uj.andriod.Zaliczenie.file;

import android.content.Context;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.sql.TaskDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import static android.os.Environment.*;
import static pl.edu.uj.andriod.Zaliczenie.file.DatabaseWriter.WriteStatus.NO_STORAGE_MOUNTED;
import static pl.edu.uj.andriod.Zaliczenie.file.DatabaseWriter.WriteStatus.SUCCESS;

public class DatabaseWriter {

    public static WriteStatus writeDatabaseToFile(Context context, String filename) {
        File file = new File(getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), filename);
        if (!MEDIA_MOUNTED.equals(getExternalStorageState(file)))
            return NO_STORAGE_MOUNTED;

        List<Task> tasks = new TaskDAO(context).getAllTasks();

        writeToFile(tasks, file);

        return SUCCESS;
    }

    private static void writeToFile(List<Task> tasks, File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Task task : tasks) {
                writer.print(CSVTaskSerializer.serialize(task));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public enum WriteStatus {
        SUCCESS, NO_STORAGE_MOUNTED
    }

}
