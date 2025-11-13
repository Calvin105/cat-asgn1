package com.example.catasgn1.controllers;

import com.example.catasgn1.model.Task;
import com.example.catasgn1.model.TodoList;
import com.example.catasgn1.utils.Constants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TodoController {
    @FXML private BorderPane rootPane;

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
    @FXML private TableColumn<Task, String> actionCol;

    ObservableList<Task> taskList = FXCollections.observableArrayList();
    private final TodoList todoManager = new TodoList();

    private FilteredList<Task> filteredList;

    @FXML
    private void initialize() {
        setupComboBoxValues();
        setupTable();
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

            // Pass Stage to dialog controller
            AddTaskDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Use showAndWait() to display the dialog and block the main scene
            dialogStage.showAndWait();

            // Handle results here after dialog closes
            if (controller.isSaveClicked()) {
                Task newTask = controller.getResult();

                // Update table
                todoManager.addTask(newTask);
                refreshTable();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ----------- INITIALIZATION -----------
    private void setupComboBoxValues() {
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
        comboStatus.getItems().addAll("Done", "Pending", "None");
    }

    private void setupTable() {
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
        statusCol.setCellValueFactory(
                cellData -> cellData.getValue().completedProperty()
        );
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        Callback<TableColumn<Task, String>, TableCell<Task, String>> actionCellFactory = (TableColumn<Task, String> _) -> new TableCell<Task, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                // Create cell on non-empty row
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    // Create delete and edit icons, style it
                    FontIcon deleteIcon = new FontIcon(FontAwesomeSolid.TRASH);
                    deleteIcon.setIconSize(24);
                    deleteIcon.setIconColor(Color.RED);
                    deleteIcon.setCursor(Cursor.HAND);

                    FontIcon editIcon = FontIcon.of(FontAwesomeSolid.PEN_SQUARE);
                    editIcon.setIconSize(24);
                    editIcon.setIconColor(Color.GREEN);
                    editIcon.setCursor(Cursor.HAND);

                    // Then set listeners on those icons
                    deleteIcon.setOnMouseClicked(_ -> {
                        Task currentTask = taskTableView.getSelectionModel().getSelectedItem();
                        todoManager.removeTask(currentTask.getId());
                        refreshTable();
                    });

                    editIcon.setOnMouseClicked(event -> {
                        Task currentTask = taskTableView.getSelectionModel().getSelectedItem();
                        String originalID = currentTask.getId();

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
                            Window mainWindow = ((Node)event.getSource()).getScene().getWindow();
                            dialogStage.initOwner(mainWindow);

                            // 4. Set the Scene and display
                            dialogStage.setScene(new Scene(root));

                            // Pass Stage to dialog controller
                            AddTaskDialogController controller = loader.getController();
                            controller.setDialogStage(dialogStage);
                            controller.setUpdate(true);
                            controller.setTextField(currentTask.getTitle(), currentTask.getDescription(), currentTask.getCategory(), currentTask.getPriority(), currentTask.getDueDate());

                            // Use showAndWait() to display the dialog and block the main scene
                            dialogStage.showAndWait();

                            // Handle results here after dialog closes
                            if (controller.isSaveClicked()) {
                                Task updatedTask = controller.getResult();
                                todoManager.updateTask(originalID, updatedTask);
                                refreshTable();
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    // Join them together with HBox
                    HBox manageBtn = new HBox(editIcon, deleteIcon);
                    manageBtn.setAlignment(Pos.CENTER);
                    HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                    HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                    setGraphic(manageBtn);

                    setText(null);
                }
            }
        };

        Callback<TableColumn<Task, Boolean>, TableCell<Task, Boolean>> statusCellFactory = (TableColumn<Task, Boolean> _) -> new TableCell<Task, Boolean>() {
            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Task currentTask = getTableView().getItems().get(getIndex());

                    FontIcon completedIcon = new FontIcon(FontAwesomeSolid.CHECK_CIRCLE);
                    completedIcon.setIconSize(24);
                    completedIcon.setIconColor(Color.GREEN);
                    completedIcon.setCursor(Cursor.HAND);

                    FontIcon incompleteIcon = new FontIcon(FontAwesomeRegular.CIRCLE); // Note the use of Regular for the outline
                    incompleteIcon.setIconSize(24);
                    incompleteIcon.setIconColor(Color.GRAY);
                    incompleteIcon.setCursor(Cursor.HAND);

                    FontIcon taskStatusIcon = item ? completedIcon : incompleteIcon;

                    // Add a click listener to the icon
                    taskStatusIcon.setOnMouseClicked(event -> {
                        // Determine the new state
                        boolean newCompletedStatus = !currentTask.isCompleted();

                        // Update the task object
                        currentTask.setCompleted(newCompletedStatus);

                        // Update the model (database/manager)
                        todoManager.updateTask(currentTask.getId(), currentTask);

                        // Update the displayed graphic immediately
                        if (newCompletedStatus) {
                            setGraphic(completedIcon);
                        } else {
                            setGraphic(incompleteIcon);
                        }
                    });

                    setAlignment(Pos.CENTER);
                    setGraphic(taskStatusIcon);
                    setText(null);
                }
            }
        };

            // Make the column editable
        actionCol.setCellFactory(actionCellFactory);
        statusCol.setCellFactory(statusCellFactory);
        priorityCol.setCellFactory(ComboBoxTableCell.forTableColumn(Arrays.toString(Constants.TaskPriority.values())));

        // 2. Set up the list
        // Wrap your master list in a FilteredList
        filteredList = new FilteredList<>(taskList, p -> true);  // p -> true means "show all"

        // Wrap the FilteredList in a SortedList for column sorting
        SortedList<Task> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(taskTableView.comparatorProperty());

        // 3. Set the data source for the TableView ( use the Sortedlist)
        taskTableView.setItems(sortedList);
        taskTableView.setFixedCellSize(30);
        taskTableView.setEditable(true);
    }

    private void refreshTable() {
        taskList.clear();
        taskList.addAll(todoManager.getTasks());
        taskTableView.refresh();
    }

    private void handleFilterChange() {
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

    public void onExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onAbout(ActionEvent actionEvent) {
        // 1. Create a new Stage (Window) for the dialog
        final Stage dialogStage = new Stage();

        // Set properties for the dialog window
        dialogStage.setTitle("About ToDo Manager");
        // Make it modal (user must close it before interacting with main window)
        dialogStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        // 2. Create the content (Labels and layout)
        Label titleLabel = new Label("CAT201 ToDo List App");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label teamHeader = new Label("Project Team Members");
        teamHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 0 0 0;");

        Label purposeLabel = new Label("Course Assignment: CAT201");
        Label member1 = new Label("1. Calvin Khoo Zhen Chen");
        Label member2 = new Label("2. Ch'ng Bao Sheng");
        Label member3 = new Label("3. Pik Yun Han");

        // 3. Arrange the content in a VBox
        VBox root = new VBox(10); // 10 is the spacing between elements
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(titleLabel, purposeLabel, teamHeader, member1, member2, member3);

        // 4. Create the Scene and set it on the Stage
        Scene dialogScene = new Scene(root, 300, 200);
        dialogStage.setScene(dialogScene);

        // Optional: Prevent resizing
        dialogStage.setResizable(false);

        // 5. Show the dialog and wait for it to be closed
        dialogStage.showAndWait();
    }

    public void onNew(ActionEvent actionEvent) {
        try {
            // 1. Load the FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/catasgn1/ui/task-dialog.fxml"));
            Parent root = loader.load();

            // 2. Create the Stage for the pop-up
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Task Details");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            // 3. Get the owner window from the button that triggered the event
            Window mainWindow = rootPane.getScene().getWindow();
            dialogStage.initOwner(mainWindow);

            // 4. Set the Scene and display
            dialogStage.setScene(new Scene(root));

            // Pass Stage to dialog controller
            AddTaskDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Use showAndWait() to display the dialog and block the main scene
            dialogStage.showAndWait();

            // Handle results here after dialog closes
            if (controller.isSaveClicked()) {
                Task newTask = controller.getResult();

                // Update table
                todoManager.addTask(newTask);
                refreshTable();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
