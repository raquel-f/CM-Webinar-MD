package com.raquel.todoappdesign.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.raquel.todoappdesign.FragmentSwitcher;
import com.raquel.todoappdesign.MainActivity;
import com.raquel.todoappdesign.R;
import com.raquel.todoappdesign.viewmodel.Task;
import com.raquel.todoappdesign.viewmodel.TaskViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class CreateTask extends Fragment {

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

        // date picker
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Final Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds());
        final MaterialDatePicker datePicker = builder.build();

        // get the input widgets
        TextView title = v.findViewById(R.id.create_title_input);
        TextView desc = v.findViewById(R.id.create_desc_input);

        // get the buttons
        Button cancelB = v.findViewById(R.id.create_cancel_button);
        Button createB = v.findViewById(R.id.create_create_button);
        Button dateB = v.findViewById(R.id.create_date_button);

        // end date for created task
        final Date[] newDate = new Date[1];

        // set date button listener
        dateB.setOnClickListener(view -> datePicker.show(getParentFragmentManager(), "DATE_PICKER"));

        // set date picker listener
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis((Long) selection);
            newDate[0] = new Date(calendar.getTimeInMillis());
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

            // create a new task
            Task newTask = new Task(newTitle, newDesc, newDate[0]);

            // add task to view model
            viewModel.addTaskTodo(newTask);

            // switch to list fragment
            fragmentSwitcher.switchTaskList();
        });

        return v;
    }

}