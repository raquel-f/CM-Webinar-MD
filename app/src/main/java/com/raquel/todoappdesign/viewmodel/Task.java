package com.raquel.todoappdesign.viewmodel;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {

    private String title;           // task title
    private String description;     // task description
    private Status status;          // task status (TODO, DOING or DONE)
    private Date endDate;           // task end date

    // task constructor
    public Task(String title, String description, Date endDate) {
        this.title = title;
        this.description = description;
        this.status = Status.TODO;
        this.endDate = endDate;
    }

    // getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    // prints information in console
    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", endDate=" + endDate +
                '}';
    }
}
