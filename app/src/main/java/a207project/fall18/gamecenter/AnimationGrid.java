package a207project.fall18.gamecenter;
//https://github.com/tpcstld/2048

import java.util.ArrayList;

/**
 * The animation grid for 2048 game
 */

public class AnimationGrid {
    final ArrayList<AnimationCell> globalAnimation = new ArrayList<>();

    /**
     * An array list of the field (current board)
     */
    private final ArrayList<AnimationCell>[][] field;

    /**
     * Number of active animations
     */
    private int activeAnimations = 0;

    /**
     * Decide whether if there is one more frame
     */
    private boolean oneMoreFrame = false;

    /**
     * A new AnimationGrid of size x by y
     * @param x number of rows
     * @param y number of columns
     */
    AnimationGrid(int x, int y) {
        field = new ArrayList[x][y];

        for (int xx = 0; xx < x; xx++) {
            for (int yy = 0; yy < y; yy++) {
                field[xx][yy] = new ArrayList<>();
            }
        }
    }

    /**
     * Managing the starting animations
     * @param x row number
     * @param y column number
     * @param animationType type of animation
     * @param length time for animation
     * @param delay delay for animation
     * @param extras previous cell positions
     */
    void startAnimation(int x, int y, int animationType, long length, long delay, int[] extras) {
        AnimationCell animationToAdd = new AnimationCell(x, y, animationType, length, delay, extras);
        if (x == -1 && y == -1) {
            globalAnimation.add(animationToAdd);
        } else {
            field[x][y].add(animationToAdd);
        }
        activeAnimations = activeAnimations + 1;
    }

    /**
     * Cancel animations based on the timeElapsed
     * @param timeElapsed time passed
     */
    void tickAll(long timeElapsed) {
        ArrayList<AnimationCell> cancelledAnimations = new ArrayList<>();
        for (AnimationCell animation : globalAnimation) {
            animation.tick(timeElapsed);
            if (animation.animationDone()) {
                cancelledAnimations.add(animation);
                activeAnimations = activeAnimations - 1;
            }
        }

        for (ArrayList<AnimationCell>[] array : field) {
            for (ArrayList<AnimationCell> list : array) {
                for (AnimationCell animation : list) {
                    animation.tick(timeElapsed);
                    if (animation.animationDone()) {
                        cancelledAnimations.add(animation);
                        activeAnimations = activeAnimations - 1;
                    }
                }
            }
        }

        for (AnimationCell animation : cancelledAnimations) {
            cancelAnimation(animation);
        }
    }

    /**
     * Return whether if animation are active
     * @return whether if animation are active
     */
    boolean isAnimationActive() {
        if (activeAnimations != 0) {
            oneMoreFrame = true;
            return true;
        } else if (oneMoreFrame) {
            oneMoreFrame = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return an array list of animation cell at row number x and column number y
     * @param x row number
     * @param y column number
     * @return an array list of animation cell at row x and column y
     */
    ArrayList<AnimationCell> getAnimationCell(int x, int y) {
        return field[x][y];
    }

    /**
     * Cancel all animations
     */
    void cancelAnimations() {
        for (ArrayList<AnimationCell>[] array : field) {
            for (ArrayList<AnimationCell> list : array) {
                list.clear();
            }
        }
        globalAnimation.clear();
        activeAnimations = 0;
    }

    /**
     * Cancel animation
     * @param animation the animation in 2048 game
     */
    void cancelAnimation(AnimationCell animation) {
        if (animation.getX() == -1 && animation.getY() == -1) {
            globalAnimation.remove(animation);
        } else {
            field[animation.getX()][animation.getY()].remove(animation);
        }
    }

}
