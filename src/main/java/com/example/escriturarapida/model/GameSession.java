package com.example.escriturarapida.model;
import java.util.Random;

/**
 * Stores the game state and manages level progression and time limits.
 */
public class GameSession {

    /** Initial time limit per level, in seconds. */
    private static final int INITIAL_TIME_SECONDS = 20;

    /** Number of completed levels required for each time reduction. */
    private static final int LEVELS_PER_REDUCTION = 5;

    /** Time reduction applied after each group of completed levels, in seconds. */
    private static final int TIME_REDUCTION_SECONDS = 2;

    /** Minimum time limit per level, in seconds. */
    private static final int MIN_TIME_SECONDS = 2;

    /** Maximum number of levels the player can complete. */
    private static final int MAX_LEVEL = 50;

    /** Words and phrases available for levels 1 to 10. */
    private final String[] texts = {
        "Programación con JavaFX",
        "Desarrollo de software",
        "Nunca parar de aprender",
        "Buen trabajo, sigue así",
        "Aprende algo nuevo",
        "Todo tiene solución",
        "Piensa antes de escribir",
        "HTML, CSS y JavaScript",
        "Git permite controlar versiones",
        "La práctica hace al maestro",
        "Aprender requiere práctica",
    };

    /** Words and phrases available for levels 11 to 25. */
    private final String[] mediumTexts = {
        "Java y JavaFX",
        "Hola, mundo!",
        "Sigue practicando",
        "El tiempo corre",
        "Prueba otra vez",
        "El código funciona",
        "Todo tiene solución",
        "La práctica ayuda",
        "Vamos a programar",
        "Un paso a la vez",
        "Hoy toca aprender",
        "Eventos de teclado",
        "Programación",
        "Ágil",
        "Difícil",
        "¡Tú puedes!",
        "JavaScript",
        "Python",
        "GitHub",
        "Mantén el ritmo",
    };

    /** Words and phrases available for levels 26 to 50. */
    private final String[] lastTexts = {
        "JavaFX",
        "Python",
        "C++",
        "Ágil",
        "SQL",
        "Día",
        "Git",
        "GitHub",
        "HTML",
        "CSS",
        "React",
    };

    /** Random generator used to select the target text. */
    private final Random random = new Random();

    /** Time limit for the current level, in seconds. */
    private int timeLimitSeconds = INITIAL_TIME_SECONDS;

    /** Remaining time for the current level, in seconds. */
    private int remainingSeconds = timeLimitSeconds;

    /** Text the player must reproduce exactly. */
    private String targetText;

    /** Indicates whether the game session has ended. */
    private boolean finished = false;

    /** Number of levels successfully completed by the player. */
    private int completedLevels = 0;

    /**
     * Creates a game session with an initial random text.
     */
    public GameSession() {
        selectRandomText();
    }

    /**
     * Returns the text the player must type.
     *
     * @return the target word or phrase
     */
    public String getTargetText() {
        return targetText;
    }

    /**
     * Checks whether the answer exactly matches the target text.
     *
     * @param answer the text entered by the player
     * @return true if the answer matches exactly; false otherwise
     */
    public boolean isCorrectAnswer(String answer) {
        return targetText.equals(answer);
    }

    /**
     * Selects a random word or phrase for the current level,
     * avoiding consecutive repetitions.
     */
    public void selectRandomText() {
        String[] currentTexts;

        int currentLevel = getCurrentLevel();

        if (currentLevel <= 10) {
            currentTexts = texts;
        } else if (currentLevel <= 25) {
            currentTexts = mediumTexts;
        } else {
            currentTexts = lastTexts;
        }


        String newText;

        do {
            int index = random.nextInt(currentTexts.length);
            newText = currentTexts[index];
        } while (newText.equals(targetText));

        targetText = newText;
    }

    /**
     * Returns the time limit for the current level.
     *
     * @return the time limit in seconds
     */
    public int getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    /**
     * Returns the remaining time for the current level.
     *
     * @return the remaining time in seconds
     */
    public int getRemainingSeconds() {
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


