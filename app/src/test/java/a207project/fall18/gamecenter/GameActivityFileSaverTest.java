package a207project.fall18.gamecenter;

import android.app.Activity;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameActivityFileSaverTest {

    /**
     * An instance of GameActivityFileSaver
     */
    private GameActivityFileSaver gameActivityFileSaver;

    /**
     * An instance of GameActivity
     */
    private GameActivity gameActivity;

    /**
     * Set Up before testing
     */
    @Before
    public void setUp() {
        gameActivity = mock(GameActivity.class);
        gameActivityFileSaver = new GameActivityFileSaver();
        gameActivityFileSaver.boardManager = new BoardManager();
    }

    /**
     * Test whether saveToFile and loadFromFile works
     */
    @Test
    public void loadFile() {
        gameActivity.saveToFile(GameActivityFileSaver.privateTestFile);
        gameActivityFileSaver.boardManager.getBoard().swapTiles(1, 1, 2, 2);
        gameActivity.loadFromFile(GameActivityFileSaver.privateTestFile);
    }
}