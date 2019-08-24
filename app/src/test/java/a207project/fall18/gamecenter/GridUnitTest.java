package a207project.fall18.gamecenter;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class GridUnitTest {

    /**
     * Test whether getAvailableCells works
     */
    @Test
    public void testGetAvailableCells() {
        Grid grid = new Grid(2,2);
        grid.getAvailableCells();
        assertNull(grid.getCellContent(0, 0));
        assertEquals(0, grid.getAvailableCells().get(0).getX());
    }

    /**
     * Test whether isCellAvailable works
     */
    @Test
    public void testIsCellAvailable() {
        Grid grid = new Grid(2, 2);
        assertTrue(grid.isCellsAvailable());
        ArrayList<Cell> cells = grid.getAvailableCells();
        assertTrue(grid.isCellAvailable(cells.get(0)));
    }

    /**
     * Test whether getCellContent and insertBlock and removeTile works
     */
    @Test
    public void testGetCellContent() {
        Grid grid = new Grid(2, 2);
        ArrayList<Cell> cells = grid.getAvailableCells();
        assertNull(grid.getCellContent(cells.get(0)));
        assertNull(grid.getCellContent(0, 1));
        grid.insertBlock(new Block(0, 0, 10));
        assertNotNull(grid.getCellContent(0, 0));
        assertEquals(10, grid.getCellContent(0, 0).getValue());
        grid.removeBlock(new Block(0, 0, 10));
        assertNull(grid.getCellContent(0,0));
    }

    /**
     * Test whether the undo functionality works
     */
    @Test
    public void testSaveTile() {
        Grid grid = new Grid(2, 2);
        grid.insertBlock(new Block(0, 0, 2));
        grid.insertBlock(new Block(0, 1, 2));
        grid.insertBlock(new Block(1, 0, 2));
        grid.insertBlock(new Block(1, 1, 4));
        grid.prepareSaveBlocks();
        grid.saveBlocks();
        assertEquals(4, grid.getCellContent(1,1).getValue());
        grid.insertBlock(new Block(1, 1, 8));
        assertEquals(8, grid.getCellContent(1,1).getValue());
        grid.revertBlocks();
        assertEquals(4, grid.getCellContent(1,1).getValue());
    }

    @Test
    public void testStack() {
        Grid grid = new Grid(2,2);
        assertTrue(grid.getStack().isEmpty());
        grid.clearStack();
        assertTrue(grid.getStack().isEmpty());
    }
}
