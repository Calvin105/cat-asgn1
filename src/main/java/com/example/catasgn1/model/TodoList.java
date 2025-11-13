package com.example.catasgn1.model;

import com.example.catasgn1.interfaces.TodoInterface;
import com.example.catasgn1.utils.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoList implements TodoInterface {
    List<Task> tasks;
    static final String FILE_PATH ="src/main/resources/com/example/catasgn1/data/tasks.json";
    // Read write JSON file with LocalDateAdapter to serialise and deserialize the LocalDate string correctly
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .setPrettyPrinting()
        .create();

    public TodoList() {
        loadTasks();
        System.out.println("TodoList constructor");
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
        System.out.println("TodoList addTask");
        System.out.println("Task added: " + task.getTitle());
    }

    @Override
    public void removeTask(String id) {
        tasks.removeIf(task -> task.getId().equals(id));
        saveTasks();
        System.out.println("TodoList removeTask");
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public void updateTask(String id, Task task) {
        Task updatedTask = this.getTask(id);
        updatedTask.setTitle(task.getTitle());
        updatedTask.setDescription(task.getDescription());
        updatedTask.setCompleted(task.isCompleted());
        updatedTask.setPriority(task.getPriority());
        updatedTask.setDueDate(task.getDueDate());
        saveTasks();
        System.out.println("TodoList updateTask");
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getSize() {
        return tasks.size();
    }

    @Override
    public Task getTask(String id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
    }


    private void loadTasks() {
        try {
            // Read JSON file contained in a container object
            FileReader reader = new FileReader(FILE_PATH);
            TaskListWrapper wrapper = gson.fromJson(reader, TaskListWrapper.class);
            List<Task> loadedTasks = wrapper.getTasks();

            tasks = loadedTasks == null ? new ArrayList<>() : loadedTasks;
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveTasks() {
        try (FileWriter reader = new FileWriter(FILE_PATH)) {
            TaskListWrapper taskListWrapper = new TaskListWrapper(tasks);
            gson.toJson(taskListWrapper, reader);
        } catch (IOException e) {
            System.out.println("Error while reading file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
