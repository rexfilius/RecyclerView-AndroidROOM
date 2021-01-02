package com.example.todo.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todo.model.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskRoomDatabase extends RoomDatabase {

    public static volatile TaskRoomDatabase INSTANCE;
    public abstract TaskDao taskDao();

    public static TaskRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (TaskRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class, "task_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return  INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TaskDao taskDao;

        public PopulateDbAsync(TaskRoomDatabase db) {
            taskDao = db.taskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.insert(new Task("Task1", 1));
            taskDao.insert(new Task("Task2", 2));
            taskDao.insert(new Task("Task3", 3));
            return null;
        }
    }

}
