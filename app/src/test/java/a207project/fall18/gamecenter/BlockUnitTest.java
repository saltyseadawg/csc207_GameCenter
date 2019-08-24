package a207project.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockUnitTest {

    /**
     * Test whether the Constructor works.
     */
    @Test
    public void testSuccessfulConstructor() {
        Block block = new Block(0, 1, 2);
        assertEquals(0, block.getX());
        assertEquals(1, block.getY());
        assertEquals(2, block.getValue());
    }

    /**
     * Test whether the overloading Constructor works giving it a parameter as a Cell
     */
    @Test
    public void testSuccessfulConstructorGivenACell() {
        Block block = new Block(new Cell(10, 11), 4);
        assertEquals(10, block.getX());
        assertEquals(11, block.getY());
        assertEquals(4, block.getValue());
    }

    /**
     * Test whether updatePosition works.
     */
    @Test
    public void testUpdatePosition() {
        Block block = new Block(0, 1, 2);
        block.updatePosition(new Cell(1, 0));
        assertEquals(1, block.getX());
        assertEquals(0, block.getY());
    }

    /**
     * Test whether getMergedFrom and setMergeFrom works.
     */
    @Test
    public void testMergedFrom() {
        Block block = new Block(0, 1, 2);
        Block[] arrayBlock = new Block[2];
        Block block1 = new Block(1,1,4);
        Block block2 = new Block(1,0, 4);
        arrayBlock[0] = block1;
        arrayBlock[1] = block2;
        block.setMergedFrom(arrayBlock);
        assertEquals(block1, block.getMergedFrom()[0]);
        assertEquals(block2, block.getMergedFrom()[1]);
    }
}
