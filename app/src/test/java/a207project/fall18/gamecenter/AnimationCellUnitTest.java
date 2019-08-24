package a207project.fall18.gamecenter;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class AnimationCellUnitTest {

    /**
     * An AnimationCell
     */
    private AnimationCell animationCell;

    /**
     * Set up the animationCell we are testing on
     */
    public void setUp() {
        int[] extras = new int[2];
        extras[0] = 1;
        extras[1] = 2;
        animationCell = new AnimationCell(0, 0, 10, 1000, 1, extras);
    }

    /**
     * Test whether the variable extras stays null
     */
    @Test
    public void testExtrasNonNull() {
        setUp();
        assertNotNull(animationCell.EXTRAS);
    }

    /**
     * Test whether getAnimationType works
     */
    @Test
    public void testGetAnimationType() {
        setUp();
        assertEquals(10, animationCell.getAnimationType());
    }

    /**
     * Test whether animationDone works
     */
    @Test
    public void testAnimationDone() {
        setUp();
        animationCell.tick(21);
        assertFalse(animationCell.animationDone());
    }

    /**
     * Test whether getPercentageDone works
     */
    @Test
    public void testGetPercentageDone() {
        setUp();
        animationCell.tick(21);
        assertEquals(0, animationCell.getPercentageDone(), 1f);
    }

    /**
     * Test whether isActive works
     */
    @Test
    public void testIsActive() {
        setUp();
        animationCell.tick(21);
        assertTrue(animationCell.isActive());
    }
}
