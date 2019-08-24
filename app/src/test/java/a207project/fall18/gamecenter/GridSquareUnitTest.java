package a207project.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GridSquareUnitTest {

    /**
     * A GridSquare
     */
    private GridSquare gridSquare;

    /**
     * Set up the gridSquare we will be testing on
     */
    public void setUp() {
        gridSquare = new GridSquare(0);
    }

    /**
     * Test whether getColor works
     */
    @Test
    public void testGetColor() {
        setUp();
        assertEquals(0xFFFFFFFF, gridSquare.getColor());
        gridSquare.setType(1);
        assertEquals(0xFF0000FF, gridSquare.getColor());
        gridSquare.setType(10);
        assertEquals(0xFFFFFFFF, gridSquare.getColor());
    }
}
