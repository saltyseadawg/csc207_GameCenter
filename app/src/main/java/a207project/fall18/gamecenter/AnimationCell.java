package a207project.fall18.gamecenter;
//https://github.com/tpcstld/2048

/**
 * Animation for Cell in 2048 game
 */
class AnimationCell extends Cell {
    /**
     * List containing previous cell positions
     */
    final int[] EXTRAS;

    /**
     * The animation type (represented as an integer)
     * -1: spawn animation
     * 0: move animation
     * 1: merge animation
     * (Refer to how Main2048 Game is set up)
     */
    private final int animationType;

    /**
     * The amount of time the animation should take
     */
    private final long animationTime;

    /**
     * The delay time for the animation
     */
    private final long delayTime;

    /**
     *  How much time has passed
     */
    private long timeElapsed;

    /**
     * An AnimationCell with x and y position, animationType, animationTime, delayTime and extras
     * @param x the row number
     * @param y the column number
     * @param animationType type of animation
     * @param length time for animation
     * @param delay delay for animation
     * @param extras previous cell positions
     */
     AnimationCell(int x, int y, int animationType, long length, long delay, int[] extras) {
        super(x, y);
        this.animationType = animationType;
        animationTime = length;
        delayTime = delay;
        this.EXTRAS = extras;
    }

    /**
     * Return the animationType of the AnimationCell
     * @return the animationType
     */
    int getAnimationType() {
        return animationType;
    }

    /**
     * Set the timeElapsed to the sum of timeElapsed and extra_timeElapsed
     * @param extra_timeElapsed how much time has passed
     */
    void tick(long extra_timeElapsed) {
        this.timeElapsed = this.timeElapsed + extra_timeElapsed;
    }

    /**
     * Return whether the animation is done
     * @return whether the animation is done
     */
    boolean animationDone() {
        return animationTime + delayTime < timeElapsed;
    }

    double getPercentageDone() {
        return Math.max(0, 1.0 * (timeElapsed - delayTime) / animationTime);
    }

    /**
     * Return whether AnimationCell is still active
     * @return whether AnimationCell still active
     */
    boolean isActive() {
        return (timeElapsed >= delayTime);
    }

}
