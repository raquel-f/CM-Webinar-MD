package com.raquel.todoappdesign;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.raquel.todoappdesign.fragments.CreateTask;
import com.raquel.todoappdesign.fragments.EditTask;
import com.raquel.todoappdesign.fragments.TaskFragment;
import com.raquel.todoappdesign.viewmodel.Task;

public class MainActivity extends AppCompatActivity implements FragmentSwitcher {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainFrame_layout, TaskFragment.class,null)
                .commit();

    }

    @Override
    public void switchCreateTask() {
        CreateTask fragment = CreateTask.newInstance();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.mainFrame_layout, fragment, null)
                .commit();
    }

    @Override
    public void switchEditTask(Task task) {
        EditTask fragment = EditTask.newInstance(task);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.mainFrame_layout, fragment, null)
                .commit();
    }

    @Override
    public void switchTaskList() {
        TaskFragment fragment = TaskFragment.newInstance(1);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.mainFrame_layout, fragment, null)
                .commit();
    }
}