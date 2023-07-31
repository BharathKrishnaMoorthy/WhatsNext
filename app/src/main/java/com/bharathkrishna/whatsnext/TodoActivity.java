package com.bharathkrishna.whatsnext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bharathkrishna.whatsnext.adapters.RecyclerviewAdaptor;
import com.bharathkrishna.whatsnext.helperclasses.DatabaseHelper;
import com.bharathkrishna.whatsnext.helperclasses.RecyclerTouchListener;
import com.bharathkrishna.whatsnext.Pojo.TaskHelper;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private static RecyclerviewAdaptor recyclerviewAdaptor;

    private static List<TaskHelper> taskHelperList = new ArrayList<>();
    private RecyclerView todo_recyclerview;
    private ImageButton create_Imagebutton;
    private static DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        todo_recyclerview = findViewById(R.id.todo_recyclerview);
        databaseHelper =new DatabaseHelper(this);
        taskHelperList.addAll(databaseHelper.getAllTask());
        create_Imagebutton = findViewById(R.id.create_Imagebutton);
        create_Imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create_Edit_Task(false, null, -1);
            }
        });

        recyclerviewAdaptor = new RecyclerviewAdaptor(this, taskHelperList);
        RecyclerView.LayoutManager layoutManager =new GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false);
        todo_recyclerview.setLayoutManager(layoutManager);
        todo_recyclerview.setItemAnimator(new DefaultItemAnimator());
        todo_recyclerview.setAdapter(recyclerviewAdaptor);

        todo_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, todo_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Create_Edit_Task(true, taskHelperList.get(position), position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }



    @SuppressLint("NotifyDataSetChanged")
    public static void createTask(String task_title, String task){
        long id =databaseHelper.insertTask(task_title, task);
        TaskHelper t = databaseHelper.getTask(id);
        if (t != null){
            taskHelperList.add(t);
            recyclerviewAdaptor.notifyDataSetChanged();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void updateTask(String task, int position){
        TaskHelper t =taskHelperList.get(position);
        t.setTask(task);
        databaseHelper.updateTask(t);
        recyclerviewAdaptor.notifyDataSetChanged();
    }
    static void deleteTask(int position){
        databaseHelper.deleteTask(taskHelperList.get(position));

        taskHelperList.remove(position);
        recyclerviewAdaptor.notifyDataSetChanged();
    }
    private void Create_Edit_Task(final boolean shouldUpdate, final TaskHelper task , final int position) {
        String task_title = "", task_content = "";
        if (shouldUpdate && task != null){
            task_title = task.getTask_title();
            task_content = task.getTask();
        }

        Intent create_edit_intent = new Intent(TodoActivity.this, CreateEditTask.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("task_title", task_title);
        bundle.putString("task_content", task_content);
        bundle.putBoolean("shouldUpdate",shouldUpdate);
        create_edit_intent.putExtras(bundle);
        startActivity(create_edit_intent);
    }
}