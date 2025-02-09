package breakoutadvance.core;

import breakoutadvance.levels.LevelMap;
import breakoutadvance.objects.Block;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.WindowUtils;

/**
 * Grid is used to display blocks on top of the screen in a grid format.
 */
public class Grid {

    private final Game game; // Current game instance

    private final Block[][] grid; // 2d-Array which is filled with blocks or null depending on if the block is alive

    private final LevelMap levelMap; // A representation of a level

    public Grid(Game game, LevelMap levelMap) {
        // Instantiating values
        this.game = game;
        this.levelMap = levelMap;
        grid = new Block[this.getCols()][this.getRows()];

        // Long side of the rectangle's length, based on window size
        double lSize = (WindowUtils.getWindowWidth() * (97.5 / 100.0) / this.getRows());

        // Short side of the rectangle's length, based on window size
        double sSize = ((WindowUtils.getWindowHeight() / 20.0));

        // Starting positions, where blank space is calculated
        double posXStart = WindowUtils.getWindowWidth() * (1.0 / 100.0);
        double posYStart = WindowUtils.getWindowWidth() * (4.0 / 100.0);

        // For-loop for grid
        for (int row = 0; row < this.getCols(); row++) {
            for (int col = 0; col < this.getRows(); col++) {
                // If the current row's length, is smaller than the col-amount
                // The block will be set to null, and we continue
                if (levelMap.getLevelRows()[row].getRow().length <= col) {
                    grid[row][col] = null;
                    continue;
                }

                // Calculating positions on screen
                double posX = posXStart + col * lSize; // Horizontal position for each rectangle
                double posY = posYStart + row * sSize; // Vertical position for each rectangle

                // Setting the blockType based on levelMaps, row and col
                Block.BlockType blockType = levelMap.getLevelRows()[row].getRow()[col];

                // If the blockType is null, set it to null in the grid
                if (blockType == null) {
                    grid[row][col] = null;
                    continue;
                }
                // The blockType is not null - now we create a new block
                Block block = new Block(blockType, posX, posY, lSize - Constants.OFFSET_BETWEEN_BLOCKS, sSize - Constants.OFFSET_BETWEEN_BLOCKS);

                // Adding it to grid
                grid[row][col] = block;

                // Adding it to the scene
                this.game.getPlayScene().getPane().getChildren().add(block.getNode());
            }
        }
    }

    /**
     * Remove a block from the grid and scene
     *
     * @param n Row in grid
     * @param m Col in grid
     */
    public void removeBlock(int n, int m) {
        // Assigning current block, based on n and m in grid
        Block block = this.grid[n][m];
        // If it's null, then return
        if (block == null || block.getBlockType() == null) return;

        // Add score, based on block type
        this.game.increaseScore(block.getBlockType().getBreakScore());

        // Get next block type or remove from screen
        Block.BlockType nextBlockType = Block.BlockType.getNextBlockType(block.getBlockType());

        // Checking if there exists a nextBlockType, to change color to
        if (nextBlockType != null) {
            // Updating blockType and image
            block.setBlockType(nextBlockType);
            block.updateImage();
        } else {
            // Removing the block from the pane, and grid
            this.game.getPlayScene().getPane().getChildren().remove(block.getNode());
            this.grid[n][m] = null;
        }
    }

    /**
     * Getting the amount of alive blocks in the grid
     *
     * @return 1 - if at least one block is alive, 0 - if no blocks are alive
     */
    public int getAliveAmount() {
        // If at least on block is alive, return 1
        for (Block[] blocks : grid) {
            for (Block block : blocks) {
                if (block != null)
                    return 1;
            }
        }
        // If no blocks are alive, return 0
        return 0;
    }

    // Get 2d array of Block
    public Block[][] getGrid() {
        return grid;
    }


    /**
     * Getting the length of the longest row
     *
     * @return length of the longest row
     */
    private int getRows() {
        int maxLength = 0;
        // Iterating through all rows
        for (int i = 0; i < this.levelMap.getLevelRows().length; i++) {
            // If row i's length is greater than maxLength, maxLength is updated
            if (this.levelMap.getLevelRows()[i].getRow().length > maxLength) {
                maxLength = this.levelMap.getLevelRows()[i].getRow().length;
            }
        }
        return maxLength;
    }

    /**
     * Getting length of columns
     *
     * @return length of columns
     */
    private int getCols() {
        return this.levelMap.getLevelRows().length;
    }
}
