package main.pin;

import main.AbstractPlacedPieceIterator;
import main.BoardAction;
import main.normal.NormalSolver;

import static main.BoardAction.boardAction;

public class PinSolver extends NormalSolver {
    private PinBoard pinBoard;
    private PinPiece[] pinPieces;

    public PinSolver(long restCount, String gridEncoding, String[] piecesEncoding) {
        super(restCount, gridEncoding, piecesEncoding);

        pinBoard = new PinBoard(super.gridEncoding);
        pinPieces = new PinPiece[this.piecesEncoding.length];
        for (int i = 0; i < pinPieces.length; i++) pinPieces[i] = new PinPiece(super.piecesEncoding[i]);
    }

    @Override
    public boolean solve() {
        // Check if the pin solver algorithm should be used.
        if (!pinBoard.openPins.isEmpty()) {
            iterations++;
            PinBoard.Pin pin = pinBoard.openPins.get(0);

            // Initiate the pin placed piece iterator.
            AbstractPlacedPieceIterator placedPieceIterator = new PinPlacedPieceIterator(placedPieces, pinPieces, pin.colour);
            placedPieceIterators[placedPieces.size()] = placedPieceIterator;
            recursionDepth++;

            // Use the iterator to place the next piece.
            while (placedPieceIterator.hasNext()) {
                // Initiate the piece.
                PinPiece piece = (PinPiece) placedPieceIterator.next();
                iterations++;
                piece.setX(pin.x - piece.pinPermutation.openSpace[0]);
                piece.setY(pin.y - piece.pinPermutation.openSpace[1]);

                // Place the piece.
                if (!pinBoard.canPlacePieceOnPin(pinBoard.getGrid(), piece)) continue;
                BoardAction.boardAction(new BoardAction.PlacePiece(), pinBoard.getGrid(), piece);
                BoardAction.boardAction(new BoardAction.PlacePiece(), normalBoard.getGrid(),
                        piece.pinPermutation.normalizedGrid, piece.getX(), piece.getY());
                pinBoard.determinePins();
                placedPieceIterators[placedPieces.size()] = placedPieceIterator;
                placedPieces.add(placedPieceIterator.getPieceIndex());

                // Solve the board recursively
                if (solve()) return true;

                // Could not solve the board, so remove the piece.
                boardAction(new BoardAction.RemovePiece(), pinBoard.getGrid(), piece);
                boardAction(new BoardAction.RemovePiece(), normalBoard.getGrid(),
                        piece.pinPermutation.normalizedGrid, piece.getX(), piece.getY());

                // Update the instance variables to solve the board.
                pinBoard.determinePins();
                placedPieces.remove(placedPieces.size() - 1);
            }
        } else {
            // Otherwise use the default solver algorithm.
            return super.solve();
        }

        // All permutations were tried.
        recursionDepth--;
        return false;
    }
}
