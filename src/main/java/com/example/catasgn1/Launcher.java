package com.example.catasgn1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                Launcher.class.getResource("ui/main-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Smart Todo List");
        stage.setScene(scene);
        stage.show();
    }
}
