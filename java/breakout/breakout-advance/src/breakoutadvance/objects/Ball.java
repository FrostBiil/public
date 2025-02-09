package breakoutadvance.objects;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Images;
import breakoutadvance.utils.WindowUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Used to create balls on the screen
 */
public class Ball extends AbstractEntity {
    private double velX; // x-velocity
    private double velY; // y-velocity
    private double radius; // Added to avoid confusion with width and height
    private Image img; // Image to draw
    private ImageView imgView = new ImageView(); // To draw/view to image on screen

    /**
     * Creating a ball, based on the given parameters
     * It will try to use an image to display the ball on the screen,
     * but will draw a circle, if no image is found
     *
     * @param playScene current instance of playScene
     * @param posX      x-position of the ball to draw
     * @param posY      y-position of the ball to draw
     * @param velX      x-velocity for the ball
     * @param velY      y-velocity for the ball
     * @param radius    radius of the ball
     */
    public Ball(PlayScene playScene, double posX, double posY, double velX, double velY, double radius) {
        // Setting values
        super(posX, posY, radius, radius);
        this.velX = velX;
        this.velY = velY;
        this.radius = radius;

        // Trying to display the ball using an image
        try {
            // Getting image and adding it to an ImageView
            Image image = Images.getImage(Constants.BALL_FILEPATH + Breakout.getInstance().getDataManager().getData().getBallColor() + ".png");
            if (image == null) {
                throw new Exception("Image not found");
            }
            imgView = new ImageView(image);

            // Setting dimensions
            imgView.setFitHeight(this.getHeight() * 2);
            imgView.setFitWidth(this.getWidth() * 2);

            // Setting position
            imgView.relocate(this.getPosX() + this.getHeight(), this.getPosY() + this.getHeight());

            // Adding it to the pane
            playScene.getPane().getChildren().add(imgView);
            this.setNode(imgView);
        } catch (Exception e) {
            // Drawing ball
            // Adding radius (this.getHeight()) to accommodate how a circle is drawn
            Circle ball = new Circle(this.getPosX() + this.getHeight(), this.getPosY() + this.getHeight(), this.getHeight());
            ball.setFill(Color.RED);
            playScene.getPane().getChildren().add(ball);
            this.setNode(ball);
        }
    }

    /**
     * Update x-position of the ball
     */
    public void updateX() {
        if (this.getPosX() + velX >= WindowUtils.getWindowWidth() - this.getWidth() * 2) {
            this.flipVelX(); //flip velocity if ball goes out of bounds (sides)
        } else if (this.getPosX() + velX <= 0) {
            this.flipVelX(); //flip velocity if ball goes out of bounds (sides)
        }
    }

    /**
     * Update y-position of the ball
     */
    public void updateY() {
        if (this.getPosY() + velY >= WindowUtils.getWindowHeight() - this.getHeight() * 2) {
            // Do nothing as the ball hits the bottom of the screen.
        } else if (this.getPosY() + velY <= 0) {
            this.flipVelY(); // Flip velocity if ball goes out of bounds (top)
        }
    }

    /**
     * Sets new x-, y-position
     */
    public void updatePosition() {
        this.setPosX(this.getPosX() + velX);
        this.setPosY(this.getPosY() + velY);
        this.getNode().relocate(this.getPosX(), this.getPosY()); // Relocates points in scene
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Flip the direction of the x velocity
     */
    public void flipVelX() {
        velX = -velX;
    }

    /**
     * Flip the direction of the y velocity
     */
    public void flipVelY() {
        velY = -velY;
    }

    //updates ball position

    /**
     * Function called every tick which updates the position of the ball
     */
    public void onTick() {
        this.updateX();
        this.updateY();
        this.updatePosition();
    }
}
