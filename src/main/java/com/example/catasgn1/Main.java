package com.example.catasgn1;

import com.example.catasgn1.model.Task;
import com.example.catasgn1.model.TodoList;

public class Main {
    public static void main(String[] args) {
//        Launcher.launch(Launcher.class, args);
        TodoList todoList = new TodoList();
        for (Task task : todoList.getTasks()) {
            System.out.println(task.toString());
        }
    }
}
