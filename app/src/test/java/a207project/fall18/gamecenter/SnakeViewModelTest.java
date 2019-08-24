package a207project.fall18.gamecenter;

import android.graphics.Color;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SnakeViewModelTest {

    /**
     * An instance of SnakeViewModel
     */
    private SnakeViewModel snakeViewModel;

    /**
     * Set up before testing
     */
    @Before
    public void setUp() {
        snakeViewModel = new SnakeViewModel(3);
    }

    /**
     * Test whether setGridSize works
     */
    @Test
    public void setGridSize() {
        snakeViewModel.setGridSize(10);
        assertEquals(10, snakeViewModel.getGridSize());
    }

    /**
     * Test whether getGridSize works
     */
    @Test
    public void getGridSize() {
        snakeViewModel.setGridSize(100);
        assertEquals(100, snakeViewModel.getGridSize());
    }

    /**
     * Test whether getEndGame works
     */
    @Test
    public void getEndGame() {
        assertTrue(snakeViewModel.getEndGame());
    }

    /**
     * Test whether setEndGame works
     */
    @Test
    public void setEndGame() {
        snakeViewModel.setEndGame(true);
        assertTrue(snakeViewModel.getEndGame());
    }

    /**
     * Test handleSnakeTail
     */
    @Test
    public void handleSnakeTail() {
        snakeViewModel.handleSnakeTail();
        assertEquals(3, snakeViewModel.snakeGameModel.player.getSnakeLength());
    }

    /**
     * Test whether generateFood works
     */
    @Test
    public void generateFood() {
        snakeViewModel.generateFood();
        assertNotEquals(-1, snakeViewModel.snakeGameModel.foodPosition.getX());
        assertNotEquals(-1, snakeViewModel.snakeGameModel.foodPosition.getY());
    }

    /**
     * Test whether moveSnake works
     */
    @Test
    public void moveSnake() {
        assertEquals(GameType.RIGHT, snakeViewModel.snakeGameModel.player.getSnakeDirection());
        assertEquals(10, snakeViewModel.snakeGameModel.player.snakeHeader.getX());
        snakeViewModel.moveSnake();
        assertEquals(11, snakeViewModel.snakeGameModel.player.snakeHeader.getX());
        snakeViewModel.snakeGameModel.player.snakeHeader.setX(1000000000);
        snakeViewModel.moveSnake();
        assertEquals(0, snakeViewModel.snakeGameModel.player.snakeHeader.getX());
        snakeViewModel.changeSnakeDirection(GameType.BOTTOM);
        assertEquals(GameType.BOTTOM, snakeViewModel.snakeGameModel.player.getSnakeDirection());
        assertEquals(10, snakeViewModel.snakeGameModel.player.snakeHeader.getY());
        snakeViewModel.moveSnake();
        assertEquals(11, snakeViewModel.snakeGameModel.player.snakeHeader.getY());
        snakeViewModel.snakeGameModel.player.snakeHeader.setY(1000000000);
        snakeViewModel.moveSnake();
        assertEquals(0, snakeViewModel.snakeGameModel.player.snakeHeader.getY());
        snakeViewModel.changeSnakeDirection(GameType.LEFT);
        assertEquals(GameType.LEFT, snakeViewModel.snakeGameModel.player.getSnakeDirection());
        assertEquals(0, snakeViewModel.snakeGameModel.player.snakeHeader.getX());
        snakeViewModel.moveSnake();
        assertEquals(19, snakeViewModel.snakeGameModel.player.snakeHeader.getX());
        snakeViewModel.snakeGameModel.player.snakeHeader.setX(1000000000);
        snakeViewModel.moveSnake();
        assertEquals(0, snakeViewModel.snakeGameModel.player.snakeHeader.getY());
        snakeViewModel.changeSnakeDirection(GameType.TOP);
        assertEquals(GameType.TOP, snakeViewModel.snakeGameModel.player.getSnakeDirection());
        assertEquals(0, snakeViewModel.snakeGameModel.player.snakeHeader.getY());
        snakeViewModel.moveSnake();
        assertEquals(19, snakeViewModel.snakeGameModel.player.snakeHeader.getY());
        snakeViewModel.snakeGameModel.player.snakeHeader.setY(100);
        snakeViewModel.moveSnake();
        assertEquals(99, snakeViewModel.snakeGameModel.player.snakeHeader.getY());
    }

    /**
     * Test whether reStartGame works
     */
    @Test
    public void reStartGame() {
        assertTrue(snakeViewModel.getEndGame());
        snakeViewModel.reStartGame();
        assertEquals(1, snakeViewModel.snakePositions.size());
        assertFalse(snakeViewModel.getEndGame());
    }

    /**
     * Test whether checkCollision works
     */
    @Test
    public void checkCollision() {
        snakeViewModel.checkCollision();
        assertTrue(snakeViewModel.getEndGame());
    }

    /**
     * Test whether refreshGridSquare
     */
    @Test
    public void refreshGridSquare() {
        snakeViewModel.refreshGridSquare();
        snakeViewModel.gridSquare.get(10).get(10).setType(GameType.FOOD);
        assertEquals(Color.BLUE, snakeViewModel.gridSquare.get(10).get(10).getColor());
    }
}