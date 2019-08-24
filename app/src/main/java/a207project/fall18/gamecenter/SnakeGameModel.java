package a207project.fall18.gamecenter;
//https://github.com/zhangman523/AndroidGameSnake

public class SnakeGameModel {

    /**
     * A bool that keeps track of if the game is over.
     */
    boolean isEndGame = false;

    /**
     * The GridPosition of the food.
     */
    GridPosition foodPosition;

    /**
     * The current score.
     */
    private int score;

    /**
     * An instance of SnakePlayerModel
     */
    SnakePlayerModel player;

    SnakeGameModel(){
        player = new SnakePlayerModel();
        score = 0;
    }

    /**
     * @return the total score
     */
    int calculateScore() {
        return player.getSpeed() * score;
    }

    /**
     * Updates the snake length and score
     */
    void snakeEats(){
        player.setSnakeLength(player.getSnakeLength() + 1);
        score++;
    }

    /**
     * Resets the models for snake
     */
    public void reset(){
        if (player.snakeHeader != null) {
            player.snakeHeader.setX(10);
            player.snakeHeader.setY(10);
        } else {
            player.snakeHeader = new GridPosition(10, 10);//starting snake position
        }
        if (foodPosition != null) {
            foodPosition.setX(0);
            foodPosition.setY(0);
        } else {
            foodPosition = new GridPosition(0, 0);
        }
        player.setSnakeLength(3); //sets length of the snake
        score = 0;
        player.resetSnakeDirection();
    }
}
