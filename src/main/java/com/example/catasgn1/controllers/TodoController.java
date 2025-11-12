package com.example.catasgn1.controllers;

import com.example.catasgn1.model.Task;
import com.example.catasgn1.model.TodoList;
import com.example.catasgn1.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class TodoController {

    @FXML
    private TextField fieldSearch;
    @FXML
    private ComboBox<Constants.TaskCategory> comboCategory;

    private final TodoList todoManager = new TodoList();

    @FXML
    private void initialize() {
        comboCategory.getItems().addAll(Constants.TaskCategory.values());
    }

    @FXML
    private void showTaskDialog(ActionEvent actionEvent) {
        try {
            // 1. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/catasgn1/ui/task-dialog.fxml"));
            Parent root = loader.load();

            // 2. Create the Stage for the pop-up
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Task Details");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            // 3. Get the owner window from the button that triggered the event
            // (Node)event.getSource() is the button that was clicked.
            Window mainWindow = ((Node)actionEvent.getSource()).getScene().getWindow();
            dialogStage.initOwner(mainWindow);

            // 4. Set the Scene and display
            dialogStage.setScene(new Scene(root));

            // OPTIONAL: Pass Stage to dialog controller
            AddTaskDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Use showAndWait() to display the dialog and block the main scene
            dialogStage.showAndWait();

            // Handle results here after dialog closes
            if (controller.isSaveClicked()) {
                Task newTask = controller.getResult();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
