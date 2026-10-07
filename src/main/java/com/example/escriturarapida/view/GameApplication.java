package com.example.escriturarapida.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Creates and displays the game window.
 */
public class GameApplication extends Application {

    /**
     * Loads the game interface and displays the main window.
     *
     * @param stage the main application window
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("/com/example/escriturarapida/game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600,420 );
        stage.setTitle("Escritura rápida");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
