package com.example.catasgn1.utils;

public class Constants {
    public enum TaskPriority {
        High,
        Medium,
        Low;

        // Converts enum name to capitalized word
        public String toCapitalized() {
            String name = this.name().toLowerCase(); // "high"
            return name.substring(0, 1).toUpperCase() + name.substring(1); // "High"
        }
    }

    public enum TaskCategory {
        Work,
        Personal,
        Finance,
        Health,
        Learning,
        Other;

        // Converts enum name to capitalized word
        public String toCapitalized() {
            String name = this.name().toLowerCase(); // "high"
            return name.substring(0, 1).toUpperCase() + name.substring(1); // "High"
        }
    }
}
