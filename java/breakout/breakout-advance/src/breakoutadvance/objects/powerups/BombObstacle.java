package breakoutadvance.objects.powerups;

import breakoutadvance.objects.Powerup;
import breakoutadvance.scenes.PlayScene;

public class BombObstacle extends Powerup {
    /**
     * Basic setup for an entity
     *
     * @param playScene current play scene
     * @param posX      x-position
     * @param posY      y-position
     * @param height    height of entity
     * @param width     width of entity
     * @param velX      velocity on X-axis
     * @param velY      velocity on Y-axis
     */
    public BombObstacle(PlayScene playScene, double posX, double posY, double height, double width, double velX, double velY) {
        super(playScene, PowerupType.BOMB, posX, posY, height, width, velX, velY);
    }

    // Method calls hitBombObstacle on collisions between power up and paddle
    @Override
    public void onCollision() {
        if (hasCollided) return;
        hasCollided = true;
        this.playScene.getGame().hitBombObstacle(this.posX, this.posY);
    }
}