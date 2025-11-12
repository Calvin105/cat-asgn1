package com.example.catasgn1.controllers;

import com.example.catasgn1.model.Task;
import com.example.catasgn1.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddTaskDialogController {

    private Stage dialogStage;
    private Task result;
    private boolean isSaveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Task getResult() { return this.result; }

    public boolean isSaveClicked() { return this.isSaveClicked; }

    public void handleOk() {
        dialogStage.close();
    }

    public void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private TextField taskTitleTextField;
    @FXML
    private TextArea taskDescriptionTextField;
    @FXML
    private ComboBox<Constants.TaskCategory> taskCategoryTextField;
    @FXML
    private ComboBox<Constants.TaskPriority> taskPriorityTextField;
    @FXML
    private DatePicker taskDueDateTextPicker;

    @FXML
    private void initialize() {
        taskCategoryTextField.getItems().addAll(Constants.TaskCategory.values());
        taskPriorityTextField.getItems().addAll(Constants.TaskPriority.values());
    }

    public void onAddTask(ActionEvent actionEvent) {
        String taskTitle = taskTitleTextField.getText();
        String taskDescription = taskDescriptionTextField.getText();
        Constants.TaskCategory category = taskCategoryTextField.getValue();
        Constants.TaskPriority priority = taskPriorityTextField.getValue();
        LocalDate dueDate = taskDueDateTextPicker.getValue();

        System.out.println("Title: " + taskTitle);
        System.out.println(taskDescription);
        System.out.println(category);
        System.out.println(priority);
        System.out.println(dueDate);

        this.isSaveClicked = true;
        this.result = new Task(taskTitle, taskDescription, dueDate, category, priority);

        dialogStage.close();
    }

    public void onCancelTask(ActionEvent actionEvent) {
        dialogStage.close();
    }
}
