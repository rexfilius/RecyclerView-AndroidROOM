package com.example.todo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "task_name_col")
    private String nameOfTask;

    @ColumnInfo(name = "time_duration_col")
    private int timeDuration;

    @ColumnInfo(name = "date_col")
    private String dateTaskAdded;

    public Task() {
    }

    @Ignore
    public Task(@NonNull String nameOfTask, int timeDuration) {
        this.nameOfTask = nameOfTask;
        this.timeDuration = timeDuration;
    }

//    @Ignore
//    public Task(@NonNull String nameOfTask, int timeDuration, int id) {
//        this.nameOfTask = nameOfTask;
//        this.timeDuration = timeDuration;
//        this.id = id;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfTask() {
        return nameOfTask;
    }

    public int getTimeDuration() {
        return timeDuration;
    }

    public String getDateTaskAdded() {
        return dateTaskAdded;
    }

    // Other Setters

    public void setNameOfTask(String nameOfTask) {
        this.nameOfTask = nameOfTask;
    }

    public void setTimeDuration(int timeDuration) {
        this.timeDuration = timeDuration;
    }

    public void setDateTaskAdded(String dateTaskAdded) {
        this.dateTaskAdded = dateTaskAdded;
    }
}
