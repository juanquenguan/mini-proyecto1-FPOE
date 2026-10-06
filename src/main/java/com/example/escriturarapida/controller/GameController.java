package com.example.escriturarapida.controller;

import com.example.escriturarapida.model.GameSession;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class GameController {

    private GameSession gameSession = new GameSession();
    private Timeline timer;

    @FXML
    private Label levelLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private ProgressBar timeBar;

    @FXML
    private Label wordLabel;

    @FXML
    private TextField answerField;

    @FXML
    private Button validateButton;

    @FXML
    private Label messageLabel;

    /**
     * Prepares the initial level and starts its countdown.
     */
    @FXML
    private void initialize() {
        showCurrentLevel();
        messageLabel.setText("Escribe el texto exactamente como aparece.");

        AnswerKeyboardHandler keyboardHandler = new AnswerKeyboardHandler();

        answerField.addEventFilter(
                KeyEvent.KEY_PRESSED,
                keyboardHandler::onKeyPressed
        );

        startTimer();
    }

    /**
    * Validates the current answer and displays feedback
    */
    @FXML
    private void onValidateAnswer() {
        if (gameSession.isFinished()) {
            return;
        }

        String answer = answerField.getText();
        boolean correct = gameSession.isCorrectAnswer(answer);

        if (correct) {
            handleCorrectAnswer();
        } else {
            messageLabel.setText(
                    "Incorrecto. Revisa mayúsculas, espacios y signos."
            );
        }

    }

    /**
     * Updates the time label and progress bar.
     */
    private void updateTimerDisplay () {
        int remainingSeconds = gameSession.getRemainingSeconds();

        timeLabel.setText("Tiempo: " + remainingSeconds + " s");

        timeBar.setProgress(
                (double) remainingSeconds  / gameSession.getTimeLimitSeconds()
        );
    }

    /**
     * Updates the countdown and resolves the answer when time expires.
     */
    private class TimerHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle (ActionEvent event) {
            gameSession.decreaseRemainingTime();
            updateTimerDisplay();

            if (gameSession.getRemainingSeconds() == 0) {
                handleTimeExpired();
            }
        }
    }


    /**
     * Starts a countdown with one update per cycle.
     */
    private void startTimer() {
        timer=new Timeline(
                new KeyFrame (Duration.seconds(1), new TimerHandler())
        );

        timer.setCycleCount(gameSession.getTimeLimitSeconds());
        timer.play();
    }

    /**
     * Resolves the expired level by advancing or finishing the game.
     */
    private void handleTimeExpired() {
        if (gameSession.isFinished()) {
            return;
        }

        String answer = answerField.getText();
        boolean correct = gameSession.isCorrectAnswer(answer);

        if (correct) {
            handleCorrectAnswer();
        } else {
            timer.stop();
            gameSession.finish();

            answerField.setDisable(true);
            validateButton.setDisable(true);

            showGameSummary("tiempo agotado");
        }
    }

    /**
     * Displays the current level and prepares the answer controls.
     */
    private void showCurrentLevel() {
        levelLabel.setText("Nivel: " + gameSession.getCurrentLevel());
        wordLabel.setText(gameSession.getTargetText());

        answerField.clear();
        answerField.setDisable(false);
        validateButton.setDisable(false);

        updateTimerDisplay();
        answerField.requestFocus();

    }

    /**
     * Completes the current level and starts the next one.
     */
    private void handleCorrectAnswer(){
        timer.stop();
        gameSession.advanceLevel();
        showCurrentLevel();
        messageLabel.setText("Correcto! Nivel superado.");
        startTimer();

    }

    /**
     * Displays the final game summary.
     */
    private void showGameSummary (String reason){

        String summary = "Fin de la partida " + reason + ".\n"
                + "Niveles completados: " + gameSession.getCompletedLevels() + ".\n"
                + "Tiempo restante: " + gameSession.getRemainingSeconds() + "s";

        messageLabel.setText(summary);

    }

    /**
     * Restarts the game
     */

    @FXML
    private void onRestartGame () {
        timer.stop();

        gameSession = new GameSession();

        showCurrentLevel();
        messageLabel.setText("Escribe el texto exactamente como aparece.");

        startTimer();


    }

    /**
     * Validates the current answer when Enter is pressed.
     */
    private class AnswerKeyboardHandler extends KeyboardAdapter {

        /**
         * Handles Enter and leaves other keys unchanged.
         */
        @Override
        public void onKeyPressed(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                onValidateAnswer();
            }
        }
    }

    /**
     * Displays feedback with the selected CSS . (It's still not implemented)

    private void showMessage(String text, String feedbackStyle) {
        messageLabel.setText(text);

        messageLabel.getStyleClass().removeAll("success", "error");

        if (!feedbackStyle.isEmpty()) {
            messageLabel.getStyleClass().add(feedbackStyle);
        }
    }*/
}
