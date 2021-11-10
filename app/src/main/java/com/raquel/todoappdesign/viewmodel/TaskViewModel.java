package com.raquel.todoappdesign.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends ViewModel {

    private List<Task> todo;    // Tasks in TO-DO
    private List<Task> doing;   // Tasks in DOING
    private List<Task> done;    // Tasks in DONE

    // add task to to-do list
    public void addTaskTodo(Task task){
        if (todo == null) {
            todo = new ArrayList<>();
        }
        todo.add(task);

        // remove task from other lists
        if(doing != null) doing.remove(task);
        if(done != null) done.remove(task);
    }

    // add task to doing list
    public void addTaskDoing(Task task){
        if (doing == null) {
            doing = new ArrayList<>();
        }
        doing.add(task);

        // remove task from other lists
        if(todo != null) todo.remove(task);
        if(done != null) done.remove(task);
    }

    // add task to done list
    public void addTaskDone(Task task){
        if (done == null) {
            done = new ArrayList<>();
        }
        done.add(task);

        // remove task from other lists
        if(todo != null) todo.remove(task);
        if(doing != null) doing.remove(task);
    }

    // get all to-do tasks
    public List<Task> getTodoTasks(){
        return todo;
    }

    // get all doing tasks
    public List<Task> getDoingTasks(){
        return doing;
    }

    // get all done tasks
    public List<Task> getDoneTasks(){
        return done;
    }

    // remove task
    public void removeTask(Task task){
        // remove task from any list it might be in
        if(todo != null) todo.remove(task);
        if(doing != null) doing.remove(task);
        if(done != null) done.remove(task);
    }
}
