package com.example.todo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todo.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE from task_table")
    void deleteAllTasks();

    @Query("SELECT * from task_table ORDER BY date_col DESC")
    LiveData<List<Task>> getAllTasks();


    // other methods

    @Query("DELETE from task_table where id = :id")
    void deleteTask(int id);

    @Query("UPDATE task_table SET task_name_col = :taskNameText WHERE id = :id")
    int updateTask(int id, String taskNameText);

    @Query("SELECT * from task_table")
    int getTaskCount();


}
