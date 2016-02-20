package pl.edu.uj.andriod.Zaliczenie.file;

import android.content.Context;
import pl.edu.uj.andriod.Zaliczenie.model.Task;
import pl.edu.uj.andriod.Zaliczenie.sql.TaskDAO;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static android.os.Environment.*;
import static pl.edu.uj.andriod.Zaliczenie.file.DatabaseWriter.WriteStatus.NO_STORAGE_MOUNTED;
import static pl.edu.uj.andriod.Zaliczenie.file.DatabaseWriter.WriteStatus.SUCCESS;

public class DatabaseReader {
    public static DatabaseWriter.WriteStatus importDatabase(Context context, String filename) {
        File file = new File(getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), filename);
        if (!isReadable(file))
            return NO_STORAGE_MOUNTED;

        List<Task> tasks = readFile(file);

        new TaskDAO(context).replaceDatabase(tasks);
        
        return SUCCESS;
    }

    private static List<Task> readFile(File file) {
        List<Task> tasks = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                tasks.add(CSVTaskSerializer.parse(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private static boolean isReadable(File file) {
        String externalStorageState = getExternalStorageState(file);
        return MEDIA_MOUNTED.equals(externalStorageState) || MEDIA_MOUNTED_READ_ONLY.equals(externalStorageState);
    }
}
