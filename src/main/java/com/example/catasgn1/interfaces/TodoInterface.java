package com.example.catasgn1.interfaces;


import com.example.catasgn1.model.Task;

import java.util.List;

public interface TodoInterface {
    public void addTask(Task task);

    public void removeTask(String id);

    public List<Task> getTasks();

    public void updateTask(String id, Task task);

    public boolean isEmpty();

    public int getSize();

    public Task getTask(String id);
}
