package com.example.catasgn1.utils;

public class Constants {
    public enum TaskPriority {
        HIGH("High"),
        MEDIUM("Medium"),
        LOW("Low");

        private final String displayName;
        TaskPriority(String priority) {
            this.displayName = priority;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public enum TaskCategory {
        WORK("Work"),
        PERSONAL("Personal"),
        FINANCE("Finance"),
        HEALTH("Health"),
        LEARNING("Learning"),
        OTHER("Other");

        private final String displayName;
        TaskCategory(String category) {
            this.displayName = category;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
