package pl.edu.uj.andriod.Zaliczenie.ui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import pl.edu.uj.andriod.Zaliczenie.file.DatabaseReader;
import pl.edu.uj.andriod.Zaliczenie.file.DatabaseWriter;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static pl.edu.uj.andriod.Zaliczenie.R.layout.import_export;

public class ImportExportActivity extends Activity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
    };
    private static final String FILENAME = "TasksDb.csv";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(import_export);
    }

    public void importDatabase(View v) {
        DatabaseReader.importDatabase(this, FILENAME);
    }

    public void exportDatabase(View v) {
        // Check if we have write permission
        int permission = checkSelfPermission(WRITE_EXTERNAL_STORAGE);

        if (permission != PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            DatabaseWriter.writeDatabaseToFile(this, FILENAME);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        DatabaseWriter.writeDatabaseToFile(this, FILENAME);
    }

}