package main.pin;

import main.AbstractPlacedPieceIterator;

import java.util.ArrayList;

class PinPlacedPieceIterator extends AbstractPlacedPieceIterator {
    private int nextPieceIndex;
    private int permutationCount;
    private int permutationIndex;
    private int openSpacesCount;
    private int openSpacesIndex;
    private Colour colour;
    private PinPiece pinPiece;
    private PinPiece[] pinPieces;

    PinPlacedPieceIterator(ArrayList<Integer> placedPieces, PinPiece[] pinPieces, Colour colour) {
        super(placedPieces);
        this.colour = colour;
        this.pinPieces = pinPieces;
    }

    @Override
    protected boolean hasPieceIteration() {
        return openSpacesIndex < openSpacesCount || permutationIndex < permutationCount;
    }

    @Override
    protected boolean hasNextPiece() {
        for (int i = pieceIndex + 1; i < pinPieces.length; i++) {
            if (pinPieces[i].colour != colour || isPieceUsed(i)) continue;
            nextPieceIndex = i;
            return true;
        }

        return false;
    }

    protected void iterate() {
        if (openSpacesIndex < openSpacesCount) {
            openSpacesIndex++;
            pinPiece.pinPermutation.openSpace = pinPiece.pinPermutation.openSpaces.get(openSpacesIndex);
        } else if (permutationIndex < permutationCount) {
            permutationIndex++;
            pinPiece.pinPermutation = pinPiece.permutations.get(permutationIndex);

            openSpacesIndex = 0;
            openSpacesCount = pinPiece.permutations.get(permutationIndex).openSpaces.size() - 1;
            pinPiece.pinPermutation.openSpace = pinPiece.pinPermutation.openSpaces.get(openSpacesIndex);
        } else {
            pieceIndex = nextPieceIndex;
            pinPiece = pinPieces[pieceIndex];

            permutationIndex = 0;
            pinPiece.pinPermutation = pinPiece.permutations.get(permutationIndex);
            permutationCount = pinPiece.permutations.size() - 1;

            openSpacesIndex = 0;
            pinPiece.pinPermutation.openSpace = pinPiece.pinPermutation.openSpaces.get(openSpacesIndex);
            openSpacesCount = pinPiece.permutations.get(permutationIndex).openSpaces.size() - 1;
        }
    }

    @Override
    public PinPiece getPiece() {
        return pinPiece;
    }
}
