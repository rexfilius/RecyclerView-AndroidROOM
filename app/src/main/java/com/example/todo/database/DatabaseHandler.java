package com.example.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todo.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE ="CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_TASK + " TEXT,"
                + Constants.KEY_TIME_DURATION + "INTEGER,"
                + Constants.KEY_DATE_NAME + " LONG);";
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TASK, task.getNameOfTask());
        values.put(Constants.KEY_TIME_DURATION, task.getTimeDuration());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);
    }

    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                            Constants.KEY_TASK,
                            Constants.KEY_TIME_DURATION,
                            Constants.KEY_DATE_NAME},
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        Task task = new Task();
        if(cursor != null) {
            task.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                    Constants.KEY_ID))));
            task.setNameOfTask(cursor.getString(cursor.getColumnIndex(
                    Constants.KEY_TASK)));
            task.setTimeDuration(cursor.getInt(cursor.getColumnIndex(
                    Constants.KEY_TIME_DURATION)));

            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(
                    cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
            task.setDateTaskAdded(formattedDate);
        }
        return task;
    }

    public List<Task> getAllTask() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> taskList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                            Constants.KEY_TASK,
                            Constants.KEY_TIME_DURATION,
                            Constants.KEY_DATE_NAME},
                null, null, null, null,
                Constants.KEY_DATE_NAME + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(
                        Constants.KEY_ID))));
                task.setNameOfTask(cursor.getString(cursor.getColumnIndex(
                        Constants.KEY_TASK)));
                task.setTimeDuration(cursor.getInt(cursor.getColumnIndex(
                        Constants.KEY_TIME_DURATION)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(
                        cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());
                task.setDateTaskAdded(formattedDate);

                taskList.add(task);
            } while(cursor.moveToNext());
        }
        return taskList;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TASK, task.getNameOfTask());
        values.put(Constants.KEY_TIME_DURATION, task.getTimeDuration());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME, values, Constants.KEY_ID + "=?",
                        new String[]{String.valueOf(task.getId())});
    }

    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public int getTaskCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }
}
