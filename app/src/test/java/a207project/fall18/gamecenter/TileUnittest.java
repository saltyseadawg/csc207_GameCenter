package a207project.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TileUnittest {
    /**
     * Test whether the first constructor of Tile works
     */
    @Test
    public void testTileClassFirstConstructor() {
        Tile tile = new Tile(1);
        assertEquals(2, tile.getId());
        assertEquals(R.drawable.tile_2, tile.getBackground());
        Tile otherTile = new Tile(2);
        assertEquals(1, tile.compareTo(otherTile));
        assertEquals(R.drawable.tile_3, otherTile.getBackground());
    }

    /**
     * Test whether the second constructor of Tile works
     */
    @Test
    public void testTileClassSecondConstructor() {
        Tile tile = new Tile(10, 14);
        Tile otherTile = new Tile(23, 15);
        assertEquals(10, tile.getId());
        assertEquals(23, otherTile.getId());
        assertEquals(14, tile.getBackground());
        assertEquals(15, otherTile.getBackground());
        assertEquals(13, tile.compareTo(otherTile));
    }
}
