# 📝 Smart Todo List with JavaFX

A desktop-based **To-Do List Application** built using **JavaFX** and **SceneBuilder**.  
This project allows users to create, edit, delete, and categorize their daily tasks with an intuitive, user-friendly interface.  
All data is saved in **JSON** format to ensure persistence between sessions.

---

## 🎯 Project Objectives

- Construct a software system using **Java** and **JavaFX**.
- Design a **user-friendly graphical interface** using **SceneBuilder**.
- Implement full task management features with **persistent data storage**.
- Work collaboratively as a **team of three** to design, implement, and demonstrate the project.

---

## 👥 Team Members

| Role | Name | Responsibility |
|------|------|----------------|
| **Person A** | [Name] | Core logic implementation, JSON storage, Task model |
| **Person B** | [Name] | GUI design using SceneBuilder, FXML connection, layout management |
| **Person C** | [Name] | Search & filter features, integration, testing, final demo |

---

## 🧩 Features

### 1. Task Management (20%)
- Add new tasks with fields:
    - **Title**
    - **Description**
    - **Due Date**
    - **Category**
    - **Priority**
- Edit and delete existing tasks.
- Mark tasks as **completed**.

### 2. Graphical User Interface (30%)
- Built **entirely in SceneBuilder** using FXML layout.
- Utilizes various **JavaFX layout panes**:
    - `BorderPane`, `HBox`, `VBox`, `GridPane`
- Displays tasks in a **TableView** or **ListView**.
- Includes:
    - `DatePicker` for due dates
    - `ComboBox` or `ChoiceBox` for category and priority
    - `MenuBar` with **File**, **Help**, and **Exit**
- Video evidence (2 mins) showing GUI creation in SceneBuilder is required.

### 3. Search and Filter (30%)
- **Filter tasks** by:
    - Category
    - Due date
    - Completion status
- **Search** tasks by keyword (e.g., title or description).
- Dynamic updates using JavaFX’s **FilteredList** and **ObservableList**.

### 4. Data Persistence / I/O (20%)
- Tasks are stored in a **JSON file** (e.g., `tasks.json`).
- Data automatically **loads at startup** and **saves on exit**.
- Uses libraries such as **Gson** or **Jackson** for JSON handling.

---

## 🏗️ System Design Overview

```text
+---------------------+
|   User Interface    |  ← Built with SceneBuilder (.fxml)
+---------------------+
           ↓
+---------------------+
|   Controller Layer  |  ← Handles UI events, connects to TaskManager
+---------------------+
           ↓
+---------------------+
|    TaskManager      |  ← Core logic: add/edit/delete/filter tasks
+---------------------+
           ↓
+---------------------+
|   JSON Data Store   |  ← Persists data using Gson/Jackson
+---------------------+
