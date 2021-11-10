package com.raquel.todoappdesign.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.raquel.todoappdesign.FragmentSwitcher;
import com.raquel.todoappdesign.MainActivity;
import com.raquel.todoappdesign.R;
import com.raquel.todoappdesign.viewmodel.Status;
import com.raquel.todoappdesign.viewmodel.Task;
import com.raquel.todoappdesign.viewmodel.TaskViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EditTask extends Fragment {

    // TODO test

    private static final String TASK_KEY = "task_key";
    private Task task;

    private TaskViewModel viewModel;
    private FragmentSwitcher fragmentSwitcher;

    public EditTask() {
        // Required empty public constructor
    }

    public static EditTask newInstance(Task task) {
        EditTask fragment = new EditTask();
        Bundle args = new Bundle();
        args.putSerializable(TASK_KEY, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = (Task) getArguments().getSerializable(TASK_KEY);
        }
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
        View v = inflater.inflate(R.layout.fragment_edit_task, container, false);

        // get the task information
        String title = task.getTitle();
        String desc = task.getDescription();
        Status status = task.getStatus();
        Date date = task.getEndDate();
        List<Task> modelTasks = null;

        // get the input widgets
        TextView titleView = v.findViewById(R.id.edit_title_input);
        TextView descView = v.findViewById(R.id.edit_desc_input);
        RadioGroup radioGroup = v.findViewById(R.id.edit_status_input);
        RadioButton radioTODO = v.findViewById(R.id.edit_status_TODO);
        RadioButton radioDOING = v.findViewById(R.id.edit_status_DOING);
        RadioButton radioDONE = v.findViewById(R.id.edit_status_DONE);
        CalendarView calendarView = v.findViewById(R.id.edit_date_input);
        Button cancelB = v.findViewById(R.id.edit_cancel_button);
        Button editB = v.findViewById(R.id.edit_edit_button);

        // set calendar change listener
        calendarView.setOnDateChangeListener((view, year, month, day) -> {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            long endTime = c.getTimeInMillis();
            calendarView.setDate(endTime);
        });

        // apply the task information in the input widgets
        titleView.setText(title);
        descView.setText(desc);
        switch(status){
            case TODO:
                radioTODO.setChecked(true);
                modelTasks = viewModel.getTodoTasks();
                break;
            case DOING:
                radioDOING.setChecked(true);
                modelTasks = viewModel.getDoingTasks();
                break;
            case DONE:
                radioDONE.setChecked(true);
                modelTasks = viewModel.getDoneTasks();
                break;
            default:
                break;
        }
        calendarView.setDate(date.getTime());

        // set the button's listeners
        cancelB.setOnClickListener(view -> {
            // switch to list fragment
            fragmentSwitcher.switchTaskList();
        });

        List<Task> finalModelTasks = modelTasks;
        editB.setOnClickListener(view -> {
            // get the new task information
            String newTitle = titleView.getText().toString();
            String newDesc = descView.getText().toString();

            int selectedRadioID = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadio = v.findViewById(selectedRadioID);

            Date newDate = new Date(calendarView.getDate());

            // update task main information
            task.setTitle(newTitle);
            task.setDescription(newDesc);
            task.setEndDate(newDate);


            // update the task status and list
            switch (selectedRadio.getText().toString()){
                case "To Do":
                    if(status != Status.TODO) {
                        task.setStatus(Status.TODO);
                        viewModel.addTaskTodo(task);
                    }
                    break;
                case "Doing":
                    if(status != Status.DOING) {
                        task.setStatus(Status.DOING);
                        viewModel.addTaskDoing(task);
                    }
                    break;
                case "Done":
                    if(status != Status.DONE) {
                        task.setStatus(Status.DONE);
                        viewModel.addTaskDone(task);
                    }
                    break;
                default:
                    break;
            }

            // switch to list fragment
            fragmentSwitcher.switchTaskList();
        });

        return v;
    }
}