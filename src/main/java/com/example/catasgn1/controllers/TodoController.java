package com.example.catasgn1.controllers;

import com.example.catasgn1.model.TodoList;
import com.example.catasgn1.utils.Constants;
import com.example.catasgn1.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Arrays;

public class TodoController {

    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private ComboBox<String> categoryBox;
    @FXML
    private ComboBox<String> priorityBox;
    @FXML
    private TableView<Task> taskTable;

    private final TodoList todoManager = new TodoList();

    @FXML
    private void initialize() {
        // Initialize categoryBox using TaskCategory enum
        categoryBox.getItems().addAll(
            Arrays.stream(Constants.TaskCategory.values())
                .map(Constants.TaskCategory::toCapitalized) // Convert enum constants to capitalised string (e.g., "Work")
                .toList()
        );

        // Initialize priorityBox using TaskPriority enum
        priorityBox.getItems().addAll(
            Arrays.stream(Constants.TaskPriority.values())
                .map(Constants.TaskPriority::toCapitalized) // Convert enum constants to capitalised string (e.g., "High")
                .toList()
        );

        ObservableList<Task> tasks = FXCollections.observableArrayList(todoManager.getTasks());
        taskTable.setItems(tasks);
    }

    @FXML
    private void onAddTask() {
//        Task task = new Task(
//                titleField.getText(),
//                descriptionField.getText(),
//                dueDatePicker.getValue(),
//                categoryBox.getValue(),
//                priorityBox.getValue()
//        );
//        taskManager.addTask(task);
//
//        titleField.clear();
//        descriptionField.clear();
//        dueDatePicker.setValue(LocalDate.now());
    }
}
