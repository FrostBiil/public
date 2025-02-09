package breakoutadvance.objects;

import breakoutadvance.utils.BlockUtil;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Used to create blocks on the screen
 */
public class Block extends AbstractEntity {

    private BlockType blockType; // Type of block
    private Image img; // Image to draw
    private ImageView imgView = new ImageView(); // To draw/view image on screen
    /**
     * Creating a block, based on the given parameters
     * It will try to use an image to display the block on the screen,
     * but will draw a rectangle, if no image is found
     *
     * @param blockType type of block
     * @param posX      x position
     * @param posY      y position
     * @param width     the width of the block
     * @param height    the height of the block
     */
    public Block(BlockType blockType, double posX, double posY, double width, double height) {
        // Getting coordinates and height/width from parent class
        super(posX, posY, height, width);
        this.blockType = blockType;

        // Trying to display the block using an image
        try {
            // Getting image and adding it to an ImageView
            img = BlockUtil.buildBrickImage((int) width, blockType.getImage());
            imgView = new ImageView(img);

            // Set dimensions
            imgView.setFitWidth(width);
            imgView.setFitHeight(height);

            // Set position
            imgView.setX(posX);
            imgView.setY(posY);

            this.setNode(imgView);
        } catch (Exception e) {
            // Creates new rectangle
            Rectangle rectangle = new Rectangle(posX, posY, width, height);

            // Setting colors to blocks
            rectangle.setFill(blockType.getColor());

            this.setNode(rectangle);
        }
    }

    /**
     * Changing the blocks image, called when hit and when currentBlock = nextBlock
     */
    public void updateImage() {
        // Getting image
        img = BlockUtil.buildBrickImage((int) width, blockType.getImage());

        // If the current node for the block, is an ImageView, update the ImageView based on the new image
        if (this.getNode() instanceof ImageView imageView) {
            imageView.setImage(img);
        }
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    /**
     * Enum for different kind of blockTypes
     * Is used to add the right number of points when a block is hit,
     * and to easily update a type of block, to another
     */
    public enum BlockType {
        // Types of block
        YELLOW(0, 100, "yellow", Color.YELLOW),
        GREEN(1, 300, "green", Color.GREEN),
        BLUE(2, 300, "blue", Color.BLUE),
        PINK(3, 500, "pink", Color.PINK),
        RED(4, 700, "red", Color.RED),
        ;

        private final int id; // Id - used to distinguish blocks

        private final int breakScore; // How many points it gives

        private final String image; // Image of block

        private final Color color; // Color of block

        /**
         * Constructor for BlockType
         *
         * @param id            id
         * @param breakScore    how many points it gives
         * @param image         image of block
         * @param color         color of block
         */
        BlockType(int id, int breakScore, String image, Color color) {
            // Setting values
            this.id = id;
            this.breakScore = breakScore;
            this.image = image;
            this.color = color;
        }

        /**
         * Getting a BlockType based on given id, if such a BlockType exists
         *
         * @param id id (which block)
         * @return BlockType based on id
         */
        public static BlockType getBlockType(int id) {
            for (BlockType blockType : BlockType.values()) {
                if (blockType.getId() == id) {
                    return blockType;
                }
            }
            return null;
        }

        /**
         * Getting the next BlockType, to determine if it should be removed
         * or change color
         *
         * @param blockType what block's nextBlockType to get
         * @return next BlockType
         */
        public static BlockType getNextBlockType(BlockType blockType) {
            return getBlockType(blockType.getId() - 1);
        }

        public int getId() {
            return id;
        }

        public int getBreakScore() {
            return breakScore;
        }

        public String getImage() {
            return image;
        }

        public Color getColor() {
            return color;
        }
    }
}
