package a207project.fall18.gamecenter;
//https://github.com/zhangman523/AndroidGameSnake

public class SnakePlayerModel {
    /**
     * The speed of the snake.
     */
    private int speed;
    /**
     * The initial length of the snake.
     */
    private int snakeLength;

    /**
     * The GridPosition of the snake's head.
     */
    GridPosition snakeHeader;

    /**
     * The direction the snake travels in.
     */
    private int snakeDirection = GameType.RIGHT;

    SnakePlayerModel(){
        snakeLength = 3;
    }
    /**
     * Sets the speed of the snake
     *
     * @param newSpeed the new speed input
     */
    void setSpeed (int newSpeed){
        speed = newSpeed;
    }

    /**
     * Returns the speed of the snake
     */
    int getSpeed(){
        return speed;
    }

    /**
     * Sets the length of the snake
     *
     * @param newSnakeLength the new length input
     */
     void setSnakeLength (int newSnakeLength){
        snakeLength = newSnakeLength;
    }

    /**
     * Returns the length of the snake
     */
    int getSnakeLength(){
        return snakeLength;
    }

    /**
     * Returns the current direction of the snake
     */
    int getSnakeDirection(){
        return snakeDirection;
    }

    /**
     * Changes the direction of the snake
     *
     * @param direction the new direction input
     */
    void setSnakeDirection(int direction) {
        if (snakeDirection == GameType.RIGHT && direction == GameType.LEFT) return;
        if (snakeDirection == GameType.LEFT && direction == GameType.RIGHT) return;
        if (snakeDirection == GameType.TOP && direction == GameType.BOTTOM) return;
        if (snakeDirection == GameType.BOTTOM && direction == GameType.TOP) return;
        snakeDirection = direction;
    }

    /**
     * Sets the direction of the snake to its default, right.
     */
    void resetSnakeDirection(){
        snakeDirection = GameType.RIGHT;
    }

}
