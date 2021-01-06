package com.example.todo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todo.R;
import com.example.todo.database.DatabaseHandler;
import com.example.todo.database.TaskDao;
import com.example.todo.database.Task;
import com.example.todo.util.TaskListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskListAdapter mTaskListAdapter;
    private List<Task> taskList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    private Button saveButton;
    private EditText taskName;
    private EditText timeDuration;
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);

        databaseHandler = new DatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //taskList = new ArrayList<>();
        //taskList = databaseHandler.getAllTask();
        //taskList = (List<Task>) taskDao.getAllTask();

        mTaskListAdapter = new TaskListAdapter(this);
        recyclerView.setAdapter(mTaskListAdapter);
        mTaskListAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopupDialog();
            }
        });
    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);

        taskName = view.findViewById(R.id.popup_name);
        timeDuration = view.findViewById(R.id.popup_time_duration);
        saveButton = view.findViewById(R.id.popup_save_button);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!taskName.getText().toString().isEmpty()
                        && !timeDuration.getText().toString().isEmpty()) {
                    saveTask(v);
                } else {
                    Snackbar.make(v, "Empty fields not allowed", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveTask(View view) {
        Task task = new Task();

        String newTaskName = taskName.getText().toString().trim();
        int newTimeDuration = Integer.parseInt(timeDuration.getText().toString().trim());

        task.setNameOfTask(newTaskName);
        task.setTimeDuration(newTimeDuration);
        //databaseHandler.addTask(task);
        taskDao.insert(task);
        Snackbar.make(view, "Item Saved", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
                startActivity(new Intent(ListActivity.this, ListActivity.class));
                finish();
            }
        }, 1200);
    }
}
