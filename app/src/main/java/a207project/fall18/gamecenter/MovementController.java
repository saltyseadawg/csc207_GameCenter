package a207project.fall18.gamecenter;

import android.content.Context;
import android.widget.Toast;


public class MovementController {

    /**
     * An instance of BoardManager, set to default null
     */
    private BoardManager boardManager = null;

    public MovementController() {
    }

    /**
     * Set boardManager as boardManager
     */
    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Processes a tap in the game
     */
    public void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
