package com.example.todo.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.data.TaskDao;
import com.example.todo.model.Task;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Task> taskList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private TaskDao taskDao;

    public RecyclerViewAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Task task = taskList.get(position);

        viewHolder.taskName.setText(MessageFormat.format("Task: {0}",
                task.getNameOfTask()));
        viewHolder.timeDuration.setText(MessageFormat.format("Duration: {0}",
                String.valueOf(task.getTimeDuration())));
        viewHolder.dateAdded.setText(MessageFormat.format("Added on: {0}",
                task.getDateTaskAdded()));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
        //return taskDao.getTaskCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView taskName;
        public TextView timeDuration;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            taskName = itemView.findViewById(R.id.list_row_name);
            timeDuration = itemView.findViewById(R.id.list_row_time_duration);
            dateAdded = itemView.findViewById(R.id.list_row_date_added);
            editButton = itemView.findViewById(R.id.list_row_edit_button);
            deleteButton = itemView.findViewById(R.id.list_row_delete_button);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position;
            position = getAdapterPosition();
            Task task = taskList.get(position);

            switch (v.getId()) {
                case R.id.list_row_edit_button:
                    editTask(task);
                    break;
                case R.id.list_row_delete_button:
                    deleteTask(task.getId());
                    break;
            }
        }

        private void editTask(final Task newTask) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup, null);

            final EditText taskName;
            final EditText timeDuration;
            Button saveButton;
            TextView title;

            taskName = view.findViewById(R.id.popup_name);
            timeDuration = view.findViewById(R.id.popup_time_duration);
            title = view.findViewById(R.id.popup_title);

            title.setText(R.string.edit_popup_title);
            saveButton = view.findViewById(R.id.popup_save_button);
            saveButton.setText(R.string.update_save_button_text);

            taskName.setText(newTask.getNameOfTask());
            timeDuration.setText(String.valueOf(newTask.getTimeDuration()));

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DatabaseHandler databaseHandler = new DatabaseHandler(context);
                    newTask.setNameOfTask(taskName.getText().toString());
                    newTask.setTimeDuration(Integer.parseInt(
                            timeDuration.getText().toString()));

                    if(!taskName.getText().toString().isEmpty()
                    && !timeDuration.getText().toString().isEmpty()) {
                        //databaseHandler.updateTask(newTask);
                        taskDao.insert(newTask);
                        notifyItemChanged(getAdapterPosition(), newTask);
                    } else {
                        Snackbar.make(view, "Fields Empty", Snackbar.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });

        }

        private void deleteTask(final int id) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirm_popup, null);

            Button noButton = view.findViewById(R.id.confirm_popup_no_button);
            Button yesButton = view.findViewById(R.id.confirm_popup_yes_button);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DatabaseHandler db = new DatabaseHandler(context);
                    //db.deleteTask(id);
                    taskDao.deleteTask(id);
                    taskList.remove(getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());
                    dialog.dismiss();
                }
            });
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }
}
