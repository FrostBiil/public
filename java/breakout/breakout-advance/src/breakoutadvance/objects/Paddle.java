package breakoutadvance.objects;

import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.PaddleUtil;
import breakoutadvance.utils.WindowUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Used to create a paddle on the screen
 */
public class Paddle extends AbstractEntity {
    private double velX; // Current directional velocity
    private Image img; // Image to draw
    private ImageView imgView = new ImageView(); // To draw/view to image on screen

    private boolean moveLeft = false; // Is paddle set to move left?
    private boolean moveRight = false; // Is paddle set to move right?

    /**
     * Creating a paddle, based on the given parameters
     * It will try to use an image to display the paddle on the screen,
     * but will draw a rectangle, if no image is found
     *
     * @param playScene current instance of playScene
     * @param posX      x position
     * @param posY      y position
     * @param velX      Starting velocity in the x direction
     * @param height    height of the paddle
     * @param width     width of the paddle
     */
    public Paddle(PlayScene playScene, double posX, double posY, double velX, double height, double width) {
        super(posX, posY, height, width);
        this.velX = velX;

        // Trying to display the paddle using an image
        try {
            // Getting image and adding it to an ImageView
            Image image = PaddleUtil.buildPaddleImage((int) this.width);
            imgView = new ImageView(image);

            // Setting dimensions
            imgView.setFitHeight(this.getHeight());
            imgView.setFitWidth(this.getWidth());

            // Setting position
            imgView.relocate(this.getPosX(), this.getPosY());

            // Adding it to the pane
            playScene.getPane().getChildren().add(imgView);
            this.setNode(imgView);
        } catch (Exception e) {
            // Create Rectangle as the paddle
            Rectangle paddle = new Rectangle(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());
            paddle.setFill(Color.RED);
            playScene.getPane().getChildren().add(paddle);

            // Update position
            this.setNode(paddle);
        }
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

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        imgView.setImage(img);
    }

    public ImageView getImgView() {
        return imgView;
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

