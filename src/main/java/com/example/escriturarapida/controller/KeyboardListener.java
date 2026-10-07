package com.example.escriturarapida.controller;
import javafx.scene.input.KeyEvent;

/**
 * Defines callbacks for keyboard press, release, and character typing events.
 */
public interface KeyboardListener {

    /**
     * Handles a key press event.
     *
     * @param event the keyboard event generated when a key is pressed
     */
    void onKeyPressed(KeyEvent event);

    /**
     * Handles a key release event.
     *
     * @param event the keyboard event generated when a key is released
     */
    void onKeyReleased(KeyEvent event);

    /**
     * Handles a character typing event.
     *
     * @param event the keyboard event containing the typed character
     */
    void onKeyTyped(KeyEvent event);
}
