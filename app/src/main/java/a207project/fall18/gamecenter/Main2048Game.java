package a207project.fall18.gamecenter;
//https://github.com/tpcstld/2048

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Main2048Game {

    static final int SPAWN_ANIMATION = -1;
    static final int MOVE_ANIMATION = 0;
    static final int MERGE_ANIMATION = 1;

    static final int FADE_GLOBAL_ANIMATION = 0;
    private static final long MOVE_ANIMATION_TIME = Main2048View.BASE_ANIMATION_TIME;
    private static final long SPAWN_ANIMATION_TIME = Main2048View.BASE_ANIMATION_TIME;
    private static final long NOTIFICATION_DELAY_TIME = MOVE_ANIMATION_TIME + SPAWN_ANIMATION_TIME;
    private static final long NOTIFICATION_ANIMATION_TIME = Main2048View.BASE_ANIMATION_TIME * 5;
    private static final int startingMaxValue = 2048;
    //Odd state = game is not active
    //Even state = game is active
    //Win state = active state + 1
    private static final int GAME_WIN = 1;
    private static final int GAME_LOST = -1;
    private static final int GAME_NORMAL = 0;
    int gameState = GAME_NORMAL;
    int lastGameState = GAME_NORMAL;
    private int bufferGameState = GAME_NORMAL;
    private static final int GAME_ENDLESS = 2;
    private static final int GAME_ENDLESS_WON = 3;
    private static final String HIGH_SCORE = "high score";
    private static final String FIRST_RUN = "first run";
    private static int endingMaxValue;
    final int numSquaresX = 4;
    final int numSquaresY = 4;
    private final Context mContext;
    /**
     * Instance of Main2048View
     */
    private final Main2048View mView;
    public Grid grid = null;
    AnimationGrid aGrid;
    boolean canUndo;
    public long score = 0;
    long highScore = 0;
    long lastScore = 0;
    private long bufferScore = 0;
    public Stack<Long> stack = new Stack<>();

    Main2048Game(Context context, Main2048View view) {
        mContext = context;
        mView = view;
        endingMaxValue = (int) Math.pow(2, view.numCellTypes - 1);
    }

    /**
     * Creates a new game
     */
    void newGame() {
        if (grid == null) {
            grid = new Grid(numSquaresX, numSquaresY);
        } else {
            grid.clearGrid();
            grid.clearStack();
        }
        aGrid = new AnimationGrid(numSquaresX, numSquaresY);
        highScore = getHighScore();
        if (score >= highScore) {
            highScore = score;
            recordHighScore();
        }
        score = 0;
        gameState = GAME_NORMAL;
        addStartBlocks();
        mView.showHelp = firstRun();
        mView.refreshLastTime = true;
        mView.resyncTime();
        mView.invalidate();
    }

    /**
     * Creates first block of the game (value 2)
     */
    private void addStartBlocks() {
        int startBlocks = 2;
        for (int xx = 0; xx < startBlocks; xx++) {
            this.addRandomBlock();
        }
    }

    /**
     * Finds a random empty spot to spawn a new random block
     * (value either 2 or 4)
     */
    private void addRandomBlock() {
        if (grid.isCellsAvailable()) {
            int value = Math.random() < 0.9 ? 2 : 4;
            Block block = new Block(grid.randomAvailableCell(), value);
            spawnBlock(block);
        }
    }

    /**
     * Takes block and adds it a
     * @param block new block to spawn
     */
    private void spawnBlock(Block block) {
        grid.insertBlock(block);
        aGrid.startAnimation(block.getX(), block.getY(), SPAWN_ANIMATION,
                SPAWN_ANIMATION_TIME, MOVE_ANIMATION_TIME, null); //Direction: -1 = EXPANDING
    }

    /**
     * Records highscore
     */
    private void recordHighScore() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(HIGH_SCORE, highScore);
        editor.commit();
    }

    /**
     * Returns a duplicate of lastScore
     * @return copy of lastScore
     */
    private long dup_score(){
        Long copy = new Long(lastScore);
        return copy;
    }

    /**
     * Returns the highscore of the game
     * @return current highscore
     */
    private long getHighScore() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        return settings.getLong(HIGH_SCORE, -1);
    }

    private boolean firstRun() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (settings.getBoolean(FIRST_RUN, true)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(FIRST_RUN, false);
            editor.commit();
            return true;
        }
        return false;
    }


    /**
     * Prepares the blocks.
     */
    private void prepareBlocks() {
        for (Block[] array : grid.field) {
            for (Block block : array) {
                if (grid.isCellOccupied(block)) {
                    block.setMergedFrom(null);
                }
            }
        }
    }

    /**
     * Updates block being moved
     * @param block block being moved
     * @param cell values of the block
     */
    private void moveBlock(Block block, Cell cell) {
        grid.field[block.getX()][block.getY()] = null;
        grid.field[cell.getX()][cell.getY()] = block;
        block.updatePosition(cell);
    }

    /**
     * Saves previous state for undo
     */
    private void saveUndoState() {
        grid.saveBlocks();
        canUndo = true;
        lastScore = bufferScore;
        lastGameState = bufferGameState;
    }

    /**
     * Prepares the saved undo state
     */
    private void prepareUndoState() {
        grid.prepareSaveBlocks();
        bufferScore = score;
        bufferGameState = gameState;
    }

    /**
     * Checks if the undo is available and reverts to the the last state if possible
     */
    void revertUndoState() {
        if (grid.getStack().isEmpty()) {
            canUndo = false;
        }
        if (canUndo) {
            aGrid.cancelAnimations();
            grid.revertBlocks();
            score = lastScore;
            score = stack.pop();
            gameState = lastGameState;
            mView.refreshLastTime = true;
            mView.invalidate();
        }
    }

    /**
     * Returns whether or not the game is won
     * @return true if game is won
     */
    boolean gameWon() {
        return (gameState > 0 && gameState % 2 != 0);
    }

    /**
     * Returns whether or not the game is lost
     * @return true if game is lost
     */
    boolean gameLost() {
        return (gameState == GAME_LOST);
    }

    /**
     * Returns whether or not the game is active
     * @return true if game is not won or lost
     */
    boolean isActive() {
        return !(gameWon() || gameLost());
    }

    /**
     * Moving the block and updating the game information
     * @param direction direction the block is moving
     */
    public void move(int direction) {
        aGrid.cancelAnimations();
        // 0: up, 1: right, 2: down, 3: left
        if (!isActive()) {
            return;
        }
        prepareUndoState();
        Cell vector = getVector(direction);
        List<Integer> traversalsX = buildTraversalsX(vector);
        List<Integer> traversalsY = buildTraversalsY(vector);
        boolean moved = false;

        prepareBlocks();

        for (int xx : traversalsX) {
            for (int yy : traversalsY) {
                Cell cell = new Cell(xx, yy);
                Block block = grid.getCellContent(cell);

                if (block != null) {
                    Cell[] positions = findFarthestPosition(cell, vector);
                    Block next = grid.getCellContent(positions[1]);

                    if (next != null && next.getValue() == block.getValue() &&
                            next.getMergedFrom() == null) {
                        Block merged = new Block(positions[1], block.getValue() * 2);
                        Block[] temp = {block, next};
                        merged.setMergedFrom(temp);

                        grid.insertBlock(merged);
                        grid.removeBlock(block);

                        // Converge the two blocks' positions
                        block.updatePosition(positions[1]);

                        int[] extras = {xx, yy};
                        aGrid.startAnimation(merged.getX(), merged.getY(), MOVE_ANIMATION,
                                MOVE_ANIMATION_TIME, 0, extras); //Direction: 0 = MOVING MERGED
                        aGrid.startAnimation(merged.getX(), merged.getY(), MERGE_ANIMATION,
                                SPAWN_ANIMATION_TIME, MOVE_ANIMATION_TIME, null);

                        // Update the score
                        score = score + merged.getValue();
                        highScore = Math.max(score, highScore);

                        // The mighty 2048 block
                        if (merged.getValue() >= winValue() && !gameWon()) {
                            gameState = gameState + GAME_WIN; // Set win state
                            endGame();
                        }
                    } else {
                        moveBlock(block, positions[0]);
                        int[] extras = {xx, yy, 0};
                        aGrid.startAnimation(positions[0].getX(), positions[0].getY(),
                                MOVE_ANIMATION, MOVE_ANIMATION_TIME, 0, extras); //Direction: 1 = MOVING NO MERGE
                    }

                    if (!positionsEqual(cell,block)) {
                        moved = true;
                    }
                }
            }
        }

        if (moved) {
            saveUndoState();
            addRandomBlock();
            checkLose();
            stack.add(dup_score());

        }
        mView.resyncTime();
        mView.invalidate();
    }

    /**
     * Checking if the game is lost (no moves an game is not won)
     */
    private void checkLose() {
        if (!movesAvailable() && !gameWon()) {
            gameState = GAME_LOST;
            endGame();
        }
    }

    /**
     * Ending the game and recording the score if it is high enough and sending the score to database
     */
    private void endGame() {
        aGrid.startAnimation(-1, -1, FADE_GLOBAL_ANIMATION, NOTIFICATION_ANIMATION_TIME,
                NOTIFICATION_DELAY_TIME, null);
        if (score >= highScore) {
            highScore = score;
            recordHighScore();
        }
        writeScoreToDatabase();
    }

    // Movement methods

    private Cell getVector(int direction) {
        Cell[] map = {
                new Cell(0, -1), // up
                new Cell(1, 0),  // right
                new Cell(0, 1),  // down
                new Cell(-1, 0)  // left
        };
        return map[direction];
    }

    private List<Integer> buildTraversalsX(Cell vector) {
        List<Integer> traversals = new ArrayList<>();

        for (int xx = 0; xx < numSquaresX; xx++) {
            traversals.add(xx);
        }
        if (vector.getX() == 1) {
            Collections.reverse(traversals);
        }

        return traversals;
    }

    private List<Integer> buildTraversalsY(Cell vector) {
        List<Integer> traversals = new ArrayList<>();

        for (int xx = 0; xx < numSquaresY; xx++) {
            traversals.add(xx);
        }
        if (vector.getY() == 1) {
            Collections.reverse(traversals);
        }

        return traversals;
    }

    private Cell[] findFarthestPosition(Cell cell, Cell vector) {
        Cell previous;
        Cell nextCell = new Cell(cell.getX(), cell.getY());
        do {
            previous = nextCell;
            nextCell = new Cell(previous.getX() + vector.getX(),
                    previous.getY() + vector.getY());
        } while (grid.isCellWithinBounds(nextCell) && grid.isCellAvailable(nextCell));

        return new Cell[]{previous, nextCell};
    }

    private boolean movesAvailable() {
        return grid.isCellsAvailable() || blockMatchesAvailable();
    }

    private boolean blockMatchesAvailable() {
        Block block;

        for (int xx = 0; xx < numSquaresX; xx++) {
            for (int yy = 0; yy < numSquaresY; yy++) {
                block = grid.getCellContent(new Cell(xx, yy));

                if (block != null) {
                    for (int direction = 0; direction < 4; direction++) {
                        Cell vector = getVector(direction);
                        Cell cell = new Cell(xx + vector.getX(), yy + vector.getY());

                        Block other = grid.getCellContent(cell);

                        if (other != null && other.getValue() == block.getValue()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean positionsEqual(Cell first, Cell second) {
        return first.getX() == second.getX() && first.getY() == second.getY();
    }

    private int winValue() {
        if (!canContinue()) {
            return endingMaxValue;
        } else {
            return startingMaxValue;
        }
    }

    void setEndlessMode() {
        gameState = GAME_ENDLESS;
        mView.invalidate();
        mView.refreshLastTime = true;
    }

    boolean canContinue() {
        return !(gameState == GAME_ENDLESS || gameState == GAME_ENDLESS_WON);
    }

    /**
     * Writes the user's score to the database
     */
    void writeScoreToDatabase(){
        HighscoreController highscoreController = new HighscoreController("2048",
                (int) score);
    }
}
