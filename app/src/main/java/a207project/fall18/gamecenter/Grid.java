package a207project.fall18.gamecenter;
//https://github.com/tpcstld/2048

import java.util.ArrayList;
import java.util.Stack;

/**
 * The grid for the 2048 game
 */

public class Grid {
    /**
     * The current Grid state
     */
    protected final Block[][] field;

    /**
     * The previous Grid state
     */
    final Block[][] undoField;

    /**
     * A Temporary Field
     */
    private final Block[][] bufferField;

    /**
     * A Stack that keeps track of previous Grid states
     */
    private Stack<Block[][]> stack = new Stack<>();

    /**
     * A new grid of dimension sizeX x sizeY
     * @param sizeX number of columns
     * @param sizeY number of rows
     */
    Grid(int sizeX, int sizeY) {
        field = new Block[sizeX][sizeY];
        undoField = new Block[sizeX][sizeY];
        bufferField = new Block[sizeX][sizeY];
        clearGrid();
        clearUndoGrid();
    }

    /**
     * Return the Stack of Grid
     * @return the stack
     */
    Stack<Block[][]> getStack(){
        return stack;
    }

    /**
     * Returns a copy of a 2D Array
     * @param array a 2D array
     * @return a copy of array
     */
    Block[][] clone2D(Block[][] array){
        Block[][] copy = new Block [array.length][array.length];
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array.length; j++) {
                copy[i][j] = array[i][j];
            }
        }
        return copy;
    }

    /**
     * Replace stack with a new empty stack
     */
    void clearStack(){
        Stack<Block[][]> new_stack = new Stack<>();
        stack = new_stack;
    }

    /**
     * Add previous field to the Stack
     */
    void prev_fields(){
        stack.add(clone2D(undoField));
    }

    /**
     * Returns any random available cell in the field
     * @return a cell that is empty
     */
    Cell randomAvailableCell() {
        ArrayList<Cell> availableCells = getAvailableCells();
        if (availableCells.size() >= 1) {
            return availableCells.get((int) Math.floor(Math.random() * availableCells.size()));
        }
        return null;
    }

    /**
     * Returns a list of available cells
     * @return list of available cells
     */
    ArrayList<Cell> getAvailableCells() {
        ArrayList<Cell> availableCells = new ArrayList<>();
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] == null) {
                    availableCells.add(new Cell(xx, yy));
                }
            }
        }
        return availableCells;
    }

    /**
     * Return whether if there are any available cells
     * @return whether if there are any available cells
     */
    boolean isCellsAvailable() {
        return (getAvailableCells().size() >= 1);
    }

    /**
     * Return whether if cell is available
     * @param cell the cell of the 2048 game
     * @return whether if cell is available
     */
    boolean isCellAvailable(Cell cell) {
        return !isCellOccupied(cell);
    }

    /**
     * Return whether the cell has a value
     * @param cell the cell of the 2048 game
     * @return whether the cell has a value
     */
    boolean isCellOccupied(Cell cell) {
        return (getCellContent(cell) != null);
    }

    /**
     * Return a Block with the corresponding cell
     * @param cell the cell in the 2048 game
     * @return a Block with the corresponding cell
     */
    Block getCellContent(Cell cell) {
        if (cell != null && isCellWithinBounds(cell)) {
            return field[cell.getX()][cell.getY()];
        } else {
            return null;
        }
    }

    /**
     * Return a Block at position x and y
     * @param x the row number
     * @param y the column number
     * @return a Block at row number x and column number y
     */
    Block getCellContent(int x, int y) {
        if (isCellWithinBounds(x, y)) {
            return field[x][y];
        } else {
            return null;
        }
    }

    /**
     * Return whether the cell is within the bounds of field
     * @param cell the cell of the 2048 game
     * @return whether the cell is within the bounds of field
     */
    boolean isCellWithinBounds(Cell cell) {
        return 0 <= cell.getX() && cell.getX() < field.length
                && 0 <= cell.getY() && cell.getY() < field[0].length;
    }

    /**
     * Return whether the cell at row number x and column number y is within bounds
     * @param x row number
     * @param y column number
     * @return whether the cell at row number x and column number y is within bounds
     */
    private boolean isCellWithinBounds(int x, int y) {
        return 0 <= x && x < field.length
                && 0 <= y && y < field[0].length;
    }

    /**
     * Inserting a tile into the field
     * @param block the block on the 2048 game
     */
    void insertBlock(Block block) {
        field[block.getX()][block.getY()] = block;
    }

    /**
     * Remove a block in the field
     * @param block the block in the 2048 game
     */
    void removeBlock(Block block) {
        field[block.getX()][block.getY()] = null;
    }

    /**
     * Save the block into the undoField
     */
    void saveBlocks() {
        for (int xx = 0; xx < bufferField.length; xx++) {
            for (int yy = 0; yy < bufferField[0].length; yy++) {
                if (bufferField[xx][yy] == null) {
                    undoField[xx][yy] = null;
                } else {
                    undoField[xx][yy] = new Block(xx, yy, bufferField[xx][yy].getValue());
                }
            }
        }
        prev_fields();
    }

    /**
     * Preparing the save the blocks
     */
    void prepareSaveBlocks() {
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] == null) {
                    bufferField[xx][yy] = null;
                } else {
                    bufferField[xx][yy] = new Block(xx, yy, field[xx][yy].getValue());
                }
            }
        }
    }

    /**
     * Revert the tiles back to previous position
     */
    void revertBlocks() {
        Block[][] prev_undoField = stack.pop();

        for (int xx = 0; xx < prev_undoField.length; xx++) {
            for (int yy = 0; yy < prev_undoField[0].length; yy++) {
                if (prev_undoField[xx][yy] == null) {
                    field[xx][yy] = null;
                } else {
                    field[xx][yy] = new Block(xx, yy, prev_undoField[xx][yy].getValue());
                }
            }
        }
    }

    /**
     * Clear the Grid
     */
    void clearGrid() {
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                field[xx][yy] = null;
            }
        }
    }

    /**
     * Clear the undoField
     */
    private void clearUndoGrid() {
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                undoField[xx][yy] = null;
            }
        }
    }
}
