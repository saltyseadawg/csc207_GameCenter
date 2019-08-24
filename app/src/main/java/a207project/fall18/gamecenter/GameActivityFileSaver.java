package a207project.fall18.gamecenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static android.content.Context.MODE_PRIVATE;

class GameActivityFileSaver {

    /**
     * An instance of BoardManager.
     */
    BoardManager boardManager;

    static String privateTestFile = "test_file.ser";
    GameActivityFileSaver() {}

    /**
     * Loading a file.
     */
    public void loadFile(String fileName, Context context) {
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                if (fileName.equals(StartingActivity.SAVE_FILENAME) || fileName.equals(privateTestFile)) {
                    ObjectInputStream input = new ObjectInputStream(inputStream);
                    boardManager = (BoardManager) input.readObject();
                    inputStream.close();
                }
            }
        }
        catch (FileNotFoundException e) {
            Log.e("authenticating activity", "File not found: " + e.toString());
        }
        catch (IOException e) {
            Log.e("authenticating activity", "Cannot read file" + e.toString());
        }
        catch (ClassNotFoundException e) {
            Log.e("authenticating activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Saving a file.
     */
    public void saveFile(String fileName, Context context) {
        try {
            if (fileName.equals(StartingActivity.SAVE_FILENAME) || fileName.equals(privateTestFile)) {
                ObjectOutputStream outputStream = new ObjectOutputStream(
                        context.openFileOutput(fileName, MODE_PRIVATE));
                outputStream.writeObject(boardManager);
                outputStream.close();
            }
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
