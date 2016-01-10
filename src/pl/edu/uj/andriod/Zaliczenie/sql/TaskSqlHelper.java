package pl.edu.uj.andriod.Zaliczenie.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static pl.edu.uj.andriod.Zaliczenie.R.string.create_table;

final class TaskSqlHelper extends SQLiteOpenHelper {
    private static final String database = "Tasks.db";
    private static final int version = 1;
    private final Context context;
    
    public TaskSqlHelper(Context context) {
        super(context, database, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    private void createTable(SQLiteDatabase db) {
        db.execSQL(context.getResources().getString(create_table));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table tasks;");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
