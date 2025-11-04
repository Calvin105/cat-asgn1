package com.example.catasgn1.model;

import com.example.catasgn1.TodoInterface;
import com.example.catasgn1.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoList implements TodoInterface {
    List<Task> tasks;
    static final String FILE_PATH ="src/main/resources/com/example/catasgn1/data/tasks.json";
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
        System.out.println("TodoList addTask");
        System.out.println("Task added: " + task.getTitle());
    }

    @Override
    public void removeTask(String id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private void loadTasks() {
        try {
            Type listType = new TypeToken<List<Task>>() {}.getType();
            String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            List<Task> loadedTasks = gson.fromJson(root.get("tasks"), listType);

            tasks = loadedTasks == null ? new ArrayList<Task>() : loadedTasks;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveTasks() {
        try (FileWriter reader = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, reader);
        } catch (IOException e) {
            System.out.println("Error while reading file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
