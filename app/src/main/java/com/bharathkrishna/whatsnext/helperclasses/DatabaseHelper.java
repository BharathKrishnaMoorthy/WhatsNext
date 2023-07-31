package com.bharathkrishna.whatsnext.helperclasses;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bharathkrishna.whatsnext.Pojo.TaskHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "task_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaskHelper.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskHelper.TABLE_NAME);
    }


    // Read Task
    public TaskHelper getTask(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TaskHelper.TABLE_NAME,
                new String[]{TaskHelper.COLUMN_ID,TaskHelper.COLUMN_TASK_TITLE,TaskHelper.COLUMN_TASK, TaskHelper.COLUMN_TIMESTAMP},
                TaskHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        // prepare note object
        @SuppressLint("Range") TaskHelper task = new TaskHelper(cursor.getInt(cursor.getColumnIndex(TaskHelper.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(TaskHelper.COLUMN_TASK_TITLE)),
                cursor.getString(cursor.getColumnIndex(TaskHelper.COLUMN_TASK)),
                cursor.getString(cursor.getColumnIndex(TaskHelper.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return task;
    }

    @SuppressLint("Range")
    public List<TaskHelper> getAllTask(){
        List<TaskHelper> tasks = new ArrayList<>();
        String selectQuery =  "SELECT  * FROM " + TaskHelper.TABLE_NAME + " ORDER BY "+ TaskHelper.COLUMN_TIMESTAMP + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                TaskHelper task = new TaskHelper();
                task.setId(cursor.getInt(cursor.getColumnIndex(TaskHelper.COLUMN_ID)));
                task.setTask_title(cursor.getString(cursor.getColumnIndex(TaskHelper.COLUMN_TASK_TITLE)));
                task.setTask(cursor.getString(cursor.getColumnIndex(TaskHelper.COLUMN_TASK)));
                task.setTimestamp(cursor.getString(cursor.getColumnIndex(TaskHelper.COLUMN_TIMESTAMP)));
                tasks.add(task);
            }while (cursor.moveToNext());
        }
        db.close();
        return tasks;
    }

    public int getTasksCount(){
        String countQuery = " SELECT * FROM "+ TaskHelper.TABLE_NAME;
        SQLiteDatabase db =getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery , null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Insert task in to internal db
    public long insertTask (String task_title, String task){
        // get writable database as we want to write data
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskHelper.COLUMN_TASK_TITLE, task_title);
        values.put(TaskHelper.COLUMN_TASK, task);
        long id = db.insert(TaskHelper.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // Updating Note
    public int updateTask (TaskHelper task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskHelper.COLUMN_TASK_TITLE, task.getTask_title());
        values.put(TaskHelper.COLUMN_TASK, task.getTask());
        return  db.update(TaskHelper.TABLE_NAME, values, TaskHelper.COLUMN_ID + "=?", new String[] {String.valueOf(task.getId())});
    }

    //Deleting note
    public void deleteTask(TaskHelper task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TaskHelper.TABLE_NAME, TaskHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
        db.close();
    }
}
