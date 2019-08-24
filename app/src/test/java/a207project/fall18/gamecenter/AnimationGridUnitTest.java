package a207project.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AnimationGridUnitTest {

    /**
     * An AnimationGrid
     */
    private AnimationGrid animationGrid;
    /**
     * An AnimationCell
     */
    private AnimationCell animationCell;

    /**
     * Set up animationGrid and animationCell that we will test on
     */
    public void setUp() {
        animationGrid = new AnimationGrid(10, 10);
        int[] extras = new int[2];
        extras[0] = 1;
        extras[1] = 2;
        animationCell = new AnimationCell(0, 0, 10, 1000, 1, extras);
    }

    /**
     * Test startAnimation works
     */
    @Test
    public void testStartAnimation() {
        setUp();
        animationGrid.startAnimation(animationCell.getX(), animationCell.getY(), animationCell.getAnimationType(), 1000, 1, animationCell.EXTRAS);
        assertTrue(animationGrid.isAnimationActive());
        assertEquals(animationGrid.getAnimationCell(0, 0).get(0).getAnimationType(), animationCell.getAnimationType());
    }

    /**
     * Test cancelAniamtion works
     */
    @Test
    public void testCancelAnimation() {
        setUp();
        animationGrid.startAnimation(animationCell.getX(), animationCell.getY(), animationCell.getAnimationType(), 1000, 1, animationCell.EXTRAS);
        animationGrid.cancelAnimations();
        assertFalse(animationGrid.isAnimationActive());
        animationGrid.startAnimation(animationCell.getX(), animationCell.getY(), animationCell.getAnimationType(), 1000, 1, animationCell.EXTRAS);
        assertEquals(animationGrid.getAnimationCell(0, 0).get(0).getAnimationType(), animationCell.getAnimationType());
        animationGrid.cancelAnimation(animationGrid.getAnimationCell(0,0).get(0));
        assertTrue(animationGrid.getAnimationCell(0, 0).isEmpty());
    }

    /**
     * Test TickAll works
     */
    @Test
    public void testTickAll() {
        setUp();
        animationGrid.startAnimation(-1, -1, 10, 100, 1, animationCell.EXTRAS);
        animationGrid.startAnimation(0, 0, 11, 10000, 1, animationCell.EXTRAS);
        animationGrid.tickAll(10000);
        assertEquals(0, animationGrid.globalAnimation.size());
        assertFalse(animationGrid.getAnimationCell(0, 0).get(0).animationDone());
    }
}
