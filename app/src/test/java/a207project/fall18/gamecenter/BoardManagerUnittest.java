package a207project.fall18.gamecenter;

import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BoardManagerUnittest {

    private BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        Board.NUM_COLS = 4; Board.NUM_ROWS = 4;
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        Board board = new Board(tiles);
        boardManager = new BoardManager(board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether calculateScore works
     */
    @Test
    public void calculateScore() {
        setUpCorrect();
        assertEquals(1000, boardManager.calculateScore());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        boardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertTrue(boardManager.isValidTap(11));
        assertTrue(boardManager.isValidTap(14));
        assertFalse(boardManager.isValidTap(10));
    }

    /**
     * Test whether touchMove works.
     */
    @Test
    public void testTouchMove() {
        setUpCorrect();
        boardManager.touchMove(11);
        assertEquals(16, boardManager.getBoard().getTile(2, 3).getId());
        assertEquals(12, boardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether undo and autoSaved work.
     */
    @Test
    public void testUndoAndAutoSaved() {
        BoardManager oldBoard = new BoardManager();
        oldBoard.autoSaved();
        oldBoard.getBoard().swapTiles(1, 1, 2, 2);
        assertEquals(oldBoard.getBoard().getTile(1,1), oldBoard.undo().getBoard().getTile(2, 2));
    }

    /**
     * Test whether toString works.
     */
    @Test
    public void testToString() {
        setUpCorrect();
        assertEquals("Board{tiles=" + Arrays.toString(boardManager.getBoard().getTiles()) + "}", boardManager.getBoard().toString());
    }

    /**
     * Test whether calculateScore works.
     */
    @Test
    public void testCalculateScore() {
        setUpCorrect();
        assertEquals(1000, boardManager.calculateScore());
    }

    // These are the tests for a 4x4 board

    /**
     * Test whether isSolvable return True on a prepopulate BoardManager that is Solvable!
     */
    @Test
    public void checkingASolvableBoard() {
        BoardManager boardManager = new BoardManager();
        Tile tiles[][] = new Tile[Board.NUM_ROWS][Board.NUM_COLS];
        Tile array[] = new Tile[boardManager.getBoard().numTiles()];
        tiles[0][0] = new Tile(11);
        tiles[0][1] = new Tile(0);
        tiles[0][2] = new Tile(9);
        tiles[0][3] = new Tile(1);
        tiles[1][0] = new Tile(6);
        tiles[1][1] = new Tile(10);
        tiles[1][2] = new Tile(3);
        tiles[1][3] = new Tile(13);
        tiles[2][0] = new Tile(4);
        tiles[2][1] = new Tile(15);
        tiles[2][2] = new Tile(8);
        tiles[2][3] = new Tile(14);
        tiles[3][0] = new Tile(7);
        tiles[3][1] = new Tile(12);
        tiles[3][2] = new Tile(5);
        tiles[3][3] = new Tile(2);
        boardManager.getBoard().setTiles(tiles);

        array[0] = new Tile(11);
        array[1] = new Tile(0);
        array[2] = new Tile(9);
        array[3] = new Tile(1);
        array[4] = new Tile(6);
        array[5] = new Tile(10);
        array[6] = new Tile(3);
        array[7] = new Tile(13);
        array[8] = new Tile(4);
        array[9] = new Tile(15);
        array[10] = new Tile(8);
        array[11] = new Tile(14);
        array[12] = new Tile(7);
        array[13] = new Tile(12);
        array[14] = new Tile(5);
        array[15] = new Tile(2);
        List<Tile> arrayList = new ArrayList<>(Arrays.asList(array));
        assertTrue(boardManager.isSolvable(arrayList));
    }

    /**
     * Test whether isSolvable return False on a prepopulate BoardManager that is unsolvable
     */
    @Test
    public void checkingANotSolvableTest() {
        BoardManager boardManager = new BoardManager();
        Tile tiles[][] = new Tile[Board.NUM_ROWS][Board.NUM_COLS];
        tiles[0][0] = new Tile(2);
        tiles[0][1] = new Tile(8);
        tiles[0][2] = new Tile(0);
        tiles[0][3] = new Tile(14);
        tiles[1][0] = new Tile(13);
        tiles[1][1] = new Tile(10);
        tiles[1][2] = new Tile(3);
        tiles[1][3] = new Tile(5);
        tiles[2][0] = new Tile(12);
        tiles[2][1] = new Tile(15);
        tiles[2][2] = new Tile(9);
        tiles[2][3] = new Tile(11);
        tiles[3][0] = new Tile(1);
        tiles[3][1] = new Tile(6);
        tiles[3][2] = new Tile(7);
        tiles[3][3] = new Tile(4);
        boardManager.getBoard().setTiles(tiles);

        Tile array[] = new Tile[boardManager.getBoard().numTiles()];
        array[0] = new Tile(2);
        array[1] = new Tile(8);
        array[2] = new Tile(0);
        array[3] = new Tile(14);
        array[4] = new Tile(13);
        array[5] = new Tile(10);
        array[6] = new Tile(3);
        array[7] = new Tile(5);
        array[8] = new Tile(12);
        array[9] = new Tile(15);
        array[10] = new Tile(9);
        array[11] = new Tile(11);
        array[12] = new Tile(1);
        array[13] = new Tile(6);
        array[14] = new Tile(7);
        array[15] = new Tile(4);
        List<Tile> arrayList = new ArrayList<>(Arrays.asList(array));
        assertFalse(boardManager.isSolvable(arrayList));
    }

    /**
     * Test whether getInversionCount works
     */
    @Test
    public void checkingCorrectInversionCount() {
        BoardManager boardManager = new BoardManager();
        Tile array[] = new Tile[boardManager.getBoard().numTiles()];
        array[0] = new Tile(2);
        array[1] = new Tile(8);
        array[2] = new Tile(0);
        array[3] = new Tile(14);
        array[4] = new Tile(13);
        array[5] = new Tile(10);
        array[6] = new Tile(3);
        array[7] = new Tile(5);
        array[8] = new Tile(12);
        array[9] = new Tile(15);
        array[10] = new Tile(9);
        array[11] = new Tile(11);
        array[12] = new Tile(1);
        array[13] = new Tile(6);
        array[14] = new Tile(7);
        array[15] = new Tile(4);
        List<Tile> arrayList = new ArrayList<>(Arrays.asList(array));
        assertEquals(56, boardManager.getInversionCount(arrayList));
    }

    /**
     * Test whether findXPosition works
     */
    @Test
    public void checkingCorrectBlankIdPosition() {
        BoardManager boardManager = new BoardManager();
        Tile tiles[][] = new Tile[Board.NUM_ROWS][Board.NUM_COLS];
        tiles[0][0] = new Tile(2);
        tiles[0][1] = new Tile(8);
        tiles[0][2] = new Tile(0);
        tiles[0][3] = new Tile(14);
        tiles[1][0] = new Tile(13);
        tiles[1][1] = new Tile(10);
        tiles[1][2] = new Tile(3);
        tiles[1][3] = new Tile(5);
        tiles[2][0] = new Tile(12);
        tiles[2][1] = new Tile(15);
        tiles[2][2] = new Tile(9);
        tiles[2][3] = new Tile(11);
        tiles[3][0] = new Tile(1);
        tiles[3][1] = new Tile(6);
        tiles[3][2] = new Tile(7);
        tiles[3][3] = new Tile(4);
        boardManager.getBoard().setTiles(tiles);
        assertEquals(2, boardManager.findXPosition());
    }

    /**
     * Test whether the BoardManager constructor always produce a Solvable BoardManager
     */
    @Test
    public void checkingBoardManagerAlwaysProduceASolvableBoard() {
        BoardManager boardManager1 = new BoardManager();
        List<Tile> flatTile = new ArrayList<>();
        int z = 0;
        for (int i = 0; i < Board.NUM_ROWS; i++) {
            for (int j = 0; j < Board.NUM_COLS; j++) {
                flatTile.add(z, boardManager1.getBoard().getTiles()[i][j]);
                z++;
            }
        }
        BoardManager boardManager2 = new BoardManager();
        List<Tile> flatTile1 = new ArrayList<>();
        int z1 = 0;
        for (int i1 = 0; i1 < Board.NUM_ROWS; i1++) {
            for (int j = 0; j < Board.NUM_COLS; j++) {
                flatTile1.add(z1, boardManager2.getBoard().getTiles()[i1][j]);
                z1++;
            }
        }
        BoardManager boardManager3 = new BoardManager();
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
