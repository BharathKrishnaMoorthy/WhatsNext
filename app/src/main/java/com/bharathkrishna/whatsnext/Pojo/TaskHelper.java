package com.bharathkrishna.whatsnext.Pojo;

import android.os.Parcelable;

public class TaskHelper{

    public static final String TABLE_NAME = "whats_next";
    public static final String COLUMN_ID = "id";

    public static final String COLUMN_TASK_TITLE = "title";
    public static final String COLUMN_TASK = "task";

    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;

    private String task_title;
    private String task;

    private String timestamp;

    public static final String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
       + COLUMN_TASK_TITLE + " TEXT, " + COLUMN_TASK + " TEXT, "  + COLUMN_TIMESTAMP +" DATETIME DEFAULT CURRENT_TIMESTAMP " +")";

    public TaskHelper() {
    }
    public  TaskHelper(int id, String task_title, String  task, String timestamp){
        this.id = id;
        this.task_title = task_title;
        this.task = task;
        this.timestamp = timestamp;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
