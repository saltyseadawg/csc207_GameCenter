package a207project.fall18.gamecenter;
//https://github.com/zhangman523/AndroidGameSnake

import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeViewModel{

    /**
     * Instance of a SnakeGameModel
     */
    @VisibleForTesting
    SnakeGameModel snakeGameModel;
    /**
     * The size of the grid.
     */
    private int gridSize = 20;
    /**
     * A List of GridSquares for the Snake board.
     */
    List<List<GridSquare>> gridSquare;
    /**
     * A list containing the GridPositions for the snake's body to travel.
     */
    List<GridPosition> snakePositions;

    SnakeViewModel(int speed){
        gridSquare = new ArrayList<>();
        snakePositions = new ArrayList<>();
        snakeGameModel = new SnakeGameModel();
        snakeGameModel.player.setSpeed(speed);
        List<GridSquare> squares;
        for (int i = 0; i < gridSize; i++) {
            squares = new ArrayList<>();
            for (int j = 0; j < gridSize; j++) {
                squares.add(new GridSquare(GameType.GRID));
            }
            gridSquare.add(squares);
        }
        snakeGameModel.player.snakeHeader = new GridPosition(10, 10);
        snakePositions.add(new GridPosition(snakeGameModel.player.snakeHeader.getX(),
                snakeGameModel.player.snakeHeader.getY()));
        snakeGameModel.foodPosition = new GridPosition(0, 0);
        setEndGame(true);
    }
    /**
     * Set the gridSize to size
     */
    void setGridSize(int size){
        gridSize = size;
    }

    /**
     * Returns the grid size
     */
    int getGridSize(){
        return gridSize;
    }

    /**
     * Returns true if the game is over
     */
    boolean getEndGame(){
        return snakeGameModel.isEndGame;
    }

    /**
     * Sets the state of the game to a boolean value
     */
    void setEndGame(boolean value){
        snakeGameModel.isEndGame = value;
    }

    /**
     * Updates mGridSquare according to the updated positions of the snake's tail.
     */
    void handleSnakeTail() {
        int snakeLength = snakeGameModel.player.getSnakeLength();
        for (int i = snakePositions.size() - 1; i >= 0; i--) {
            if (snakeLength > 0) {
                snakeLength--;
            } else {//set the grid over the length to GameType.GRID
                GridPosition position = snakePositions.get(i);
                gridSquare.get(position.getX()).get(position.getY()).setType(GameType.GRID);
            }
        }
        snakeLength = snakeGameModel.player.getSnakeLength();
        for (int i = snakePositions.size() - 1; i >= 0; i--) {
            if (snakeLength > 0) {
                snakeLength--;
            } else {
                snakePositions.remove(i);
            }
        }
    }
    /**
     * Generates the position of the next food
     */
    void generateFood() {
        Random random = new Random();
        int foodX = random.nextInt(gridSize - 1);
        int foodY = random.nextInt(gridSize - 1);
        for (int i = 0; i < snakePositions.size() - 1; i++) {
            if (foodX == snakePositions.get(i).getX() && foodY == snakePositions.get(i).getY()) {
                //generate new food position if food is on the snake
                foodX = random.nextInt(gridSize - 1);
                foodY = random.nextInt(gridSize - 1);
                //resets the loop to check food position again
                i = 0;
            }
        }
        snakeGameModel.foodPosition.setX(foodX);
        snakeGameModel.foodPosition.setY(foodY);
        refreshFood(snakeGameModel.foodPosition);
    }

    private void refreshFood(GridPosition foodPosition) {
        gridSquare.get(foodPosition.getX()).get(foodPosition.getY()).setType(GameType.FOOD);
    }

    /**
     * Moves the snake by changing the grid positions of the snake parts
     */
    void moveSnake() {
        int snakeDirection = snakeGameModel.player.getSnakeDirection();
        switch (snakeDirection) {
            case GameType.LEFT:
                // checks if snake has reached the boundary of the grid
                if (snakeGameModel.player.snakeHeader.getX() - 1 < 0) {//
                    // if it reaches the far left, let snake cross the screen to the far right
                    snakeGameModel.player.snakeHeader.setX(gridSize - 1);
                } else {
                    // otherwise the snake continues on
                    snakeGameModel.player.snakeHeader.setX(snakeGameModel
                            .player.snakeHeader.getX() - 1);
                }
                snakePositions.add(new GridPosition(snakeGameModel.player.snakeHeader.getX(),
                        snakeGameModel.player.snakeHeader.getY()));
                break;
            case GameType.TOP:
                // checks if snake has reached the boundary of the grid
                if (snakeGameModel.player.snakeHeader.getY() - 1 < 0) {
                    // if it reaches the top, let snake cross the screen to the bottom
                    snakeGameModel.player.snakeHeader.setY(gridSize - 1);
                } else {
                    // otherwise the snake continues on
                    snakeGameModel.player.snakeHeader.setY(snakeGameModel.player
                            .snakeHeader.getY() - 1);
                }
                snakePositions.add(new GridPosition(snakeGameModel.player.snakeHeader.getX(),
                        snakeGameModel.player.snakeHeader.getY()));
                break;
            case GameType.RIGHT:
                // checks if snake has reached the boundary of the grid
                if (snakeGameModel.player.snakeHeader.getX() + 1 >= gridSize) {
                    // if it reaches the far right, let snake cross the screen to the far left
                    snakeGameModel.player.snakeHeader.setX(0);
                } else {
                    // otherwise the snake continues on
                    snakeGameModel.player.snakeHeader.setX(snakeGameModel.player
                            .snakeHeader.getX() + 1);
                }
                snakePositions.add(new GridPosition(snakeGameModel.player.snakeHeader.getX(),
                        snakeGameModel.player.snakeHeader.getY()));
                break;
            case GameType.BOTTOM:
                // checks if snake has reached the boundary of the grid
                if (snakeGameModel.player.snakeHeader.getY() + 1 >= gridSize) {
                    // if it reaches the bottom, let snake cross the screen to the top
                    snakeGameModel.player.snakeHeader.setY(0);
                } else {
                    // otherwise the snake continues on
                    snakeGameModel.player.snakeHeader.setY(snakeGameModel.player.snakeHeader
                            .getY() + 1);
                }
                snakePositions.add(new GridPosition(snakeGameModel.player.snakeHeader.getX(),
                        snakeGameModel.player.snakeHeader.getY()));
                break;
        }
    }

    /**
     * Changes the direction of the snake to direction if it's valid
     * @param direction the new direction of the snake
     */
    void changeSnakeDirection(int direction){
        snakeGameModel.player.setSnakeDirection(direction);
    }

    /**
     * Refreshes the board and variables for a new game of Snake.
     */
    void reStartGame() {
        if (!snakeGameModel.isEndGame) return;
        for (List<GridSquare> squares : gridSquare) {
            for (GridSquare square : squares) {
                square.setType(GameType.GRID);
            }
        }
        snakePositions.clear();
        snakePositions.add(new GridPosition(snakeGameModel.player.snakeHeader.getX(),
                snakeGameModel.player.snakeHeader.getY()));

        snakeGameModel.reset();
        refreshFood(snakeGameModel.foodPosition);
        setEndGame(false);
    }


    /**
     * Checks if the snake has collided with itself or with the food.
     */
    void checkCollision() {
        GridPosition headerPosition = snakePositions.get(snakePositions.size() - 1);
        // checks if snake has collided with itself
        for (int i = 0; i < snakePositions.size() - 2; i++) {
            GridPosition position = snakePositions.get(i);
            if (headerPosition.getX() == position.getX() && headerPosition.getY() ==
                    position.getY()) {
                //ends the game
                setEndGame(true);
                writeScoreToDatabase();
                return;
            }
        }
        // checks if snake has collided with the food
        if (headerPosition.getX() == snakeGameModel.foodPosition.getX()
                && headerPosition.getY() == snakeGameModel.foodPosition.getY()) {
            snakeGameModel.snakeEats();
            generateFood();
        }
    }

    /**
     *  Refreshes the grid
     */
    void refreshGridSquare() {
        for (GridPosition position : snakePositions) {
            gridSquare.get(position.getX()).get(position.getY()).setType(GameType.SNAKE);
        }
    }

    /**
     * Writes the user's score to the database
     */
    public void writeScoreToDatabase(){
        HighscoreController highscoreController = new HighscoreController("Snake",
                snakeGameModel.calculateScore());
    }

}
