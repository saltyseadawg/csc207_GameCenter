package a207project.fall18.gamecenter;
//https://github.com/tpcstld/2048

/**
 * A Block in the 2048 game
 */
class Block extends Cell {
    /**
     * The number on the Block
     */
    private final int value;
    /**
     * A list of Blocks that can be merged
     */
    private Block[] mergedFrom = null;

    /**
     * A Block with x and y position with a value
     * @param x row position
     * @param y column position
     * @param value the value
     */
    Block(int x, int y, int value) {
        super(x, y);
        this.value = value;
    }

    /**
     * A Block with cell position and value
     * @param cell the cell for the Block
     * @param value the value
     */
    Block(Cell cell, int value) {
        super(cell.getX(), cell.getY());
        this.value = value;
    }

    /**
     * Updates the position of the Block with the corresponding Cell position
     * @param cell the new position of the Block
     */
    void updatePosition(Cell cell) {
        this.setX(cell.getX());
        this.setY(cell.getY());
    }

    /**
     * Return the value of the Block
     * @return the value of Block
     */
    int getValue() {
        return this.value;
    }

    /**
     * Return mergedFrom of the Block
     * @return the mergedFrom
     */
    Block[] getMergedFrom() {
        return mergedFrom;
    }

    /**
     * Set mergedFrom to block
     * @param block the new mergedFrom, mergedFrom will be set to
     */
    void setMergedFrom(Block[] block) {
        mergedFrom = block;
    }
}
