package com.example.escriturarapida;

import com.example.escriturarapida.view.GameApplication;
import javafx.application.Application;

/**
 * Starts the JavaFX game application.
 */
public class Launcher {

    /**
     * Launches the game application.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        Application.launch(GameApplication.class, args);
    }
}
