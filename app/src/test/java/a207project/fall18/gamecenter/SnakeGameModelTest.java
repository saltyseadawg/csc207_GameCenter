package a207project.fall18.gamecenter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SnakeGameModelTest {

    /**
     * An instance of SnakeGameModel
     */
    private SnakeGameModel snakeGameModel;

    /**
     * Set up before testing
     */
    @Before
    public void setUp() {
        snakeGameModel = new SnakeGameModel();
    }

    /**
     * Test whether snakeEats works
     */
    @Test
    public void snakeEats() {
        snakeGameModel.snakeEats();
        assertEquals(4, snakeGameModel.player.getSnakeLength());
    }

    /**
     * Test whether reset works
     */
    @Test
    public void reset() {
        snakeGameModel.reset();
        assertEquals(10, snakeGameModel.player.snakeHeader.getX());
        assertEquals(10, snakeGameModel.player.snakeHeader.getY());
        assertEquals(0, snakeGameModel.foodPosition.getY());
        assertEquals(0, snakeGameModel.foodPosition.getX());
        snakeGameModel.player.snakeHeader.setY(20);
        snakeGameModel.player.snakeHeader.setX(20);
        snakeGameModel.foodPosition.setX(10);
        snakeGameModel.foodPosition.setY(10);
        snakeGameModel.reset();
        assertEquals(10, snakeGameModel.player.snakeHeader.getX());
        assertEquals(10, snakeGameModel.player.snakeHeader.getY());
        assertEquals(0, snakeGameModel.foodPosition.getY());
        assertEquals(0, snakeGameModel.foodPosition.getX());
    }
}