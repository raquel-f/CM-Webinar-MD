package com.raquel.todoappdesign.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.raquel.todoappdesign.FragmentSwitcher;
import com.raquel.todoappdesign.MainActivity;
import com.raquel.todoappdesign.R;
import com.raquel.todoappdesign.viewmodel.Status;
import com.raquel.todoappdesign.viewmodel.Task;
import com.raquel.todoappdesign.viewmodel.TaskViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class EditTask extends Fragment {

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

        // date picker
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Final Date")
                .setSelection(date.getTime());
        final MaterialDatePicker datePicker = builder.build();

        // get the input widgets
        TextView titleView = v.findViewById(R.id.edit_title_input);
        TextView descView = v.findViewById(R.id.edit_desc_input);
        RadioGroup radioGroup = v.findViewById(R.id.edit_status_input);
        RadioButton radioTODO = v.findViewById(R.id.edit_status_TODO);
        RadioButton radioDOING = v.findViewById(R.id.edit_status_DOING);
        RadioButton radioDONE = v.findViewById(R.id.edit_status_DONE);
        Button cancelB = v.findViewById(R.id.edit_cancel_button);
        Button editB = v.findViewById(R.id.edit_edit_button);
        Button dateB = v.findViewById(R.id.edit_date_label);

        // end date for task
        final Date[] selectedDate = new Date[1];

        // set date button listener
        dateB.setOnClickListener(view -> datePicker.show(getParentFragmentManager(), "DATE_PICKER"));

        // set date picker listener
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis((Long) selection);
            selectedDate[0] = new Date(calendar.getTimeInMillis());
        });

        // apply the task information in the input widgets
        titleView.setText(title);
        descView.setText(desc);
        switch (status) {
            case TODO:
                radioTODO.setChecked(true);
                break;
            case DOING:
                radioDOING.setChecked(true);
                break;
            case DONE:
                radioDONE.setChecked(true);
                break;
            default:
                break;
        }

        // set the button's listeners
        cancelB.setOnClickListener(view -> {
            // switch to list fragment
            fragmentSwitcher.switchTaskList();
        });

        editB.setOnClickListener(view -> {
            // get the new task information
            String newTitle = titleView.getText().toString();
            String newDesc = descView.getText().toString();

            int selectedRadioID = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadio = v.findViewById(selectedRadioID);

            Date newDate = selectedDate[0];

            // update task main information
            task.setTitle(newTitle);
            task.setDescription(newDesc);
            task.setEndDate(newDate);


            // update the task status and list
            switch (selectedRadio.getText().toString()) {
                case "To Do":
                    if (status != Status.TODO) {
                        task.setStatus(Status.TODO);
                        viewModel.addTaskTodo(task);
                    }
                    break;
                case "Doing":
                    if (status != Status.DOING) {
                        task.setStatus(Status.DOING);
                        viewModel.addTaskDoing(task);
                    }
                    break;
                case "Done":
                    if (status != Status.DONE) {
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