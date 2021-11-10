package com.raquel.todoappdesign.fragments;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raquel.todoappdesign.FragmentSwitcher;
import com.raquel.todoappdesign.R;
import com.raquel.todoappdesign.databinding.FragmentTaskBinding;
import com.raquel.todoappdesign.viewmodel.Task;
import com.raquel.todoappdesign.viewmodel.TaskViewModel;

import java.util.List;
import java.util.Locale;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> mValues;
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    private final FragmentSwitcher fragmentSwitcher;
    private final TaskViewModel viewModel;

    public MyTaskRecyclerViewAdapter(List<Task> tasks, FragmentSwitcher fragmentSwitcher, TaskViewModel viewModel) {
        mValues = tasks;
        this.fragmentSwitcher = fragmentSwitcher;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        // set text seen in the list
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mDateView.setText(dateFormat.format(mValues.get(position).getEndDate()));
        holder.mDescriptionView.setText(mValues.get(position).getDescription());

        //hide description
        holder.mDescriptionView.setVisibility(View.GONE);

        // add click listener to each list item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView description = view.findViewById(R.id.task_description);

                if(description.getVisibility() == View.VISIBLE){
                    description.setVisibility(View.GONE);
                }else{
                    description.setVisibility(View.VISIBLE);
                }

            }
        });

        // used in the popup listener
        MyTaskRecyclerViewAdapter adapter = this;

        PopupMenu.OnMenuItemClickListener popupListener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                // which item was selected?
                if (menuItem.getItemId() == R.id.edit_task_item) {
                    // edit task
                    fragmentSwitcher.switchEditTask(holder.mItem);
                } else if (menuItem.getItemId() == R.id.delete_task_item) {
                    // delete task
                    viewModel.removeTask(holder.mItem);
                    adapter.notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                } else {
                    System.out.println("how did this happen?");
                }

                // consume the press
                return true;
            }
        };

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // create the popup
                PopupMenu popup = new PopupMenu(view.getContext(), view);

                // inflate with the created layout
                popup.getMenuInflater().inflate(R.menu.popupmenu, popup.getMenu());

                // set the listener
                popup.setOnMenuItemClickListener(popupListener);

                // make popup visible
                popup.show();

                // consume the press
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        //return 0 if there is no tasks yet
        if (mValues == null) {
            return 0;
        }
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitleView;
        public final TextView mDateView;
        public final TextView mDescriptionView;
        public Task mItem;

        public ViewHolder(FragmentTaskBinding binding) {
            super(binding.getRoot());
            mTitleView = binding.taskTitle;
            mDateView = binding.taskDate;
            mDescriptionView = binding.taskDescription;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mDateView.getText() + "'";
        }
    }
}