package test;

import main.AbstractPlacedPieceIterator;
import main.pin.PinSolver;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static main.AbstractGrid.EMPTY_BOARD_ENCODING;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PinSolverTest {
    @Test
    void testSolver() {
        PinSolver pinSolver = new PinSolver(0, null, null);
        assertTrue(pinSolver.solve());
    }

    @Test
    void testSolverForEmptyBoard() {
        PinSolver pinSolver = new PinSolver(0, EMPTY_BOARD_ENCODING, null);
        assertTrue(pinSolver.solve());
    }

    @Test
    void testSolverFor118() {
        PinSolver pinSolver = new PinSolver(0, "eeeereeeleeeeeeeeleebeeyeeleeeeeeee", null);
        assertTrue(pinSolver.solve());
    }

    @Test
    void testSolverFor119() {
        PinSolver pinSolver = new PinSolver(0, "eeeeeeeeleeeegeeeleeeebeeeleeeebeee", null);
        assertTrue(pinSolver.solve());
    }

    @Test
    void testSolverFor120() {
        PinSolver pinSolver = new PinSolver(0, "eeeegeeeleeeeeeeeleeeebeeeleeeeeree", null);
        assertTrue(pinSolver.solve());
    }

    @Test
    void testOutput() {
        PinSolver pinSolver = new PinSolver(0, null, null);
        pinSolver.solve();
        System.out.println(pinSolver.getGridEncoding());
        for (String pieceEncoding : pinSolver.getPiecesEncoding()) System.out.println(pieceEncoding);
        System.out.println(pinSolver.getRestCount());
        for(AbstractPlacedPieceIterator abstractPlacedPieceIterator : pinSolver.getPlacedPieceIterators()) {
            TestUtils.printGrid(abstractPlacedPieceIterator.getPiece().getPermutation().getGrid());
            System.out.println(abstractPlacedPieceIterator.getPiece().getX());
            System.out.println(abstractPlacedPieceIterator.getPiece().getY());
            System.out.println(abstractPlacedPieceIterator.getPieceIndex());
        }
    }
}
