package com.example.catasgn1.model;

import com.example.catasgn1.utils.Constants;
import com.example.catasgn1.interfaces.TaskInterface;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.UUID;

public class Task implements TaskInterface {
    private String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Constants.TaskCategory category;
    private Constants.TaskPriority priority;
    private boolean completed;

    public Task(
            String title,
            String description,
            LocalDate dueDate,
            Constants.TaskCategory category,
            Constants.TaskPriority priority
    ) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priority = priority;
        this.completed = false;
    }

    // Used only when reading JSON file, otherwise use the above constructor
    // to avoid ID duplication
    public Task(
            String id,
            String title,
            String description,
            LocalDate dueDate,
            Constants.TaskCategory category,
            Constants.TaskPriority priority
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.priority = priority;
        this.completed = false;
    }

    // Getters and setters
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public StringProperty categoryProperty() {return new SimpleStringProperty(category.toString());}
    public Constants.TaskCategory getCategory() { return category; }
    public void setCategory(Constants.TaskCategory category) { this.category = category; }

    public StringProperty priorityProperty() { return new SimpleStringProperty(priority.toString()); }
    public Constants.TaskPriority getPriority() { return priority; }
    public void setPriority(Constants.TaskPriority priority) { this.priority = priority; }

    // For TodoController, cell value factory
    public BooleanProperty completedProperty() { return new SimpleBooleanProperty(completed); }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed =  completed; }

    public String toString() {
        return "Task {" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", category='" + category + '\'' +
                ", priority='" + priority + '\'' +
                ", completed=" + completed +
                '}';
    }
}
