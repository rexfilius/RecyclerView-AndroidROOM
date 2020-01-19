package com.example.todo.model;

public class Task {

    private String nameOfTask;
    private int timeDuration;
    private int id;
    private String dateTaskAdded;

    public Task() {
    }

    public Task(String nameOfTask, int timeDuration) {
        this.nameOfTask = nameOfTask;
        this.timeDuration = timeDuration;
    }

    public Task(String nameOfTask, int timeDuration, int id) {
        this.nameOfTask = nameOfTask;
        this.timeDuration = timeDuration;
        this.id = id;
    }

    public String getNameOfTask() {
        return nameOfTask;
    }

    public void setNameOfTask(String nameOfTask) {
        this.nameOfTask = nameOfTask;
    }

    public int getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(int timeDuration) {
        this.timeDuration = timeDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTaskAdded() {
        return dateTaskAdded;
    }

    public void setDateTaskAdded(String dateTaskAdded) {
        this.dateTaskAdded = dateTaskAdded;
    }
}
