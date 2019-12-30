package main.normal;

import main.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static main.AbstractGrid.DEFAULT_BOARD_ENCODING;
import static main.AbstractGrid.DEFAULT_PIECES_ENCODING;

public class NormalSolver implements Solver {
    private static final Logger logger = Logger.getLogger(NormalSolver.class.getName());

    private final long restCount;
    private int boardHeight;
    private int[] nextCoordinate;
    private NormalPiece[] normalPieces;

    protected int iterations = 0;
    protected int recursionDepth = 0;
    protected AbstractPlacedPieceIterator[] placedPieceIterators;
    protected ArrayList<Integer> placedPieces;
    protected String gridEncoding;
    protected String[] piecesEncoding;
    protected NormalBoard normalBoard;

    public NormalSolver(long restCount, String gridEncoding, String[] piecesEncoding) {
        this.restCount = restCount;
        this.gridEncoding = gridEncoding != null ? gridEncoding : DEFAULT_BOARD_ENCODING;
        this.piecesEncoding = piecesEncoding != null ? piecesEncoding : DEFAULT_PIECES_ENCODING;

        // Turn the gridEncoding into a board.
        normalBoard = new NormalBoard(this.gridEncoding);
        boardHeight = normalBoard.getGrid().length;

        // Add each piece to the pieces array.
        normalPieces = new NormalPiece[this.piecesEncoding.length];
        placedPieces = new ArrayList<>();
        IntStream.range(0, normalPieces.length).forEach(index -> normalPieces[index] = new NormalPiece(this.piecesEncoding[index]));

        // Initialize the placed pieces arrays.
        placedPieceIterators = new AbstractPlacedPieceIterator[normalPieces.length];
    }

    // Loop over each pin and try each rotation and flip on the openPins.
    // After pins use a smart algorithm on a normalized board.
    public boolean solve() {
        // When no next coordinate can be found, the algorithm has finished!!!
        if (!determineNextCoordinate()) {
            logger.log(Level.INFO, "Board solved with {0} iterations and iteration depth {1}.", new Integer[]{iterations, recursionDepth});
            return true;
        }

        // Initiate the placed piece iterator.
        boolean topToBottom = nextCoordinate[1] < Math.ceil((double) boardHeight / 2);
        if (determineHasUnsolvablePattern(topToBottom)) return false;
        int pattern = topToBottom ? determinePattern() : determineMirroredPattern();
        AbstractPlacedPieceIterator placedPieceIterator = new PlacedPieceIterator(placedPieces, normalPieces, pattern);
        placedPieceIterators[placedPieces.size()] = placedPieceIterator;
        recursionDepth++;

        // Use the iterator to place the next piece.
        while (placedPieceIterator.hasNext()) {
            // Initiate the piece.
            NormalPiece piece = (NormalPiece) placedPieceIterator.next();
            iterations++;
            piece.setX(nextCoordinate[0]);
            piece.determineY(topToBottom, nextCoordinate[1], boardHeight);

            // Place the piece.
            if (topToBottom && BoardAction.PlacePiece.canPerformAction(normalBoard.getGrid(), piece)) {
                BoardAction.boardAction(new BoardAction.PlacePiece(), normalBoard.getGrid(), piece);
            } else if (!topToBottom && BoardAction.PlacePieceMirrored.canPerformAction(normalBoard.getGrid(), piece)){
                BoardAction.boardAction(new BoardAction.PlacePieceMirrored(), normalBoard.getGrid(), piece);
            } else {
                continue;
            }
            placedPieces.add(placedPieceIterator.getPieceIndex());

            // Solve the board recursively.
            if (solve()) return true;

            //  Could not solve the board, so remove the piece.
            if (topToBottom) BoardAction.boardAction(new BoardAction.RemovePiece(), normalBoard.getGrid(), piece);
            else BoardAction.boardAction(new BoardAction.RemovePieceMirrored(), normalBoard.getGrid(), piece);

            // Update the instance variables to solve the board.
            placedPieces.remove(placedPieces.size() - 1);
            determineNextCoordinate();
            topToBottom = nextCoordinate[1] < Math.ceil((double) boardHeight / 2);
        }

        // All permutations were tried.
        recursionDepth--;
        return false;
    }

    private boolean determineNextCoordinate() {
        int height = normalBoard.getGrid().length;
        int length = normalBoard.getGrid()[0].length;
        for (int x = 0; x < length; x++) {
            int y2 = height - 1;
            for (int y1 = 0; y1 < Math.ceil((double) height / 2); y1++) {
                if (normalBoard.getGrid()[y1][x] == 0) {
                    nextCoordinate = new int[]{x, y1};
                    return true;
                }
                if (normalBoard.getGrid()[y2][x] == 0) {
                    nextCoordinate = new int[]{x, y2};
                    return true;
                }
                y2--;
            }
        }

        return false;
    }

    private boolean determineHasUnsolvablePattern(boolean topToBottom) {
        int x = nextCoordinate[0];
        int y = nextCoordinate[1];

        if (x == normalBoard.getGrid()[0].length - 1) {
            if (topToBottom) return normalBoard.getGrid()[y + 1][x] == 1;
            return normalBoard.getGrid()[y - 1][x] == 1;
        }

        if (topToBottom) return normalBoard.getGrid()[y][x + 1] == 1 && normalBoard.getGrid()[y + 1][x] == 1;
        return normalBoard.getGrid()[y][x + 1] == 1 && normalBoard.getGrid()[y - 1][x] == 1;
    }

    private int determinePattern() {
        int x = nextCoordinate[0];
        int y = nextCoordinate[1];

        if (x == normalBoard.getGrid()[0].length - 1) return 0;
        if (y == 0 && normalBoard.getGrid()[y][x + 1] == 1 && normalBoard.getGrid()[y + 1][x + 1] == 1) return 0;
        if (y == 0 && normalBoard.getGrid()[y + 1][x + 1] == 1) return 1;
        if (y == 0) return 2;
        return 3;
    }

    private int determineMirroredPattern() {
        int x = nextCoordinate[0];
        int y = nextCoordinate[1];

        if (x == normalBoard.getGrid()[0].length - 1) return 0;
        if (y == boardHeight - 1 && normalBoard.getGrid()[y][x + 1] == 1 && normalBoard.getGrid()[y - 1][x + 1] == 1) return 0;
        if (y == boardHeight - 1 && normalBoard.getGrid()[y - 1][x + 1] == 1) return 1;
        if (y == boardHeight - 1) return 2;
        return 3;
    }

    @Override
    public long getRestCount() {
        return restCount;
    }

    @Override
    public String getGridEncoding() {
        return gridEncoding;
    }

    @Override
    public String[] getPiecesEncoding() {
        return piecesEncoding;
    }

    @Override
    public AbstractPlacedPieceIterator[] getPlacedPieceIterators() {
        return placedPieceIterators;
    }
}
