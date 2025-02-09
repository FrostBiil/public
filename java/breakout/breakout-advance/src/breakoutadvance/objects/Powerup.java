package breakoutadvance.objects;

import breakoutadvance.objects.powerups.PowerupType;
import breakoutadvance.scenes.PlayScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Abstract class used to create powerups on screen
 */
public abstract class Powerup extends AbstractEntity {

    protected final PlayScene playScene; // What instance of PlayScene
    protected boolean hasCollided = false; // Has been caught by player or not
    private final double velX; // x-velocity
    private double velY; // y-velocity
    private Image img; // // Image to draw
    private ImageView imgView = new ImageView(); // To draw/view image on screen

    /**
     * Basic setup for creating a powerup
     * It will try to use an image to display the powerup on the screen,
     * but will draw a ball, if no image is found
     *
     * @param posX   x position
     * @param posY   y position
     * @param height height of powerup
     * @param width  width of powerup
     */
    public Powerup(PlayScene playScene, PowerupType type, double posX, double posY, double height, double width, double velX, double velY) {
        // Setting values
        super(posX, posY, height, width);
        this.playScene = playScene;
        this.velX = velX;
        this.velY = velY;

        // Trying to display the powerup using an image
        try {
            // Getting image and adding it to an ImageView
            Image image = type.getImg();
            imgView = new ImageView(image);

            // Set dimensions
            imgView.setFitHeight(this.getHeight() * 4);
            imgView.setFitWidth(this.getWidth() * 4);

            // Set position
            imgView.relocate(this.getPosX() + this.getHeight(), this.getPosY() + this.getHeight());

            // Adding it to the pane
            playScene.getPane().getChildren().add(imgView);
            this.setNode(imgView);
        } catch (Exception e) {
            // Drawing a yellow ball instead of using an image
            Circle ball = new Circle(this.getPosX(), this.getPosY(), this.getHeight() / 2);
            ball.setFill(Color.YELLOW);
            playScene.getPane().getChildren().add(ball);
            this.setNode(ball);
        }
    }

    /**
     * Updates x- and y-position
     */
    public void updatePosition() {
        this.setPosX(this.getPosX() + velX);
        this.setPosY(this.getPosY() + velY);
        this.getNode().relocate(this.getPosX(), this.getPosY()); //relocates points in scene
    }

    /**
     * Function called every tick which updates the position of the powerup
     * @param speed amount to add to its y-velocity
     */
    public void onTick(double speed) {
        updatePosition();
        velY += speed; // Adding speed, to make the powerup accelerate, making it faster over time
    }

    public abstract void onCollision();
}
