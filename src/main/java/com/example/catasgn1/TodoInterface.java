package com.example.catasgn1;


import com.example.catasgn1.model.Task;

import java.time.LocalDate;
import java.util.List;

public interface TodoInterface {
    public void addTask(Task task);

    public void removeTask(String id);

    public List<Task> getTasks();

    public boolean isEmpty();
}
