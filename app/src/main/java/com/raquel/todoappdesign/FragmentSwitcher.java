package com.raquel.todoappdesign;

import com.raquel.todoappdesign.viewmodel.Task;

public interface FragmentSwitcher {
    void switchCreateTask();
    void switchEditTask(Task task);
    void switchTaskList();
}
