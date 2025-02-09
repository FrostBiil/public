package breakoutadvance.utils;

import breakoutadvance.objects.AbstractEntity;
import breakoutadvance.objects.Ball;
import breakoutadvance.objects.Paddle;
import breakoutadvance.objects.Powerup;

/**
 * Utility class for collision detection.
 */
public class CollisionChecker {

    /**
     * Checks collision between two entities (box-to-box collision).
     *
     * @param firstEntity  the first entity
     * @param secondEntity the second entity
     * @return true if collided, false otherwise
     */
    public static boolean checkCollision(AbstractEntity firstEntity, AbstractEntity secondEntity) {
        return overlaps(firstEntity.getPosX(), firstEntity.getPosY(), firstEntity.getWidth(), firstEntity.getHeight(),
                secondEntity.getPosX(), secondEntity.getPosY(), secondEntity.getWidth(), secondEntity.getHeight());
    }

    /**
     * Checks collision between a paddle and a powerup (box-to-box collision).
     *
     * @param paddle  the paddle
     * @param powerup the powerup
     * @return true if collided, false otherwise
     */
    public static boolean checkCollision(Paddle paddle, Powerup powerup) {
        if (paddle.getPosY() + paddle.getHeight() < powerup.getPosY()) {
            return false; // Powerup is above the paddle
        }

        return overlaps(paddle.getPosX(), paddle.getPosY(), paddle.getWidth(), paddle.getHeight(),
                powerup.getPosX(), powerup.getPosY(), powerup.getWidth(), powerup.getWidth());
    }

    /**
     * Checks collision between a ball (sphere) and an entity (box).
     *
     * @param entity the entity (block or paddle)
     * @param ball   the ball
     * @return the axis of collision or NONE if no collision
     */
    public static EdgeHit checkCollision(AbstractEntity entity, Ball ball) {
        double ballCenterX = ball.getPosX() + ball.getRadius();
        double ballCenterY = ball.getPosY() + ball.getRadius();

        // Entity bounds
        double left = entity.getPosX();
        double right = left + entity.getWidth();
        double top = entity.getPosY();
        double bottom = top + entity.getHeight();

        // Find the closest point on the entity to the center of the ball
        double closestX = clamp(ballCenterX, left, right);
        double closestY = clamp(ballCenterY, top, bottom);

        // Calculate the distance between the ball's center and the closest point
        double distanceX = ballCenterX - closestX;
        double distanceY = ballCenterY - closestY;
        double distanceSquared = distanceX * distanceX + distanceY * distanceY;

        // Check if the distance is less than the radius squared
        if (distanceSquared > ball.getRadius() * ball.getRadius()) {
            return EdgeHit.NONE; // No collision
        }

        // Determine the axis of collision
        boolean hitHorizontal = (ballCenterY > top && ballCenterY < bottom);
        boolean hitVertical = (ballCenterX > left && ballCenterX < right);

        if (hitHorizontal) return EdgeHit.XAXIS;
        if (hitVertical) return EdgeHit.YAXIS;

        return EdgeHit.BOTH; // Corner collision
    }

    /**
     * Utility method to clamp a value between a minimum and a maximum.
     *
     * @param value the value to clamp
     * @param min   the minimum value
     * @param max   the maximum value
     * @return the clamped value
     */
    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Utility method to check if two rectangles overlap.
     *
     * @param x1, y1, w1, h1 - first rectangle properties
     * @param x2, y2, w2, h2 - second rectangle properties
     * @return true if rectangles overlap, false otherwise
     */
    private static boolean overlaps(double x1, double y1, double w1, double h1, double x2, double y2, double w2, double h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }
}
