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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


/**
 * Connects the game rules with the interface and handles player actions.
 */
public class GameController {

    /** Current game session. */
    private GameSession gameSession = new GameSession();

    /** Timer that updates the remaining time every second. */
    private Timeline timer;

    /** Label that displays the current level. */
    @FXML
    private Label levelLabel;

    /** Label that displays the remaining time. */
    @FXML
    private Label timeLabel;

    /** Progress bar that displays the remaining time proportion. */
    @FXML
    private ProgressBar timeBar;

    /** Label that displays the text the player must type. */
    @FXML
    private Label wordLabel;

    /** Text field where the player enters an answer. */
    @FXML
    private TextField answerField;

    /** Button used to validate the player's answer. */
    @FXML
    private Button validateButton;

    /** Label that displays feedback and the final game summary. */
    @FXML
    private Label messageLabel;

    /**
     * Prepares the initial level and starts its countdown.
     */
    @FXML
    private void initialize() {
        showCurrentLevel();
        showMessage("Escribe el texto exactamente como aparece.", "");

        AnswerKeyboardHandler keyboardHandler = new AnswerKeyboardHandler();

        answerField.addEventFilter(
                KeyEvent.KEY_PRESSED,
                keyboardHandler::onKeyPressed
        );

        startTimer();
    }

    /**
     * Validates the current answer and displays feedback.
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
            showMessage("Incorrecto. Revisa mayúsculas, espacios y signos.", "error");
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

        /**
         * Updates the countdown and handles time expiration.
         *
         * @param event the timer event
         */
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
     * Starts a countdown that updates every second.
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

            showGameSummary("Tiempo agotado", "error");
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
     * Records a correct answer and either displays the final summary
     * or starts the next level.
     */
    private void handleCorrectAnswer() {
        timer.stop();
        gameSession.advanceLevel();

        if (gameSession.isFinished()) {
            answerField.setDisable(true);
            validateButton.setDisable(true);

            showGameSummary("Nivel máximo completado", "success");
            return;
        }

        showCurrentLevel();
        showMessage("Correcto! Nivel superado.", "success");
        startTimer();
    }

    /**
     * Displays the final game summary.
     *
     * @param reason the reason the game ended
     * @param feedbackStyle the CSS class for the feedback
     */
    private void showGameSummary(String reason, String feedbackStyle) {
        String summary = "Fin de la partida: " + reason + ".\n"
                + "Niveles completados: " + gameSession.getCompletedLevels() + ".\n"
                + "Tiempo restante: " + gameSession.getRemainingSeconds() + " s";

        showMessage(summary, feedbackStyle);
    }

    /**
     * Starts a new game session from level one.
     */
    @FXML
    private void onRestartGame () {
        timer.stop();

        gameSession = new GameSession();

        showCurrentLevel();
        showMessage("Escribe el texto exactamente como aparece.", "");

        startTimer();


    }

    /**
     * Validates the current answer when Enter is pressed.
     */
    private class AnswerKeyboardHandler extends KeyboardAdapter {

        /**
         * Validates the answer when Enter is pressed.
         * Consumes the Enter event.
         *
         * @param event the key press event
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
     * Validates the answer when the validate button is clicked with the primary mouse button.
     *
     * @param event the mouse event generated by the click
     */
    @FXML
    private void onValidateButtonClicked(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            onValidateAnswer();
        }
    }

    /**
     * Displays feedback using the selected CSS class.
     *
     * @param text the message to display
     * @param feedbackStyle the feedback CSS class, or an empty string
     */
    private void showMessage(String text, String feedbackStyle) {
        messageLabel.setText(text);

        messageLabel.getStyleClass().removeAll("success", "error");

        if (!feedbackStyle.isEmpty()) {
            messageLabel.getStyleClass().add(feedbackStyle);
        }
    }
}
