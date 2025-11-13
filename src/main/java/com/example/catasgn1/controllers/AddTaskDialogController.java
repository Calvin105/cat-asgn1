package com.example.catasgn1.controllers;

import com.example.catasgn1.model.Task;
import com.example.catasgn1.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddTaskDialogController {

    private Stage dialogStage;
    private Task result;
    private boolean update = false;
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

    @FXML private Label titleLabel;

    // Fields
    @FXML private TextField taskTitleTextField;
    @FXML private TextArea taskDescriptionTextField;
    @FXML private ComboBox<Constants.TaskCategory> taskCategoryTextField;
    @FXML private ComboBox<Constants.TaskPriority> taskPriorityTextField;
    @FXML private DatePicker taskDueDateTextPicker;

    // Errors
    @FXML private Label errorTitle;
    @FXML private Label errorDescription;
    @FXML private Label errorCategory;
    @FXML private Label errorPriority;
    @FXML private Label errorDueDate;

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

        if (taskTitle == null || taskDescription == null || category == null || priority == null || dueDate == null) {
            validateField(taskTitle, errorTitle);
            validateField(taskDescription, errorDescription);
            validateField(category, errorCategory);
            validateField(priority, errorPriority);
            validateField(dueDate, errorDueDate);

            return;
        }

        this.isSaveClicked = true;
        this.result = new Task(taskTitle, taskDescription, dueDate, category, priority);

        dialogStage.close();
    }

    public void onCancelTask(ActionEvent actionEvent) {
        dialogStage.close();
    }

    private <T> void validateField(T fieldValue, Label errorMessage) {
        boolean isInvalid = fieldValue == null || (fieldValue instanceof String && ((String) fieldValue).isEmpty());
        errorMessage.setVisible(isInvalid);
        errorMessage.setStyle("-fx-text-fill: red;");
    }

    public void setTextField(String title, String description, Constants.TaskCategory category, Constants.TaskPriority priority, LocalDate dueDate) {
        taskTitleTextField.setText(title);
        taskDescriptionTextField.setText(description);
        taskCategoryTextField.setValue(category);
        taskPriorityTextField.setValue(priority);
        taskDueDateTextPicker.setValue(dueDate);
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
        if (update) titleLabel.setText("Edit Task");
    }
}
