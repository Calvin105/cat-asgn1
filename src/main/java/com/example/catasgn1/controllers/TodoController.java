package com.example.catasgn1.controllers;

import com.example.catasgn1.model.Task;
import com.example.catasgn1.model.TodoList;
import com.example.catasgn1.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TodoController {
    // Search fields
    @FXML private TextField fieldSearch;
    @FXML private ComboBox<String> comboCategory;
    @FXML private ComboBox<String> comboPriority;
    @FXML private ComboBox<String> comboStatus;

    @FXML private Button btnDeleteTask;

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

    private FilteredList<Task> filteredList;

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

        // 2. Set up the list
        // Wrap your master list in a FilteredList
        filteredList = new FilteredList<>(taskList, p -> true);  // p -> true means "show all"

        // Wrap the FilteredList in a SortedList for column sorting
        SortedList<Task> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(taskTableView.comparatorProperty());

        // 3. Set the data source for the TableView ( use the Soretdlist)
        taskTableView.setItems(sortedList);
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


    @FXML
    private void handleDeleteTask(ActionEvent actionEvent) {
        // 1. Get the task currently selected in the table
        Task selectedTask = taskTableView.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            // 2. Remove the task from the model (for saving to JSON)
            todoManager.removeTask(selectedTask.getId());

            // 3. Reload the task list to refresh the table
            // (Following the same pattern as your showTaskDialog)
            taskList.clear();
            taskList.addAll(todoManager.getTasks());
            taskTableView.refresh();
        } else {
            // Optional but recommended: Show a warning if no task is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Task Selected");
            alert.setContentText("Please select a task in the table to delete.");
            alert.showAndWait();
        }
    }

    private void handleFilterChange() {
        System.out.println("Search Field: " + search);
        System.out.println("Priority: " + priority);
        System.out.println("Status: " + status);
        System.out.println("Category: " + category + '\n');

        // 1. Start with a Predicate that shows everything
        Predicate<Task> combinedPredicate = p -> true;

        // 2. Add the Keyword Search filter
        if (search != null && !search.isEmpty()) {
            String lowerCaseSearch = search.toLowerCase();
            // Create a test for the search
            Predicate<Task> searchPredicate = task -> task.getTitle().toLowerCase().contains(lowerCaseSearch) || task.getDescription().toLowerCase().contains(lowerCaseSearch);
            // Add it to the combined test
            combinedPredicate = combinedPredicate.and(searchPredicate);
        }

        // 3. Add the Priority filter
        if (priority != null && !priority.equals("None")) {
            // Create a test for priority
            Predicate<Task> priorityPredicate = task -> task.getPriority() != null &&
                    task.getPriority().toString().equals(priority);
            // Add it to the combined test
            combinedPredicate = combinedPredicate.and(priorityPredicate);
        }

        // 4. Add the Category filter
        if (category != null && !category.equals("None")) {
            // Create a test for category
            Predicate<Task> categoryPredicate = task -> task.getCategory() != null &&
                    task.getCategory().toString().equals(category);
            // Add it to the combined test
            combinedPredicate = combinedPredicate.and(categoryPredicate);
        }

        // 5. Add the Status filter
        if (status != null && !status.equals("None")) {
            Predicate<Task> statusPredicate;
            if (status.equals("Done")){
                statusPredicate = task -> task.isCompleted();
            } else { // "Pending"
                statusPredicate = task -> !task.isCompleted();
            }
            // Add it to the combined test
            combinedPredicate = combinedPredicate.and(statusPredicate);
        }

        // 6. Finally, apply the combined filter to the list
        // The TableView will update automatically
        filteredList.setPredicate(combinedPredicate);

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
