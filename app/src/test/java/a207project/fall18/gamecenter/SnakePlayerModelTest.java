package a207project.fall18.gamecenter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SnakePlayerModelTest {

    /**
     * An instance of SnakePlayerModel
     */
    private SnakePlayerModel snakePlayerModel;

    /**
     * Set up before testing
     */
    @Before
    public void setUp() {
        snakePlayerModel = new SnakePlayerModel();
    }

    /**
     * Test whether setSpeed works
     */
    @Test
    public void setSpeed() {
        assertEquals(0, snakePlayerModel.getSpeed());
        snakePlayerModel.setSpeed(100);
        assertEquals(100, snakePlayerModel.getSpeed());
    }

    /**
     * Test whether getSpeed works
     */
    @Test
    public void getSpeed() {
        snakePlayerModel.setSpeed(3);
        assertEquals(3, snakePlayerModel.getSpeed());
    }

    /**
     * Test whether setSnakeLength works
     */
    @Test
    public void setSnakeLength() {
        assertEquals(3, snakePlayerModel.getSnakeLength());
        snakePlayerModel.setSnakeLength(4);
        assertEquals(4, snakePlayerModel.getSnakeLength());
    }

    /**
     * Test whether getSnakeLength works
     */
    @Test
    public void getSnakeLength() {
        snakePlayerModel.setSnakeLength(100);
        assertEquals(100, snakePlayerModel.getSnakeLength());
    }

    /**
     * Test whether getSnakeDirection works
     */
    @Test
    public void getSnakeDirection() {
        snakePlayerModel.resetSnakeDirection();
        assertEquals(GameType.RIGHT, snakePlayerModel.getSnakeDirection());
    }

    /**
     * Test whether setSnakeDirection works
     */
    @Test
    public void setSnakeDirection() {
        snakePlayerModel.resetSnakeDirection();
        snakePlayerModel.setSnakeDirection(GameType.LEFT);
        assertEquals(GameType.RIGHT, snakePlayerModel.getSnakeDirection());
        snakePlayerModel.setSnakeDirection(GameType.TOP);
        assertEquals(GameType.TOP, snakePlayerModel.getSnakeDirection());
    }

    /**
     * Test whether resetSnakeDirection works
     */
    @Test
    public void resetSnakeDirection() {
        snakePlayerModel.resetSnakeDirection();
        assertEquals(GameType.RIGHT, snakePlayerModel.getSnakeDirection());
    }
}