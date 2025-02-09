package breakoutadvance.objects.powerups;

import breakoutadvance.Breakout;
import breakoutadvance.objects.Powerup;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Images;
import breakoutadvance.utils.WindowUtils;
import javafx.scene.image.Image;

/**
 * Enum to determine which powerup to spawn, if any, when a block is destroyed
 */
public enum PowerupType {
    // Power up types
    PLUS_ONE(Images.getImage(Constants.BALL_FILEPATH + Breakout.getInstance().getDataManager().getData().getBallColor() + ".png"), 50), // 1 is equal to 0.1%
    PLUS_WIDTH(Images.getImage(Constants.IMAGE_PATH + "paddleWidthPowerUp.png"), 50),
    BOMB(Images.getImage(Constants.IMAGE_PATH + "bomb.png"), 50),
    HEART(Images.getImage(Constants.IMAGE_PATH + "heart_full.png"), 10);

    private final Image img; // Image to display

    private final double spawnChance; // Spawn chance

    /**
     * Constructor for a powerup
     *
     * @param img image to display
     * @param spawnChance spawn chance
     */
    PowerupType(Image img, double spawnChance) {
        this.img = img;
        this.spawnChance = spawnChance;
    }

    public Image getImg() {
        return img;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    /**
     * Creating powerup
     *
     * @param playScene current play scene
     * @param type      powerup type
     * @param posX      x-position
     * @param posY      y-position
     * @param height    height of powerup
     * @param width     width of powerup
     * @param velX      velocity on X-axis
     * @param velY      velocity on Y-axis
     * @return          powerup created
     */
    public Powerup createPowerup(PlayScene playScene, PowerupType type, double posX, double posY, double height, double width, double velX, double velY) {
        // Return switch statement, which makes the code more readable and lessens the use of a lot of return statements
        // Using yield to specify what to return in case of multiple choices (if-statements)
        return switch (type) {
            // PLUS_ONE -> Another ball is added
            case PLUS_ONE -> new PowerUpExtraBall(playScene, posX, posY, height, width, velX, velY);
            // PLUS_WIDTH -> If paddle is not at max width, spawn the powerup
            case PLUS_WIDTH -> {
                if (playScene.getGame().getPaddle().getWidth() <= WindowUtils.getWindowWidth() / 4) {
                    yield new PowerUpExpandPaddle(playScene, posX, posY, height, width, velX, velY);
                }
                yield null;
            }
            // BOMB -> Spawn a bomb
            case BOMB -> new BombObstacle(playScene, posX, posY, height, width, velX, velY);
            // HEART -> If player is not at max lives, spawn a life
            case HEART -> {
                if (playScene.getGame().getLives() <= 5) {
                    yield new HearthObstacle(playScene, posX, posY, height, width, velX, velY);
                }
                yield null;
            }
            default -> throw new IllegalArgumentException("Invalid powerup type");
        };
    }
}



