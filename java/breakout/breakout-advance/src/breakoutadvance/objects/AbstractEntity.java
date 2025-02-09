package breakoutadvance.objects;

import javafx.scene.Node;

/**
 * Abstract class used to create an entity on the screen.
 */
public abstract class AbstractEntity {

    protected Node node; // JavaFX node on the screen

    protected double posX; // x-position on screen
    protected double posY; // y-position on screen

    protected double height; // Height of the entity
    protected double width; // Width of the entity

    /**
     * Basic setup for an entity
     *
     * @param posX   x position
     * @param posY   y position
     * @param height height of entity
     * @param width  width of entity
     */
    public AbstractEntity(double posX, double posY, double height, double width) {
        // Setting values
        this.posX = posX;
        this.posY = posY;
        this.height = height;
        this.width = width;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
