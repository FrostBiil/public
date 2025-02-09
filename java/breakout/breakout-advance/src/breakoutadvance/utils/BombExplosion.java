package breakoutadvance.utils;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

/**
 * Class to play explosion when paddle collides with bomb
 */

public class BombExplosion {

    private final int animationTime = 200; // Animation time
    private final Random rand = new Random(); // Used to get random numbers
    private final Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.ORANGE, Color.ORANGERED}; // Possible colors of particles

    /**
     * @param posX X-position of where to spawn animation
     * @param posY Y-position of where to spawn animation
     * @param pane Pane to draw on
     */
    public BombExplosion(double posX, double posY, Pane pane) {
        // Values
        int numberOfParticles = 150;
        ParallelTransition bombExplosion = new ParallelTransition();

        // For every particle
        for (int i = 0; i < numberOfParticles; i++) {
            // Creating new particle, with a randomly selected color from the colors array
            Circle eParticle = new Circle(posX, posY, rand.nextInt(2, 5), colors[rand.nextInt(3)]);

            // Scaling transition
            ScaleTransition scaling = createScaleTransition(eParticle, rand.nextDouble());

            // Fading transition
            FadeTransition fading = createFadeTransition(eParticle);

            // Adding translating transition, to make the particles move
            // Interval from [-100, 99]
            TranslateTransition translating = createTranslateTransition(eParticle, rand.nextInt(200) - 100, rand.nextInt(200) - 100);

            // Combining the animations
            bombExplosion.getChildren().add(new ParallelTransition(scaling, fading, translating));

            // Adding it to pane
            pane.getChildren().add(eParticle);

            // Removing the particles when explosion is done
            bombExplosion.setOnFinished(e -> pane.getChildren().remove(eParticle));
        }

        // Playing the animation
        bombExplosion.play();
    }

    /**
     * Creating a scale transition, which makes the particles/circles smaller from their start size
     *
     * @param particle    What to scale
     * @param scaleFactor Factor to scale with
     * @return ScaleTransition
     */
    private ScaleTransition createScaleTransition(Circle particle, double scaleFactor) {
        // Creating a scale transition, based on animationTime and the given object
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(animationTime), particle);

        // Changing the scale from 100% to a random given number (scaleFactor)
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(scaleFactor);
        scaleTransition.setToY(scaleFactor);

        // Returning scaleTransition
        return scaleTransition;
    }

    /**
     * Fading the particles, making them more transparent over time
     *
     * @param particle What to fade
     * @return FadeTransition
     */
    private FadeTransition createFadeTransition(Circle particle) {
        // Creating a fade transition, based on animationTime * 2, since we want to see the particles all the time
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(animationTime * 2), particle);

        // Changing the opacity from 100% to 0
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        // Returning fadeTransition
        return fadeTransition;
    }

    /**
     * Making the particles move, from a start position, to a random location
     *
     * @param particle The particle/circle to move
     * @param x        How far to move along the x-axis
     * @param y        How far to move along the y-axis
     * @return TranslateTransition
     */
    private TranslateTransition createTranslateTransition(Circle particle, double x, double y) {
        // Creating a translation transition, which changes the location of the particles
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(animationTime), particle);

        // Setting the particle to move a set position in both its x- and y-axis
        translateTransition.setByX(x);
        translateTransition.setByY(y);

        // Returning translateTransition
        return translateTransition;
    }
}
