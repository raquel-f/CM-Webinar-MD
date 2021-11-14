package com.raquel.todoappdesign.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.raquel.todoappdesign.FragmentSwitcher;
import com.raquel.todoappdesign.MainActivity;
import com.raquel.todoappdesign.R;
import com.raquel.todoappdesign.viewmodel.Task;
import com.raquel.todoappdesign.viewmodel.TaskViewModel;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class TaskFragment extends Fragment {

    private TaskViewModel viewModel;

    private FragmentSwitcher fragmentSwitcher;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private MyTaskRecyclerViewAdapter adapter;

    @SuppressWarnings("unused")
    public static TaskFragment newInstance(int columnCount) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskFragment() {
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

        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        // Get context
        Context context = view.getContext();

        // Get DrawerLayout
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);

        // Get the app toolbar
        Toolbar toolbar = ((MainActivity) requireActivity()).findViewById(R.id.toolbar);

        // Create the Drawer Toggle Button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(requireActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Get the Drawer
        NavigationView drawer = view.findViewById(R.id.navigationView);

        // Set the drawer listener
        drawer.setNavigationItemSelectedListener(item -> {
            List<Task> newList;

            // set the correct list
            if (item.getItemId() == R.id.todo) {
                newList = viewModel.getTodoTasks();
            } else if (item.getItemId() == R.id.doing) {
                newList = viewModel.getDoingTasks();
            } else if (item.getItemId() == R.id.done) {
                newList = viewModel.getDoneTasks();
            } else {
                return false;
            }

            // Change the ui
            adapter.setDataSet(newList);
            adapter.notifyDataSetChanged();

            // Close the drawer
            drawerLayout.closeDrawer(drawer);

            return true;
        });


        // Get recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // Create and set the recycler view adapter
        adapter = new MyTaskRecyclerViewAdapter(viewModel.getTodoTasks(), fragmentSwitcher, viewModel);
        recyclerView.setAdapter(adapter);

        // Set button onClick event listener
        FloatingActionButton button = view.findViewById(R.id.addTaskButton);
        button.setOnClickListener(view1 -> fragmentSwitcher.switchCreateTask());

        return view;
    }
}