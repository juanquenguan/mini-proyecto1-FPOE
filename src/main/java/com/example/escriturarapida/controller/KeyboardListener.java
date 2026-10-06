package com.example.escriturarapida.controller;
import javafx.scene.input.KeyEvent;

public interface KeyboardListener {
    void onKeyPressed(KeyEvent event);

    void onKeyReleased(KeyEvent event);

    void onKeyTyped(KeyEvent event);
}
