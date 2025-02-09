package breakoutadvance.levels;

/**
 * Level class is used to store data for a game level
 */

public class Level {

    /**
     * Basic setup for creating a level
     * Sets the parameters a level need to be created
     *
     * @param name   level name
     * @param nextLevel  next level
     * @param paddleWidth sets paddle width
     * @param ballSpeed  sets ball speed
     * @param maxBallVelocity sets the max ball speed of the ball
     * @param powerUpSpeed sets powerup falling speed
     * @param paddleSpeed sets paddle speed
     * @param levelMap sets the level
     */
    private String name = "level_unknown"; // Name of level

    private String nextLevel = "level_unknown"; // Name of next level

    private double paddleWidth = 200; // Paddle width

    private double ballSpeed = 1.0; // Ball speed

    private double maxBallVelocity = 1.0; // Maximum amount of ball velocity

    private double powerUpSpeed = 0.01; // Speed of powerups falling

    private double paddleSpeed = 1.0; // Paddle speed

    private LevelMap levelMap = new LevelMap(); // A representation of a level

    // All getters and Setters
    public String getName() {
        return name;
    }

    public double getPaddleWidth() {
        return paddleWidth;
    }

    public double getBallSpeed() {
        return ballSpeed;
    }

    public double getMaxBallVelocity() {
        return maxBallVelocity;
    }

    public double getPowerUpSpeed() {
        return powerUpSpeed;
    }

    public double getPaddleSpeed() {
        return paddleSpeed;
    }

    public LevelMap getLevelMap() {
        return levelMap;
    }

    public void setLevelMap(LevelMap levelMap) {
        this.levelMap = levelMap;
    }

    public String getNextLevel() {
        return nextLevel;
    }


    // Convert level data into a string
    @Override
    public String toString() {
        return "Level{" +
                "name='" + name + '\'' +
                ", paddleWidth=" + paddleWidth +
                ", ballSpeed=" + ballSpeed +
                ", maxBallVelocity=" + maxBallVelocity +
                ", powerUpSpeed=" + powerUpSpeed +
                ", paddleSpeed=" + paddleSpeed +
                ", levelMap=" + levelMap +
                '}';
    }
}
