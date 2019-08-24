package a207project.fall18.gamecenter;

import android.view.MotionEvent;
import android.view.View;

public class SnakeMovement implements View.OnTouchListener{
    /**
     * Minimum swipe distance
     */
    private static final int SWIPE_MIN_DISTANCE = 0;
    private static final int SWIPE_THRESHOLD_VELOCITY = 25;
    private static final int MOVE_THRESHOLD = 250;
    private static final int RESET_STARTING = 10;
    /**
     * An instance of SnakePanelView
     */
    private final SnakePanelView snakePanelView;

    /**
     * A horizontal position point
     */
    private float x;

    /**
     * A vertical position point
     */
    private float y;

    /**
     * Last horizontal swipe distance
     */
    private float lastDx;

    /**
     * Last vertical swipe distance
     */
    private float lastDy;

    /**
     * Last horizontal position point
     */
    private float previousX;

    /**
     * Last vertical position point
     */
    private float previousY;

    /**
     * Starting horizontal position point
     */
    private float startingX;

    /**
     * Starting vertical position point
     */
    private float startingY;
    private int previousDirection = 1;
    private int veryLastDirection = 1;

    SnakeMovement(SnakePanelView view) {
        super();
        this.snakePanelView = view;
    }

    /**
     * Making snake movements according to the user swipe motion.
     */
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                startingX = x;
                startingY = y;
                previousX = x;
                previousY = y;
                lastDx = 0;
                lastDy = 0;
                return true;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                float dx = x - previousX;
                if (Math.abs(lastDx + dx) < Math.abs(lastDx) + Math.abs(dx) && Math.abs(dx) > RESET_STARTING
                        && Math.abs(x - startingX) > SWIPE_MIN_DISTANCE) {
                    startingX = x;
                    startingY = y;
                    lastDx = dx;
                    previousDirection = veryLastDirection;
                }
                if (lastDx == 0) {
                    lastDx = dx;
                }
                float dy = y - previousY;
                if (Math.abs(lastDy + dy) < Math.abs(lastDy) + Math.abs(dy) && Math.abs(dy) > RESET_STARTING
                        && Math.abs(y - startingY) > SWIPE_MIN_DISTANCE) {
                    startingX = x;
                    startingY = y;
                    lastDy = dy;
                    previousDirection = veryLastDirection;
                }
                if (lastDy == 0) {
                    lastDy = dy;
                }
                if (pathMoved() > SWIPE_MIN_DISTANCE * SWIPE_MIN_DISTANCE) {
                    boolean moved = false;
                    //Vertical
                    if (((dy >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dy) >= Math.abs(dx)) || y - startingY >= MOVE_THRESHOLD) && previousDirection % 2 != 0) {
                        moved = true;
                        changeDirection(2, GameType.BOTTOM);
                    } else if (((dy <= -SWIPE_THRESHOLD_VELOCITY && Math.abs(dy) >= Math.abs(dx)) || y - startingY <= -MOVE_THRESHOLD) && previousDirection % 3 != 0) {
                        moved = true;
                        changeDirection(3, GameType.TOP);
                    }
                    //Horizontal
                    if (((dx >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dx) >= Math.abs(dy)) || x - startingX >= MOVE_THRESHOLD) && previousDirection % 5 != 0) {
                        moved = true;
                        changeDirection(5, GameType.RIGHT);
                    } else if (((dx <= -SWIPE_THRESHOLD_VELOCITY && Math.abs(dx) >= Math.abs(dy)) || x - startingX <= -MOVE_THRESHOLD) && previousDirection % 7 != 0) {
                        moved = true;
                        changeDirection(7, GameType.LEFT);
                    }
                    if (moved) {
                        startingX = x;
                        startingY = y;
                    }
                }
                previousX = x;
                previousY = y;
                return true;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                previousDirection = 1;
                veryLastDirection = 1;
        }
        return true;
    }

    /**
     * length of the swipe movement
     */
    private float pathMoved() {
        return (x - startingX) * (x - startingX) + (y - startingY) * (y - startingY);
    }

    private  void changeDirection(int prevDirection, int snakeDirection) {
        previousDirection = previousDirection * prevDirection;
        veryLastDirection = prevDirection;
        snakePanelView.snakeViewModel.changeSnakeDirection(snakeDirection);
    }
}
