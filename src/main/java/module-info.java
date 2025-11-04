module com.example.catasgn1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.catasgn1 to javafx.fxml, com.google.gson;
    opens com.example.catasgn1.model to com.google.gson;
    exports com.example.catasgn1;
}