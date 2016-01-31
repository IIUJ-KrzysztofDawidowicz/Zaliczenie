package pl.edu.uj.andriod.Zaliczenie.sql;

import android.database.Cursor;

public class Cursors {

    public static String getString(Cursor cursor, TaskTable column){
        return cursor.getString(cursor.getColumnIndex(column.sqlName));
    }

    public static int getInt(Cursor cursor, TaskTable column){
        return cursor.getInt(cursor.getColumnIndex(column.sqlName));
    }

    public static long getLong(Cursor cursor, TaskTable column){
        return cursor.getLong(cursor.getColumnIndex(column.sqlName));
    }
    
    public static boolean getBoolean(Cursor cursor, TaskTable column){
        return getInt(cursor, column) != 0;
    }
}
