// code for implementing a solvable Board was adapted from:
// https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
package a207project.fall18.gamecenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable, Savable {

    /**
     * The board being managed.
     */
    private Board board;


    /**
     * A Stack that keeps track of previous Board states
     */
    private Stack<BoardManager> stack = new Stack<>();

    /**
     * A number that keeps track of the current player's score
     */
    private int score;

    boolean scoreRecorded = false;

    /**
     * Manage a board that has been pre-populated.
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    BoardManager() {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Collections.shuffle(tiles);
        this.board = new Board(tiles);
        while (!isSolvable(tiles)) {
            Collections.shuffle(tiles);
            this.board = new Board(tiles);
        }
    }

    /**
     * A BoardManager with different NUM_COLS and NUM_ROWS
     * @param rows NUM_ROWS for BoardManager
     * @param cols NUM_COLS for BoardManager
     */
    BoardManager(int rows, int cols) {
        Board.NUM_ROWS = rows;
        Board.NUM_COLS = cols;
        List<Tile> tiles = new ArrayList<>();
        int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        Collections.shuffle(tiles);
        this.board = new Board(tiles);
        while (!isSolvable(tiles)) {
            Collections.shuffle(tiles);
            this.board = new Board(tiles);
        }
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        int compareId = 0;
        for (Tile tile: getBoard()) {
            if (tile.getId() != compareId + 1) {
                return false;
            }
            compareId++;
        }
        if (!scoreRecorded) {
            writeScoreToDatabase();
            scoreRecorded = true;
        }
        return true;
    }

    /**
     * return the number of inversion in the array
     * @param array a flat 1-D array for tiles
     * @return the number of inversion
     */
    int getInversionCount(List<Tile> array) {
        int inversionCount = 0;
        for (int i  = 0; i < board.numTiles() - 1; i++) {
            for (int j = i + 1; j < board.numTiles(); j++) {
                if (array.get(i).getId() > array.get(j).getId() && array.get(i).getId() != board.numTiles()) {
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    /**
     * return the position of the blankId Tile counting from the bottom up
     * @return the position of the blankId Tile counting from the bottom up
     */
    int findXPosition() {
        for (int i = Board.NUM_ROWS - 1; i >= 0; i--) {
            for (int j = Board.NUM_COLS - 1; j >= 0; j--){
                if (board.getTile(i, j).getId() == board.numTiles()) {
                    return Board.NUM_ROWS - i;
                }
            }
        }
        return board.numTiles();
    }

    /**
     * return whether the BoardManager is Solvable
     * @param tiles the tile of the Board
     * @return whether the BoardManager is Solvable
     */
    boolean isSolvable(List<Tile> tiles) {
        int invCount = getInversionCount(tiles);
        if (Board.NUM_COLS % 2 == 1) {
            return invCount % 2 == 0;
        }
        else {
            int position = findXPosition();
            if (position % 2 == 1) {
                return invCount % 2 == 0;
            }
            else {
                return invCount % 2 == 1;
            }
        }

    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;
        int blankId = board.numTiles();
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        autoSaved();
        if (isValidTap(position)) {
            if (above != null && above.getId() == blankId) {
                board.swapTiles(row, col, row - 1, col);
            } else if (below != null && below.getId() == blankId) {
                board.swapTiles(row, col, row + 1, col);
            } else if (left != null && left.getId() == blankId) {
                board.swapTiles(row, col, row, col - 1);
            } else {
                board.swapTiles(row, col, row, col + 1);
            }
            score++;
        }
    }

    /**
     * calculate the score of the player playing this sliding tiles game
     * @return score
     */
    @Override
    public int calculateScore() {
        return 1000 - score;
    }

    /**
     * undo the board
     * @return previous BoardManager
     */
    @Override
    public BoardManager undo() {
        if (!stack.empty()){
            return stack.pop();
        }
        return this;
    }

    /**
     * autosave the BoardManager for each move is applied
     */
    @Override
    public void autoSaved() {
        BoardManager newBoardManager = new BoardManager(new Board(board));
        while (!stack.empty()) {
            newBoardManager.stack.add(stack.pop());
        }
        stack.add(newBoardManager);
    }

    /**
     * Writes the user's score to the database
     */
    public void writeScoreToDatabase(){
        HighscoreController highscoreController = new HighscoreController("Sliding Tiles", calculateScore());
    }

}
