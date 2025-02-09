package breakoutbasic.objects;

import breakoutbasic.scenes.PlayScene;
import breakoutbasic.utils.WindowUtils;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Used to create a paddle on the screen
 */
public class Paddle extends AbstractEntity {
    private final double velX; // Current directional velocity

    private boolean moveLeft = false; // Is paddle set to move left?
    private boolean moveRight = false; // Is paddle set to move right?

    /**
     * Creating a paddle, based on the given parameters
     *
     * @param playScene current instance of playScene
     * @param posX      x-position
     * @param posY      y-position
     * @param velX      Starting velocity in the x-direction
     * @param height    height of the paddle
     * @param width     width of the paddle
     */
    public Paddle(PlayScene playScene, double posX, double posY, double velX, double height, double width /*, String imgPath*/) {
        super(posX, posY, height, width);
        this.velX = velX;

        // Create Rectangle as the paddle
        Rectangle paddle = new Rectangle(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());
        paddle.setFill(Color.RED);
        playScene.getPane().getChildren().add(paddle);

        // Setting node
        this.setNode(paddle);
    }

    /**
     * Prevent paddle from going out of the screen and update position visibility.
     */
    public void updatePosXRight() {
        if (this.getPosX() + velX < WindowUtils.getWindowWidth() - this.getWidth())
            this.setPosX(this.getPosX() + velX);
        this.getNode().relocate(this.getPosX(), this.getPosY());
    }

    /**
     * Prevent paddle from going out of the screen and update position visibility.
     */
    public void updatePosXLeft() {
        if (this.getPosX() - velX > 0)
            this.setPosX(this.getPosX() - velX);
        this.getNode().relocate(this.getPosX(), this.getPosY());
    }

    public double getVelX() {
        return velX;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }
}

