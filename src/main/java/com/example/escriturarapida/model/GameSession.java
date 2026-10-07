package com.example.escriturarapida.model;
import java.util.Random;

public class GameSession {

    private static final int INITIAL_TIME_SECONDS = 20;
    private static final int LEVELS_PER_REDUCTION = 5;
    private static final int TIME_REDUCTION_SECONDS = 2;
    private static final int MIN_TIME_SECONDS = 2;
    private static final int MAX_LEVEL = 50;

    private final String[] texts = {
            "Java",
            "Hola, mundo!",
            "Eventos de teclado",
            "Programación con JavaFX"
    };

    private final Random random = new Random();
    private int timeLimitSeconds = INITIAL_TIME_SECONDS;
    private int remainingSeconds = timeLimitSeconds;
    private String targetText;
    private boolean finished = false;
    private int completedLevels = 0;

    /**
     * Creates a game session with an initial random text.
     */
    public GameSession() {
        selectRandomText();
    }

    public String getTargetText() {
        return targetText;
    }
    /**
     * Checks whether the given answer exactly matches the target text.
     */
    public boolean isCorrectAnswer (String answer) {
        return targetText.equals(answer);
    }

    public void selectRandomText() {
        int index = random.nextInt(texts.length);
        targetText= texts[index];
    }

    /**
     * Returns tbe time limit for the current level
     */
    public int getTimeLimitSeconds () {
        return timeLimitSeconds;
    }

    /**
     * Returns the remaining time.
     */

    public int getRemainingSeconds () {
        return  remainingSeconds;
    }

    /**
     * Decreases the remaining time without going below zero.
     */

    public void decreaseRemainingTime() {
        if (remainingSeconds > 0) {
            remainingSeconds--;
        }
    }

    /**
     * Indicates whether the game has finished.
     *
     * @return true if the game has finished; false otherwise
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Marks the game as finished.
     */
    public void finish() {
        finished = true;
    }

    /**
     * Returns the number of successfully completed levels.
     *
     * @return the completed level count
     */
    public int getCompletedLevels() {
        return completedLevels;
    }

    /**
     * Returns the current level number, capped at the maximum level.
     *
     * @return the current level number, up to the maximum level
     */
    public int getCurrentLevel() {
        return Math.min(completedLevels + 1, MAX_LEVEL);
    }

    /**
     * Records a completed level and finishes the game when the maximum
     * level is completed. Otherwise, prepares the next level.
     */
    public void advanceLevel() {
        if (finished) {
            return;
        }

        completedLevels++;

        if (completedLevels >= MAX_LEVEL) {
            finish();
            return;
        }

        timeLimitSeconds = calculateTimeLimit();

        selectRandomText();
        remainingSeconds = timeLimitSeconds;
    }
    /**
     * Calculates the level time limit from the completed level count.
     *
     * @return the calculated time limit, with a minimum of two seconds
     */
    private int calculateTimeLimit(){
        int completedGroups = completedLevels / LEVELS_PER_REDUCTION;

        int calculatedTime = INITIAL_TIME_SECONDS - completedGroups * TIME_REDUCTION_SECONDS;

        return Math.max(MIN_TIME_SECONDS, calculatedTime);
    }



}


