package a207project.fall18.gamecenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardManager5x5Unittest {

    /**
     * Test whether the BoardManager always produce a solvable 5x5 BoardManager
     */
    @Test
    public void checkingBoardManagerAlwaysProduceASolvable5x5Board() {
        BoardManager boardManager1 = new BoardManager(5,5);
        List<Tile> flatTile = new ArrayList<>();
        int z = 0;
        for (int i = 0; i < Board.NUM_ROWS; i++) {
            for (int j = 0; j < Board.NUM_COLS; j++) {
                flatTile.add(z, boardManager1.getBoard().getTiles()[i][j]);
                z++;
            }
        }
        BoardManager boardManager2 = new BoardManager(5,5);
        List<Tile> flatTile1 = new ArrayList<>();
        int z1 = 0;
        for (int i1 = 0; i1 < Board.NUM_ROWS; i1++) {
            for (int j = 0; j < Board.NUM_COLS; j++) {
                flatTile1.add(z1, boardManager2.getBoard().getTiles()[i1][j]);
                z1++;
            }
        }
        BoardManager boardManager3 = new BoardManager(5,5);
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

    /**
     * Test whether isSolvable return True on an instance of a solvable prepopulate 5x5 BoardManager
     */
    @Test
    public void checkingASolvableBoard() {
        BoardManager boardManager = new BoardManager(5, 5);
        Tile tiles[][] = new Tile[Board.NUM_ROWS][Board.NUM_COLS];
        Tile array[] = new Tile[boardManager.getBoard().numTiles()];
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(1);
        tiles[0][2] = new Tile(2);
        tiles[0][3] = new Tile(8);
        tiles[0][4] = new Tile(3);
        tiles[1][0] = new Tile(5);
        tiles[1][1] = new Tile(13);
        tiles[1][2] = new Tile(18);
        tiles[1][3] = new Tile(22);
        tiles[1][4] = new Tile(4);
        tiles[2][0] = new Tile(15);
        tiles[2][1] = new Tile(21);
        tiles[2][2] = new Tile(17);
        tiles[2][3] = new Tile(24);
        tiles[2][4] = new Tile(7);
        tiles[3][0] = new Tile(20);
        tiles[3][1] = new Tile(11);
        tiles[3][2] = new Tile(6);
        tiles[3][3] = new Tile(14);
        tiles[3][4] = new Tile(12);
        tiles[4][0] = new Tile(16);
        tiles[4][1] = new Tile(10);
        tiles[4][2] = new Tile(19);
        tiles[4][3] = new Tile(23);
        tiles[4][4] = new Tile(9);
        boardManager.getBoard().setTiles(tiles);

        array[0] = new Tile(0);
        array[1] = new Tile(1);
        array[2] = new Tile(2);
        array[3] = new Tile(8);
        array[4] = new Tile(3);
        array[5] = new Tile(5);
        array[6] = new Tile(13);
        array[7] = new Tile(18);
        array[8] = new Tile(22);
        array[9] = new Tile(4);
        array[10] = new Tile(15);
        array[11] = new Tile(21);
        array[12] = new Tile(17);
        array[13] = new Tile(24);
        array[14] = new Tile(7);
        array[15] = new Tile(20);
        array[16] = new Tile(11);
        array[17] = new Tile(6);
        array[18] = new Tile(14);
        array[19] = new Tile(12);
        array[20] = new Tile(16);
        array[21] = new Tile(10);
        array[22] = new Tile(19);
        array[23] = new Tile(23);
        array[24] = new Tile(9);
        List<Tile> arrayList = new ArrayList<>(Arrays.asList(array));
        assertTrue(boardManager.isSolvable(arrayList));
    }

    /**
     * Test whether getInversionCount works.
     */
    @Test
    public void checkingCorrectInversionCount5x5Board() {
        BoardManager boardManager = new BoardManager(5, 5);
        Tile tiles[][] = new Tile[Board.NUM_ROWS][Board.NUM_COLS];
        Tile array[] = new Tile[boardManager.getBoard().numTiles()];
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(1);
        tiles[0][2] = new Tile(2);
        tiles[0][3] = new Tile(8);
        tiles[0][4] = new Tile(3);
        tiles[1][0] = new Tile(5);
        tiles[1][1] = new Tile(13);
        tiles[1][2] = new Tile(18);
        tiles[1][3] = new Tile(22);
        tiles[1][4] = new Tile(4);
        tiles[2][0] = new Tile(15);
        tiles[2][1] = new Tile(21);
        tiles[2][2] = new Tile(17);
        tiles[2][3] = new Tile(24);
        tiles[2][4] = new Tile(7);
        tiles[3][0] = new Tile(20);
        tiles[3][1] = new Tile(11);
        tiles[3][2] = new Tile(6);
        tiles[3][3] = new Tile(14);
        tiles[3][4] = new Tile(12);
        tiles[4][0] = new Tile(16);
        tiles[4][1] = new Tile(10);
        tiles[4][2] = new Tile(19);
        tiles[4][3] = new Tile(23);
        tiles[4][4] = new Tile(9);
        boardManager.getBoard().setTiles(tiles);

        array[0] = new Tile(0);
        array[1] = new Tile(1);
        array[2] = new Tile(2);
        array[3] = new Tile(8);
        array[4] = new Tile(3);
        array[5] = new Tile(5);
        array[6] = new Tile(13);
        array[7] = new Tile(18);
        array[8] = new Tile(22);
        array[9] = new Tile(4);
        array[10] = new Tile(15);
        array[11] = new Tile(21);
        array[12] = new Tile(17);
        array[13] = new Tile(24);
        array[14] = new Tile(7);
        array[15] = new Tile(20);
        array[16] = new Tile(11);
        array[17] = new Tile(6);
        array[18] = new Tile(14);
        array[19] = new Tile(12);
        array[20] = new Tile(16);
        array[21] = new Tile(10);
        array[22] = new Tile(19);
        array[23] = new Tile(23);
        array[24] = new Tile(9);
        List<Tile> arrayList = new ArrayList<>(Arrays.asList(array));
        assertEquals(86, boardManager.getInversionCount(arrayList));
    }

    /**
     * Test whether findXPosition works.
     */
    @Test
    public void checkForCorrectPositionOfBlankId() {
        BoardManager boardManager = new BoardManager(5, 5);
        Tile tiles[][] = new Tile[Board.NUM_ROWS][Board.NUM_COLS];
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(1);
        tiles[0][2] = new Tile(2);
        tiles[0][3] = new Tile(8);
        tiles[0][4] = new Tile(3);
        tiles[1][0] = new Tile(5);
        tiles[1][1] = new Tile(13);
        tiles[1][2] = new Tile(18);
        tiles[1][3] = new Tile(22);
        tiles[1][4] = new Tile(4);
        tiles[2][0] = new Tile(15);
        tiles[2][1] = new Tile(21);
        tiles[2][2] = new Tile(17);
        tiles[2][3] = new Tile(24);
        tiles[2][4] = new Tile(7);
        tiles[3][0] = new Tile(20);
        tiles[3][1] = new Tile(11);
        tiles[3][2] = new Tile(6);
        tiles[3][3] = new Tile(14);
        tiles[3][4] = new Tile(12);
        tiles[4][0] = new Tile(16);
        tiles[4][1] = new Tile(10);
        tiles[4][2] = new Tile(19);
        tiles[4][3] = new Tile(23);
        tiles[4][4] = new Tile(9);
        boardManager.getBoard().setTiles(tiles);
        assertEquals(3, boardManager.findXPosition());
    }
}
