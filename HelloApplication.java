package com.example.project_java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stg;
    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("commande.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.isMaximized();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        stage.centerOnScreen();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
        //stg.setFullScreen(true);
        stg.sizeToScene();
        stg.centerOnScreen();
        stg.setResizable(false);

    }
    /*public void center(Stage s) throws IOException{
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();


        stg.setX((screenWidth - stg.getWidth()) / 2);
        stg.setY((screenHeight - stg.getHeight()) / 2);
    }*/
}