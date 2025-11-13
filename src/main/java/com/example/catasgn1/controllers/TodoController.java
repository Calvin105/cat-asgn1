package com.example.catasgn1.controllers;

import com.example.catasgn1.model.Task;
import com.example.catasgn1.model.TodoList;
import com.example.catasgn1.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class TodoController {
    // Search fields
    @FXML private TextField fieldSearch;
    @FXML private ComboBox<String> comboCategory;
    @FXML private ComboBox<String> comboPriority;
    @FXML private ComboBox<String> comboStatus;

    private String search;
    private String category;
    private String priority;
    private String status;

    // Table
    @FXML private TableView<Task> taskTableView;
    @FXML private TableColumn<Task, String> titleCol;
    @FXML private TableColumn<Task, String> descriptionCol;
    @FXML private TableColumn<Task, String> priorityCol;
    @FXML private TableColumn<Task, String> categoryCol;
    @FXML private TableColumn<Task, String> dueDateCol;
    @FXML private TableColumn<Task, Boolean> statusCol;

    ObservableList<Task> taskList = FXCollections.observableArrayList();
    private final TodoList todoManager = new TodoList();

    @FXML
    private void initialize() {
        comboCategory.getItems().addAll(
            Stream.concat(
                Arrays.stream(Constants.TaskCategory.values()).map(Enum::toString),
                Stream.of("None")
            ).toList()
        );
        comboPriority.setItems(FXCollections.observableArrayList(
            Stream.concat(
                Arrays.stream(Constants.TaskPriority.values()).map(Enum::toString),
                Stream.of("None")
            ).toList()
        ));
        comboStatus.getItems().addAll(new String[]{"Done", "Pending", "None"});

        // Initialize the ObservableList
        taskList.addAll(todoManager.getTasks());

        // 1. Link each column to a property in the Task class
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        priorityCol.setCellValueFactory(
            cellData -> cellData.getValue().priorityProperty()
        );
        categoryCol.setCellValueFactory(
            cellData -> cellData.getValue().categoryProperty()
        );
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusCol.setCellValueFactory(
            cellData -> cellData.getValue().completedProperty()
        );

        // Make the column editable
        statusCol.setCellFactory(CheckBoxTableCell.forTableColumn(statusCol));
        priorityCol.setCellFactory(ComboBoxTableCell.forTableColumn(Arrays.toString(Constants.TaskPriority.values())));

        // 2. Set the data source for the TableView
        taskTableView.setItems(taskList);
        taskTableView.setFixedCellSize(30);
        taskTableView.setEditable(true);
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
                todoManager.addTask(newTask);
                taskList.clear();
                taskList.addAll(todoManager.getTasks());
                taskTableView.refresh();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleFilterChange() {
        System.out.println("Search Field: " + search);
        System.out.println("Priority: " + priority);
        System.out.println("Status: " + status);
        System.out.println("Category: " + category + '\n');
    }

    public void handleKeyTyped(KeyEvent keyEvent) {
        // Every keystroke changes will be reflected here
        search = fieldSearch.getText();
        handleFilterChange();
    }

    public void handleComboPriority(ActionEvent actionEvent) {
        priority = comboPriority.getSelectionModel().getSelectedItem();
        handleFilterChange();
    }

    public void handleComboCategory(ActionEvent actionEvent) {
        category = comboCategory.getSelectionModel().getSelectedItem();
        handleFilterChange();
    }

    public void handleComboStatus(ActionEvent actionEvent) {
        status = comboStatus.getSelectionModel().getSelectedItem();
        handleFilterChange();
    }
}
