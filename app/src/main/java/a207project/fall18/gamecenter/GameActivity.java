package a207project.fall18.gamecenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * An instance of GameActivityFileSaver
     */
    GameActivityFileSaver gameActivityFileSaver = new GameActivityFileSaver();

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(StartingActivity.SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main);
        addUndoButtonListener();
        addSolvedButtonListener();
        addBackButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.NUM_COLS);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Board.NUM_COLS;
                        columnHeight = displayHeight / Board.NUM_ROWS;

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Activate the Undo Button
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = boardManager.undo();
                saveToFile(StartingActivity.SAVE_FILENAME);
                recreate();
            }
        });
    }

    /**
     * Activate the Solved Button
     */
    private void addSolvedButtonListener(){
        Button solvedButton = findViewById(R.id.solved);
        solvedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boardManager.scoreRecorded) {
                    switchWin();
                }else{
                    Toast.makeText(GameActivity.this, "Puzzle Not Solved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Activate the Back Button
     */
    private void addBackButtonListener(){
        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchBack();
            }
        });
    }

    /**
     * Activate the WinActivity
     */
    protected void switchWin(){
        Intent tmp = new Intent(GameActivity.this, WinActivity.class);
        tmp.putExtra("Score", boardManager.calculateScore());
        startActivity(tmp);
    }

    /**
     * Activate the StartingActivity
     */
    protected void switchBack(){
        Intent tmp = new Intent(GameActivity.this, StartingActivity.class);
        startActivity(tmp);
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / Board.NUM_ROWS;
            int col = nextPos % Board.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        saveToFile(StartingActivity.SAVE_FILENAME);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.SAVE_FILENAME);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    public void loadFromFile(String fileName) {
        gameActivityFileSaver.loadFile(fileName, this);
        if (gameActivityFileSaver.boardManager != null) {
            boardManager = gameActivityFileSaver.boardManager;
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        gameActivityFileSaver.boardManager = this.boardManager;
        gameActivityFileSaver.saveFile(fileName, this);
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
