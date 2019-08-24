package a207project.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GridPositionUnitTest {

    /**
     * A GridPosition
     */
    private GridPosition gridPosition;

    /**
     * Set up the gridPosition we will be testing on
     */
    public void setUp() {
        gridPosition = new GridPosition(0, 1);
    }

    /**
     * Test whether the variables in GridPosition is set up correctly
     */
    @Test
    public void testGeneralGridPosition() {
        setUp();
        assertEquals(0, gridPosition.getX());
        assertEquals(1, gridPosition.getY());
        gridPosition.setX(10);
        gridPosition.setY(11);
        assertEquals(10, gridPosition.getX());
        assertEquals(11, gridPosition.getY());
    }
}
