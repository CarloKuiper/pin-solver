package test;

import main.pin.PinBoard;
import org.junit.jupiter.api.Test;

import static main.AbstractGrid.DEFAULT_BOARD_ENCODING;
import static test.TestUtils.printGrid;

class BoardTest {
    @Test
    void testCreateIQTwistBoard() {
        PinBoard pinBoard = new PinBoard(DEFAULT_BOARD_ENCODING);
        printGrid(pinBoard.getGrid());
    }
}
