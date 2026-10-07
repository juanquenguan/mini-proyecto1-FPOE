package com.example.escriturarapida.controller;
import javafx.scene.input.KeyEvent;


/**
 * Provides empty implementations of the keyboard listener callbacks.
 * Subclasses can override only the callbacks they need.
 */
public abstract class KeyboardAdapter implements KeyboardListener {

    /**
     * Handles a key press without performing any action.
     *
     * @param event the key press event
     */
    public void onKeyPressed(KeyEvent event) {
    }

    /**
     * Handles a key release without performing any action.
     *
     * @param event the key release event
     */
    @Override
    public void onKeyReleased(KeyEvent event) {
    }

    /**
     * Handles a typed character without performing any action.
     *
     * @param event the character typing event
     */
    @Override
    public void onKeyTyped(KeyEvent event) {
    }
}
