# 🧠 Smart Todo List with JavaFX

A **desktop To-Do List application** built using **JavaFX** and **SceneBuilder**.  
This project helps users organize their daily tasks by allowing them to **create, edit, delete, categorize, and search** through their todos — all within a modern, interactive GUI.

---

## 🎯 Project Overview

**Objective:**  
To design and implement a **JavaFX-based To-Do List** with a **user-friendly GUI**, built entirely using **SceneBuilder**, and supported by **JSON data persistence**.

**Programming Language:** Java  
**Framework:** JavaFX  
**GUI Builder:** SceneBuilder  
**Data Storage:** JSON file (via Gson library)  
**Team Size:** 3 Members  
**Due Date:** 23 November 2025

---

## 👥 Team Members & Roles

| Member | Role | Responsibilities |
|:-------|:------|:----------------|
| 🧑‍💻 **Person A** | Core Logic & Data Persistence | Implement `Task` and `TaskManager` classes, handle JSON read/write operations, ensure data is saved and loaded correctly. |
| 🧑‍🎨 **Person B** | GUI Design (SceneBuilder) | Design all interface layouts using SceneBuilder, connect FXML components to controller, ensure visual consistency and usability. |
| 🧑‍🔬 **Person C** | Search, Filter & Integration | Implement task filtering and keyword search, connect GUI events with logic, perform integration testing and video demos. |

---

## 🧩 Features

### 🗂️ 1. Task Management (20%)
- Add, edit, delete, and mark tasks as complete.
- Each task includes:
    - Title
    - Description
    - Due Date
    - Category
    - Priority

### 🪟 2. Graphical User Interface (30%)
- Built **exclusively with SceneBuilder** (FXML).
- Includes:
    - `MenuBar` (File, Help, Exit)
    - `TableView` or `ListView` to display tasks
    - `DatePicker` for selecting due dates
    - `ComboBox` or `ChoiceBox` for category & priority
    - Buttons: Add, Edit, Delete, Mark Complete
- Responsive layout using:
    - `VBox`, `HBox`, `GridPane`, and `BorderPane`
- GUI design proof: 2-minute video showing GUI built from scratch.

### 🔍 3. Search & Filter (30%)
- **Filter by:**
    - Category
    - Due date
    - Completion status
- **Search by keyword:** (title or description)
- Implemented using:
    - `FilteredList`
    - `SortedList`
    - `Predicate<Task>`

### 💾 4. Data Persistence (20%)
- All tasks are stored in `tasks.json`.
- Data loads automatically on startup and saves on exit.
- Uses the **Gson** library for JSON serialization and deserialization.

---

## 🏗️ System Architecture

The project follows a simplified **MVC (Model–View–Controller)** structure for clarity and maintainability.

```text
┌───────────────────────────────────────────────────┐
│                     View                          │
│  (FXML files built in SceneBuilder)               │
│  → Handles layout, menus, buttons, task display   │
└───────────────────────────────────────────────────┘
              │
              ▼
┌───────────────────────────────────────────────────┐
│                  Controller                       │
│  (TodoController.java)                            │
│  → Connects GUI events to logic                   │
│  → Handles Add/Edit/Delete/Search actions         │
└───────────────────────────────────────────────────┘
              │
              ▼
┌───────────────────────────────────────────────────┐
│                    Model                          │
│  (Task.java, TaskManager.java)                    │
│  → Manages Task data and logic                    │
│  → Handles JSON read/write                        │
└───────────────────────────────────────────────────┘
```

## 📂 Folder Structure and Explanation
```text
SmartTodoList/
├── src/
│   ├── main/
│   │   ├── Main.java
│   │   │   → Entry point of the application. Initializes JavaFX and loads the FXML layout.
│   │   │
│   │   ├── controller/
│   │   │   └── TodoController.java
│   │   │       → Connects the GUI with the backend logic.
│   │   │       → Handles button clicks, search filters, and data updates.
│   │   │
│   │   ├── model/
│   │   │   ├── Task.java
│   │   │   │   → Represents a single to-do task (title, description, date, etc.)
│   │   │   │
│   │   │   └── TaskManager.java
│   │   │       → Manages the list of tasks (add, edit, delete, mark complete).
│   │   │       → Handles JSON file I/O using Gson.
│   │   │
│   │   └── service/
│   │       └── JsonStorage.java
│   │           → Handles saving/loading data to/from tasks.json.
│   │
│   └── resources/
│       ├── ui/
│       │   └── todo.fxml
│       │       → The FXML layout file built using SceneBuilder.
│       │       → Defines all visual components of the app.
│       │
│       ├── css/
│       │   └── style.css
│       │       → (Optional) Custom styling for buttons, fonts, and layout.
│       │
│       └── data/
│           └── tasks.json
│               → Stores the user’s saved to-do tasks in JSON format.
│
└── videos/
    ├── gui_build.mp4
    │   → 2-minute video showing the GUI creation process in SceneBuilder.
    └── full_demo.mp4
        → 5-minute video demonstrating all app features.
```

## ⚙️ How to run
Run:
```text
src/main/java/com.example.catasgn1/Launcher.java
```