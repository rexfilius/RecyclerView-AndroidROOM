package com.example.todo.util;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.todo.data.TaskDao;
import com.example.todo.data.TaskRoomDatabase;
import com.example.todo.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;
    private LiveData<List<Task>> allTask;

    public TaskRepository(Application application) {
        TaskRoomDatabase db  = TaskRoomDatabase.getDatabase(application);
        taskDao= db.taskDao();
        allTask = taskDao.getAllTask();
    }

    public LiveData<List<Task>> getAllTask() {
        return allTask;
    }

    public void insert(Task task) {
        new InsertAsyncTask(taskDao).execute(task);
    }

    private class InsertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao asyncTaskDao;

        public InsertAsyncTask(TaskDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Task... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
