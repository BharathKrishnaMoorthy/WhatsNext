package com.bharathkrishna.whatsnext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bharathkrishna.whatsnext.Pojo.TaskHelper;

public class CreateEditTask extends AppCompatActivity {
    EditText title_edittext, task_edittext;
    Button delete_button, save_button;

    Bundle bundle;
    Boolean shouldUpdate;
    int position;

    String task_title, task_content;
    TaskHelper task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_task);
        title_edittext = findViewById(R.id.title_edittext);
        task_edittext = findViewById(R.id.task_edittext);
        delete_button = findViewById(R.id.delete_button);
        save_button = findViewById(R.id.save_button);
        bundle = getIntent().getExtras();
        shouldUpdate = bundle.getBoolean("shouldUpdate");
        position = bundle.getInt("position");
        task_title = bundle.getString("task_title");
        task_content = bundle.getString("task_content");
        todo_task();
    }

    private void todo_task() {
        if (shouldUpdate){
            title_edittext.setText(task_title);
            task_edittext.setText(task_content);
        }else {
            delete_button.setVisibility(View.INVISIBLE);
        }
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(task_edittext.getText().toString())){
                    Toast.makeText(CreateEditTask.this, "Enter note!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (shouldUpdate){
                    TodoActivity.updateTask(task_edittext.getText().toString(), position);
                    finish();
                }else {
                    TodoActivity.createTask(title_edittext.getText().toString() ,task_edittext.getText().toString());
                    finish();
                }
            }

        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoActivity.deleteTask(position);
                finish();
            }
        });
    }
}