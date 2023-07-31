package com.bharathkrishna.whatsnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharathkrishna.whatsnext.R;
import com.bharathkrishna.whatsnext.Pojo.TaskHelper;

import java.util.List;

public class RecyclerviewAdaptor extends RecyclerView.Adapter<RecyclerviewAdaptor.MyViewHolder> {

    private Context context;
    private List<TaskHelper> tasklist;

    public RecyclerviewAdaptor(Context context, List<TaskHelper> tasklist) {
        this.context = context;
        this.tasklist = tasklist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_todolist_item, parent, false);
        return new MyViewHolder(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdaptor.MyViewHolder holder, int position) {
        TaskHelper taskHelper = tasklist.get(position);
        holder.task_title_textview.setText(taskHelper.getTask_title());
        holder.task_content_textview.setText(taskHelper.getTask());
    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView task_title_textview, task_content_textview;
        public ImageButton edit_button, delete_button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_title_textview =itemView.findViewById(R.id.task_title_textview);
            task_content_textview = itemView.findViewById(R.id.task_content_textview);

        }
    }
}
