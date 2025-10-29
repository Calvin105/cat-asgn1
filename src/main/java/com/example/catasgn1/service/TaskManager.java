package com.example.catasgn1.service;

import com.example.catasgn1.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskManager {

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }
}
