package a207project.fall18.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file.ser";
    /**
     * The board manager.
     */
    public static final String SAVE_SIZE = "size_file.ser";

    /**
     *  An instance of BoardManager
     */
    private BoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new BoardManager(Board.NUM_ROWS, Board.NUM_COLS);
        loadFromFile(SAVE_SIZE);
        saveToFile(SAVE_SIZE);
        loadFromFile(SAVE_FILENAME);
        saveToFile(SAVE_FILENAME);

        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
        addBoard3ButtonListener();
        addBoard4ButtonListener();
        addBoard5ButtonListener();
        addBackButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager();
                saveToFile(SAVE_FILENAME);
                saveToFile(SAVE_SIZE);
                switchToGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        final Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(SAVE_SIZE);
                saveToFile(SAVE_SIZE);
                loadFromFile(SAVE_FILENAME);
                saveToFile(SAVE_FILENAME);
                makeToastLoadedText();
                switchToGame();
            }
        });
    }

    /**
     * Activate the button for a 3x3 Board
     */
    private void addBoard3ButtonListener() {
        Button button3 = findViewById(R.id.Button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(3, 3);
                saveToFile(SAVE_FILENAME);
                saveToFile(SAVE_SIZE);
                switchToGame();
            }
        });
    }

    /**
     * Activate the Button for a 4x4 Board
     */
    private void addBoard4ButtonListener() {
        Button button4 = findViewById(R.id.Button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(4, 4);
                saveToFile(SAVE_FILENAME);
                saveToFile(SAVE_SIZE);
                switchToGame();
            }
        });
    }

    /**
     * Activate the Button for a 5x5 Board
     */
    private void addBoard5ButtonListener() {
        Button button5 = findViewById(R.id.Button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(5, 5);
                saveToFile(SAVE_FILENAME);
                saveToFile(SAVE_SIZE);
                switchToGame();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(SAVE_FILENAME);
                saveToFile(SAVE_SIZE);
                makeToastSavedText();
            }
            });
    }

    /**
     * Activate the back button.
     */
    private void addBackButtonListener() {
        Button backButton = findViewById(R.id.goBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchBack();
            }
        });
    }

    /**
     * Activate the GameCenterActivity
     */
    private void switchBack(){
        Intent tmp = new Intent(StartingActivity.this, GameCenterActivity.class);
        startActivity(tmp);
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(SAVE_FILENAME);
        loadFromFile(SAVE_SIZE);
    }

    /**
     * Dispatch onPause to fragments
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(SAVE_FILENAME);
        saveToFile(SAVE_SIZE);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(SAVE_FILENAME);
        saveToFile(SAVE_SIZE);
        startActivity(tmp);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                if (fileName.equals(SAVE_FILENAME)) {
                    ObjectInputStream input = new ObjectInputStream(inputStream);
                    boardManager = (BoardManager) input.readObject();
                    inputStream.close();
                }
                else {
                    ObjectInputStream input = new ObjectInputStream(inputStream);
                    Board.NUM_ROWS = (Integer) input.readObject();
                    Board.NUM_COLS = Board.NUM_ROWS;
                    inputStream.close();
                }
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " +
                    e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            if (fileName.equals(SAVE_FILENAME)) {
                ObjectOutputStream outputStream = new ObjectOutputStream(
                        this.openFileOutput(fileName, MODE_PRIVATE));
                outputStream.writeObject(boardManager);
                outputStream.close();
            }
            else if (fileName.equals(SAVE_SIZE)) {
                ObjectOutputStream outputStream = new ObjectOutputStream(
                        this.openFileOutput(fileName, MODE_PRIVATE));
                outputStream.writeObject(Board.NUM_COLS);
                outputStream.close();
            }
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
