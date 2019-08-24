package a207project.fall18.gamecenter;
//https://github.com/tpcstld/2048

/**
 * A Cell in 2048 game
 */
class Cell {
    /**
     * Row number position
     */
    private int x;
    /**
     * Column number position
     */
    private int y;

    /**
     * A new Cell with a set position
     * @param x row number
     * @param y column number
     */
    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return the row number of the Cell
     * @return the row number of Cell
     */
    int getX() {
        return this.x;
    }

    /**
     * Set x to new_x
     * @param new_x the new row number x will be set to
     */
    void setX(int new_x) {
        this.x = new_x;
    }

    /**
     * Return the column number of the Cell
     * @return the column number of the cell
     */
    int getY() {
        return this.y;
    }

    /**
     * Set y to new_y
     * @param new_y  the new column number y will be set to
     */
    void setY(int new_y) {
        this.y = new_y;
    }
}
