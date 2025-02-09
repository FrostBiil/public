package breakoutadvance.levels;

import breakoutadvance.objects.Block;

import java.util.Arrays;

/**
 * LevelMap class is a template for a level, which consists of LevelRow objects
 */
public class LevelMap {
    LevelRow[] levelRows = new LevelRow[0]; // Array of rows in the level

    public LevelRow[] getLevelRows() {
        return levelRows;
    }

    public void setLevelRows(LevelRow[] levelRows) {
        this.levelRows = levelRows;
    }

    @Override
    public String toString() {
        return "LevelMap{" +
                "levelRows=" + Arrays.toString(levelRows) +
                '}';
    }

    /**
     * LevelRow class is a template for a row in the LevelMap class
     * Consists of an array of Block.BlockType objects, which is every block
     */
    public static class LevelRow {
        Block.BlockType[] row = new Block.BlockType[0]; // Array of blocks in the current row (LevelRow)

        public Block.BlockType[] getRow() {
            return row;
        }

        public void setRow(Block.BlockType[] row) {
            this.row = row;
        }

        @Override
        public String toString() {
            return "LevelRow{" +
                    "row=" + Arrays.toString(row) +
                    '}';
        }
    }
}
