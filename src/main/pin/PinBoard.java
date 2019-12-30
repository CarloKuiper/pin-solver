package main.pin;

import main.AbstractGrid;
import main.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static main.pin.Colour.determineColour;

public class PinBoard extends AbstractGrid {
    List<Pin> openPins;

    public PinBoard(String gridEncoding) {
        super(gridEncoding);
        determinePins();
    }

    @Override
    public int decode(Character c) {
        return decode(c, true);
    }

    static int decode(Character c, boolean isBoard) {
        switch (c) {
            case 'b':
                return isBoard ? BLUE_PIN : BLUE_OPEN;
            case 'B':
                return BLUE_CLOSED;
            case 'g':
                return isBoard ? GREEN_PIN : GREEN_OPEN;
            case 'G':
                return GREEN_CLOSED;
            case 'r':
                return isBoard ? RED_PIN : RED_OPEN;
            case 'R':
                return RED_CLOSED;
            case 'y':
                return isBoard ? YELLOW_PIN : YELLOW_OPEN;
            case 'Y':
                return YELLOW_CLOSED;
            default:
                return EMPTY;
        }
    }

    boolean canPlacePieceOnPin(int[][] grid, Piece piece) {
        int[][] pieceGrid = piece.getPermutation().getGrid();
        if (piece.getX() < 0) return false;
        if (piece.getX() + pieceGrid[0].length > grid[0].length) return false;
        if (piece.getY() < 0) return false;
        if (piece.getY() + pieceGrid.length > grid.length) return false;
        for (int x = 0; x < pieceGrid[0].length; x++) {
            for (int y = 0; y < pieceGrid.length; y++) {
                if (!isValidValue(grid[piece.getY() + y][piece.getX() + x] + pieceGrid[y][x])) return false;
            }
        }
        return true;
    }

    private static boolean isValidValue(int value) {
        return IntStream.of(EMPTY, NONEMPTY, BLUE_PIN, BLUE_OPEN, BLUE_CLOSED, GREEN_PIN, GREEN_OPEN, GREEN_CLOSED, RED_PIN,
                RED_OPEN, RED_CLOSED, YELLOW_PIN, YELLOW_OPEN, YELLOW_CLOSED)
                .anyMatch(validValue -> value == validValue);
    }

    void determinePins() {
        openPins = new ArrayList<>();
        int length = getGrid()[0].length;
        int height = getGrid().length;
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                Colour colour = determineColour(getGrid()[y][x]);
                if (colour == null) continue; // empty board unit square
                if (getGrid()[y][x] == colour.factor) {
                    openPins.add(new Pin(colour, x, y));
                }
            }
        }
    }

    class Pin {
        Colour colour;
        int x;
        int y;

        Pin(Colour colour, int x, int y) {
            this.colour = colour;
            this.x = x;
            this.y = y;
        }
    }
}
