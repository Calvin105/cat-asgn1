package com.example.catasgn1;

import com.example.catasgn1.model.Task;
import com.example.catasgn1.model.TodoList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TodoList todoList = new TodoList();
        // R - Read the TodoList
        System.out.println("READ Operation");
        printTodoList(todoList);

        // C - Create a task and add into the TodoList
        System.out.println("CREATE Operation");
        todoList.addTask(new Task(
            "Submit Tax Documents",
            "Gather and submit all required tax documents for Q4 filing.",
            LocalDate.of(2025, 11, 15),
            Constants.TaskCategory.FINANCE,
            Constants.TaskPriority.HIGH
        ));
        printTodoList(todoList);

        // U - Update an attribute of a task in the TodoList
        System.out.println("UPDATE Operation");

        Scanner scanner = new Scanner(System.in);
        boolean repeat = true;

        while (repeat) {
            updateTaskAttributes(todoList);
            todoList.saveTasks();

            System.out.print("Repeat [Y/n]: " );
            char input = scanner.next().toLowerCase().charAt(0);
            repeat = input == 'y';
        }

        // D - Delete one task in the TodoList
        System.out.println("DELETE operation");
        int selection = getUserSelection(todoList);
        todoList.removeTask(todoList.getTasks().get(selection-1).getId());
        printTodoList(todoList);
    }

    public static void printTodoList(TodoList todoList) {
        for (int i = 0; i<todoList.getTasks().size(); i++) {
            System.out.println(i+1 + ". " + todoList.getTasks().get(i));
        }
    }

    public static int getUserSelection(TodoList todoList) {
        Scanner scanner = new Scanner(System.in);
        int selection = -1;
        boolean isValid = false;

        System.out.println("\nThe tasks you want to choose from:");

        while (!isValid) {
            printTodoList(todoList);
            System.out.print("Enter the number of your selection (1-" + todoList.getSize() + "): ");

            if (scanner.hasNextInt()) {
                selection = scanner.nextInt();

                // 3. Validate the selection against the list size
                if (selection >= 1 && selection <= todoList.getSize()) {
                    isValid = true; // Input is valid, exit the loop
                } else {
                    System.out.println("\nInvalid selection. Please choose a number between 1 and " + todoList.getSize() + ".");
                }
            } else {
                System.out.println("\nInvalid input. Please enter a number.");
                // Consume the non-integer input to prevent an infinite loop
                scanner.next();
            }
        }
        return selection;
    }

    public static void updateTaskAttributes(TodoList todoList) {
        if (todoList.getSize() == 0) {
            System.out.println("No tasks available to update.");
            return;
        }

        // 1. Get the user's task selection (returns 1-based index)
        int selection = getUserSelection(todoList);

        // Convert to 0-based index to retrieve the Task object
        Task taskToUpdate = todoList.getTask(todoList.getTasks().get(selection-1).getId());

        if (taskToUpdate == null) return; // Safety check

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSelected Task: " + taskToUpdate);

        // 2. Prompt for the attribute to update
        System.out.println("\nWhich attribute would you like to update?");
        System.out.println("1. Title | 2. Description | 3. Due Date | 4. Category | 5. Priority | 6. Completed Status");
        System.out.print("Enter your choice (1-6): ");

        // This input is temporary and should be handled with robustness in production code
        String attrChoice = scanner.nextLine().trim();

        // 3. Handle the update based on the choice
        switch (attrChoice) {
            case "1":
                System.out.print("Enter new Title: ");
                taskToUpdate.setTitle(scanner.nextLine());
                break;
            case "2":
                System.out.print("Enter new Description: ");
                taskToUpdate.setDescription(scanner.nextLine());
                break;
            case "3":
                updateDueDate(taskToUpdate, scanner);
                break;
            case "4":
                updateCategory(taskToUpdate, scanner);
                break;
            case "5":
                updatePriority(taskToUpdate, scanner);
                break;
            case "6":
                updateCompletedStatus(taskToUpdate, scanner);
                break;
            default:
                System.out.println("Invalid attribute choice.");
                return;
        }

        System.out.println("\nTask successfully updated! New details:");
        System.out.println(taskToUpdate);
    }

    private static void updateDueDate(Task task, Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean validDate = false;
        while (!validDate) {
            System.out.print("Enter new Due Date (yyyy-MM-dd): ");
            String dateStr = scanner.nextLine();
            try {
                task.setDueDate(LocalDate.parse(dateStr, formatter));
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
    }

    private static void updateCategory(Task task, Scanner scanner) {
        System.out.println("Available Categories: " + java.util.Arrays.toString(Constants.TaskCategory.values()));
        boolean valid = false;
        while (!valid) {
            System.out.print("Enter new Category: ");
            String catStr = scanner.nextLine().toUpperCase();
            try {
                task.setCategory(Constants.TaskCategory.valueOf(catStr));
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid category. Please choose from the list.");
            }
        }
    }

    private static void updatePriority(Task task, Scanner scanner) {
        System.out.println("Available Priorities: " + java.util.Arrays.toString(Constants.TaskPriority.values()));
        boolean valid = false;
        while (!valid) {
            System.out.print("Enter new Priority: ");
            String prioStr = scanner.nextLine().toUpperCase();
            try {
                task.setPriority(Constants.TaskPriority.valueOf(prioStr));
                valid = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid priority. Please choose from the list.");
            }
        }
    }

    private static void updateCompletedStatus(Task task, Scanner scanner) {
        System.out.print("Is the task completed? (true/false): ");
        String statusStr = scanner.nextLine().toLowerCase();
        if (statusStr.equals("true")) {
            task.setCompleted(true);
        } else if (statusStr.equals("false")) {
            task.setCompleted(false);
        } else {
            System.out.println("Invalid input. Status remains unchanged.");
        }
    }
}
