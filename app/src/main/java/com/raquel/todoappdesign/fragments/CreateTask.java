package com.raquel.todoappdesign.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.raquel.todoappdesign.FragmentSwitcher;
import com.raquel.todoappdesign.MainActivity;
import com.raquel.todoappdesign.R;
import com.raquel.todoappdesign.viewmodel.Task;
import com.raquel.todoappdesign.viewmodel.TaskViewModel;

import java.util.Calendar;
import java.util.Date;


public class CreateTask extends Fragment {

    // TODO test

    private TaskViewModel viewModel;
    private FragmentSwitcher fragmentSwitcher;

    public CreateTask() {
        // Required empty public constructor
    }


    public static CreateTask newInstance() {
        return new CreateTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the view model variable
        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);

        // initialize the interface
        if (getActivity() instanceof MainActivity) {
            fragmentSwitcher = (FragmentSwitcher) getActivity();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_task, container, false);

        // get the input widgets
        TextView title = v.findViewById(R.id.create_title_input);
        TextView desc = v.findViewById(R.id.create_desc_input);
        CalendarView date = v.findViewById(R.id.create_date_input);

        // get the buttons
        Button cancelB = v.findViewById(R.id.create_cancel_button);
        Button createB = v.findViewById(R.id.create_create_button);

        // set calendar change listener
        date.setOnDateChangeListener((view, year, month, day) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            long endTime = c.getTimeInMillis();
            date.setDate(endTime);
        });

        // set the button's listeners
        cancelB.setOnClickListener(view -> {
            // switch to list fragment
            fragmentSwitcher.switchTaskList();
        });

        createB.setOnClickListener(view -> {
            // get the input
            String newTitle = title.getText().toString();
            String newDesc = desc.getText().toString();
            Date newDate = new Date(date.getDate());

            // create a new task
            Task newTask = new Task(newTitle, newDesc, newDate);

            // add task to view model
            viewModel.addTaskTodo(newTask);

            // switch to list fragment
            fragmentSwitcher.switchTaskList();
        });

        return v;
    }

}