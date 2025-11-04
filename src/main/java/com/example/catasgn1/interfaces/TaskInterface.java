package com.example.catasgn1.interfaces;

import com.example.catasgn1.utils.Constants;

import java.time.LocalDate;

public interface TaskInterface {
    public String getId();

    public String getTitle();
    public void setTitle(String title);

    public String getDescription();
    public void setDescription(String description);

    public LocalDate getDueDate();
    public void setDueDate(LocalDate dueDate);

    public Constants.TaskCategory getCategory();
    public void setCategory(Constants.TaskCategory category);

    public Constants.TaskPriority getPriority();
    public void setPriority(Constants.TaskPriority priority);

    public boolean isCompleted();
    public void setCompleted(boolean completed);

    public String toString();
}
