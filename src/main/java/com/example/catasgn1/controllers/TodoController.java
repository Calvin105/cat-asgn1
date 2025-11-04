package com.example.catasgn1.controllers;

import com.example.catasgn1.utils.Constants;
import com.example.catasgn1.model.Task;
import com.example.catasgn1.service.TaskManager;
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

    private final TaskManager taskManager = new TaskManager();

    @FXML
    private void initialize() {
        // Initialize categoryBox using TaskCategory enum
        categoryBox.getItems().addAll(
            Arrays.stream(Constants.TaskCategory.values())
                .map(Enum::toString) // Convert enum constants to String (e.g., "WORK")
                .toList()
        );

        // Initialize priorityBox using TaskPriority enum
        priorityBox.getItems().addAll(
            Arrays.stream(Constants.TaskPriority.values())
                .map(Enum::toString) // Convert enum constants to String (e.g., "HIGH")
                .toList()
        );

        taskTable.setItems(taskManager.getTasks());
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
