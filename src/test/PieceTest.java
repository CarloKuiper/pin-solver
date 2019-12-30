package test;

import main.normal.NormalBoard;
import main.normal.NormalPiece;
import main.pin.PinPiece;
import org.junit.jupiter.api.Test;

import static main.AbstractGrid.DEFAULT_BOARD_ENCODING;
import static main.AbstractGrid.DEFAULT_PIECES_ENCODING;
import static main.Piece.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.TestUtils.printGrid;

class PieceTest {
    private static final int[][] mirrorSymmetricArray = new int[][]{{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 1}, {0, 0, 1, 0}};
    private static final int[][] c4Array = new int[][]{{0, 1, 0, 0}, {0, 1, 1, 1}, {1, 1, 1, 0}, {0, 0, 1, 0}};
    private static final int[][] c2Array = new int[][]{{1, 1, 1, 0}, {0, 1, 1, 1}};
    private static final int[][] nonSymmetricArray = new int[][]{{0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 1}};

    @Test
    void testCreateIQPuzzlerPieces() {
        for (String pieceEncoding : DEFAULT_PIECES_ENCODING) {
            new NormalPiece(pieceEncoding);
        }
    }

    @Test
    void testCreateIQTwistPieces() {
        for (String pieceEncoding : DEFAULT_PIECES_ENCODING) {
            PinPiece piece = new PinPiece(pieceEncoding);
            printGrid(piece.getGrid());
        }
    }

    @Test
    void testDiagonalFlip() {
        NormalBoard board = new NormalBoard(DEFAULT_BOARD_ENCODING);
        int[][] flippedGridBoard = diagonalFlip(board.getGrid());
        printGrid(flippedGridBoard);
    }

    @Test
    void testRotate() {
        NormalBoard board = new NormalBoard(DEFAULT_BOARD_ENCODING);
        int[][] rotatedGridBoard = rotate(board.getGrid());
        printGrid(rotatedGridBoard);
    }

    @Test
    void testIsEqual() {
        PinPiece piece1 = new PinPiece(DEFAULT_PIECES_ENCODING[0]);
        PinPiece piece2 = new PinPiece(DEFAULT_PIECES_ENCODING[1]);
        assertTrue(isEqual(piece1.getGrid(), piece1.getGrid()));
        assertFalse(isEqual(piece1.getGrid(), piece2.getGrid()));
    }

    @Test
    void testIsMirrorSymmetric() {
        assertTrue(isMirrorSymmetric(mirrorSymmetricArray));
        assertFalse(isMirrorSymmetric(nonSymmetricArray));
    }

    @Test
    void testIsC4() {
        assertTrue(isC4(c4Array));
        assertFalse(isC4(nonSymmetricArray));
    }

    @Test
    void testIsC2() {
        assertTrue(isC2(c2Array));
        assertFalse(isC2(nonSymmetricArray));
    }
}
