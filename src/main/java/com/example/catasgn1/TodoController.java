package com.example.catasgn1;

import com.example.catasgn1.model.Task;
import com.example.catasgn1.service.TaskManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

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
        categoryBox.getItems().addAll("Work", "Personal", "Study");
        priorityBox.getItems().addAll("Low", "Medium", "High");

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
