package a207project.fall18.gamecenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardManager3x3Unittest {

    /**
     * Test whether isSolvable return True on an instance of a solvable 3x3 BoardManager
     */
    @Test
    public void checkingASolvableBoard3x3() {
        BoardManager boardManager = new BoardManager(3, 3);
        Tile tiles[][] = new Tile[Board.NUM_ROWS][Board.NUM_COLS];
        List<Tile> array = new ArrayList<>();
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(7);
        tiles[0][2] = new Tile(1);
        tiles[1][0] = new Tile(8);
        tiles[1][1] = new Tile(3);
        tiles[1][2] = new Tile(2);
        tiles[2][0] = new Tile(6);
        tiles[2][1] = new Tile(5);
        tiles[2][2] = new Tile(4);
        boardManager.getBoard().setTiles(tiles);
        array.add(new Tile(0));
        array.add(new Tile(7));
        array.add(new Tile(1));
        array.add(new Tile(8));
        array.add(new Tile(3));
        array.add(new Tile(2));
        array.add(new Tile(6));
        array.add(new Tile(5));
        array.add(new Tile(4));
        assertTrue(boardManager.isSolvable(array));
    }

    /**
     * Test whether isSolvable return False on an instance of an unsolvable 3x3 BoardManager
     */
    @Test
    public void checkingANotSolvableBoard3x3() {
        BoardManager boardManager = new BoardManager(3, 3);
        Tile tiles[][] = new Tile[Board.NUM_ROWS][Board.NUM_COLS];
        List<Tile> array = new ArrayList<>();
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(7);
        tiles[0][2] = new Tile(1);
        tiles[1][0] = new Tile(8);
        tiles[1][1] = new Tile(3);
        tiles[1][2] = new Tile(2);
        tiles[2][0] = new Tile(6);
        tiles[2][1] = new Tile(5);
        tiles[2][2] = new Tile(4);
        boardManager.getBoard().setTiles(tiles);

        array.add(new Tile(0));
        array.add(new Tile(7));
        array.add(new Tile(1));
        array.add(new Tile(8));
        array.add(new Tile(3));
        array.add(new Tile(2));
        array.add(new Tile(6));
        array.add(new Tile(5));
        array.add(new Tile(4));
        assertTrue(boardManager.isSolvable(array));
    }

    /**
     * Test whether getInversionCount works.
     */
    @Test
    public void checkingCorrectInversionCountBoard3x3() {
        BoardManager boardManager = new BoardManager(3, 3);
        Tile tiles[][] = new Tile[Board.NUM_ROWS][Board.NUM_COLS];
        List<Tile> array = new ArrayList<>();
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(7);
        tiles[0][2] = new Tile(1);
        tiles[1][0] = new Tile(8);
        tiles[1][1] = new Tile(3);
        tiles[1][2] = new Tile(2);
        tiles[2][0] = new Tile(6);
        tiles[2][1] = new Tile(5);
        tiles[2][2] = new Tile(4);
        boardManager.getBoard().setTiles(tiles);

        array.add(new Tile(0));
        array.add(new Tile(7));
        array.add(new Tile(1));
        array.add(new Tile(8));
        array.add(new Tile(3));
        array.add(new Tile(2));
        array.add(new Tile(6));
        array.add(new Tile(5));
        array.add(new Tile(4));
        assertEquals(10, boardManager.getInversionCount(array));
    }

    /**
     * Test whether findXPosition works
     */
    @Test
    public void checkingForCorrectBlankIdPosition() {
        BoardManager boardManager = new BoardManager(3, 3);
        Tile tiles[][] = new Tile[Board.NUM_ROWS][Board.NUM_COLS];
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(7);
        tiles[0][2] = new Tile(1);
        tiles[1][0] = new Tile(8);
        tiles[1][1] = new Tile(3);
        tiles[1][2] = new Tile(2);
        tiles[2][0] = new Tile(6);
        tiles[2][1] = new Tile(5);
        tiles[2][2] = new Tile(4);
        boardManager.getBoard().setTiles(tiles);
        assertEquals(2, boardManager.findXPosition());
    }

    /**
     * Test whether BoardManager always produce a solvable 3x3 BoardManager
     */
    @Test
    public void checkingBoardManagerAlwaysProduceASolvable3x3Board() {
        BoardManager boardManager1 = new BoardManager(3,3);
        List<Tile> flatTile = new ArrayList<>();
        int z = 0;
        for (int i = 0; i < Board.NUM_ROWS; i++) {
            for (int j = 0; j < Board.NUM_COLS; j++) {
                flatTile.add(z, boardManager1.getBoard().getTiles()[i][j]);
                z++;
            }
        }
        BoardManager boardManager2 = new BoardManager(3,3);
        List<Tile> flatTile1 = new ArrayList<>();
        int z1 = 0;
        for (int i1 = 0; i1 < Board.NUM_ROWS; i1++) {
            for (int j = 0; j < Board.NUM_COLS; j++) {
                flatTile1.add(z1, boardManager2.getBoard().getTiles()[i1][j]);
                z1++;
            }
        }
        BoardManager boardManager3 = new BoardManager(3,3);
        List<Tile> flatTile2 = new ArrayList<>();
        int z2 = 0;
        for (int i2 = 0; i2 < Board.NUM_ROWS; i2++) {
            for (int j = 0; j < Board.NUM_COLS; j++) {
                flatTile2.add(z2, boardManager3.getBoard().getTiles()[i2][j]);
                z2++;
            }
        }
        assertTrue(boardManager1.isSolvable(flatTile));
        assertTrue(boardManager2.isSolvable(flatTile1));
        assertTrue(boardManager3.isSolvable(flatTile2));
    }

}
