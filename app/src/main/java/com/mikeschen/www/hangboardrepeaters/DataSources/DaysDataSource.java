package com.mikeschen.www.hangboardrepeaters.DataSources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mikeschen.www.hangboardrepeaters.Databases.MySQLiteHelper;
import com.mikeschen.www.hangboardrepeaters.Models.Days;

import java.util.ArrayList;
import java.util.List;

public class DaysDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_LOG };

    public DaysDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Days createLog(String log) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_LOG, log);
        long insertId = database.insert(MySQLiteHelper.TABLE_LOGS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_LOGS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Days newLog = cursorToLog(cursor);
        cursor.close();
        return newLog;
    }

    public void deleteLog(Days log) {
        long id = log.getId();
        System.out.println("Log deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_LOGS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Days> getAllLogs() {
        List<Days> logs = new ArrayList<Days>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_LOGS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Days log = cursorToLog(cursor);
            logs.add(log);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return logs;
    }

    private Days cursorToLog (Cursor cursor) {
        Days log = new Days();
        log.setId(cursor.getLong(0));
        log.setComment(cursor.getString(1));
        return log;
    }
}
