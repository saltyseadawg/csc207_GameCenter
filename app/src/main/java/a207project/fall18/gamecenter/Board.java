package a207project.fall18.gamecenter;

import android.support.annotation.NonNull;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 *
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    static int NUM_ROWS = 4;

    /**
     * The number of rows.
     */
    static int NUM_COLS = 4;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * A different Board with the same tiles
     * @param newBoard the board we are looking into to copy over a new board
     */

    Board(Board newBoard){
        for (int row = 0; row != Board.NUM_ROWS; row++){
            for (int col = 0; col != Board.NUM_COLS; col++){
                this.tiles[row][col] = newBoard.tiles[row][col];
            }
        }
    }

    /**
     * Return the Iterator for this board
     * @return the Iterator for this board
     */
    @Override
    @NonNull
    public Iterator<Tile> iterator() {
        return new BoardTileIterator();
    }

    /**
     * Return the number of tiles on the board.
     * @return the number of tiles on the board
     */
    int numTiles() {
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Set the tile to newTiles
     * @param newTiles the tiles that tiles will be set to
     */
    void setTiles(Tile newTiles[][]) { tiles = newTiles; }

    /**
     * Return the tiles of this Board
     * @return tiles of this Board
     */
    Tile[][] getTiles() { return tiles; }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile firstTile = getTile(row1, col1);
        Tile secondTile = getTile(row2, col2);
        tiles[row1][col1] = secondTile;
        tiles[row2][col2] = firstTile;

        setChanged();
        notifyObservers();
    }

    @NonNull
    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * The Board Iterator
     */
    private class BoardTileIterator implements Iterator<Tile> {
        int rowIndex = 0;
        int colIndex = 0;

        /**
         * Return the next element of the board
         * @return the next element of the board
         */
        public Tile next() {
            Tile nextTile = getTile(rowIndex, colIndex);
            colIndex++;
            if (colIndex >= NUM_COLS) {
                colIndex = 0;
                rowIndex++;
            }
            return nextTile;
        }

        /**
         * return whether the board has a next element
         * @return whether the board has a next element
         */
        public boolean hasNext() {
            return rowIndex < NUM_ROWS;
        }

    }
}
