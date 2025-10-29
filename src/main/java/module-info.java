module com.example.catasgn1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.catasgn1 to javafx.fxml;
    exports com.example.catasgn1;
}