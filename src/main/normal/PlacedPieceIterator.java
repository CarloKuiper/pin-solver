package main.normal;

import main.AbstractPlacedPieceIterator;

import java.util.ArrayList;

class PlacedPieceIterator extends AbstractPlacedPieceIterator {
    private int nextPieceIndex;
    private int normalizedPermutations0Count;
    private int normalizedPermutations0Index;
    private int normalizedPermutations1Count;
    private int normalizedPermutations1Index;
    private int normalizedPermutations2Count;
    private int normalizedPermutations2Index;
    private int normalizedPermutations3Count;
    private int normalizedPermutations3Index;
    private boolean checkPattern1;
    private boolean checkPattern2;
    private boolean checkPattern3;
    private NormalPiece normalPiece;
    private NormalPiece[] normalPieces;

    PlacedPieceIterator(ArrayList<Integer> placedPieces, NormalPiece[] normalPieces, int pattern) {
        super(placedPieces);
        this.normalPieces = normalPieces;
        this.checkPattern1 = pattern >= 1;
        this.checkPattern2 = pattern >= 2;
        this.checkPattern3 = pattern >= 3;
    }

    @Override
    protected boolean hasPieceIteration() {
        if (normalizedPermutations0Index < normalizedPermutations0Count) return true;
        if (checkPattern1 && normalizedPermutations1Index < normalizedPermutations1Count) return true;
        if (checkPattern2 && normalizedPermutations2Index < normalizedPermutations2Count) return true;
        return checkPattern3 && normalizedPermutations3Index < normalizedPermutations3Count;
    }

    private boolean hasPieceIteration(int pieceIndex) {
        if (!normalPieces[pieceIndex].permutations0.isEmpty()) return true;
        if (checkPattern1 && !normalPieces[pieceIndex].permutations1.isEmpty()) return true;
        if (checkPattern2 && !normalPieces[pieceIndex].permutations2.isEmpty()) return true;
        return checkPattern3 && !normalPieces[pieceIndex].permutations3.isEmpty();
    }


    @Override
    protected boolean hasNextPiece() {
        for (int i = pieceIndex + 1; i < normalPieces.length; i++) {
            if (isPieceUsed(i) || !hasPieceIteration(i)) continue;
            nextPieceIndex = i;
            return true;
        }

        return false;
    }

    @Override
    protected void iterate() {
        if (normalizedPermutations0Index < normalizedPermutations0Count) {
            normalPiece.normalPermutation = normalPiece.permutations0.get(normalizedPermutations0Index);
            normalizedPermutations0Index++;
        } else if (checkPattern1 && normalizedPermutations1Index < normalizedPermutations1Count) {
            normalPiece.normalPermutation = normalPiece.permutations1.get(normalizedPermutations1Index);
            normalizedPermutations1Index++;
        } else if (checkPattern2 && normalizedPermutations2Index < normalizedPermutations2Count) {
            normalPiece.normalPermutation = normalPiece.permutations2.get(normalizedPermutations2Index);
            normalizedPermutations2Index++;
        } else if (checkPattern3 && normalizedPermutations3Index < normalizedPermutations3Count) {
            normalPiece.normalPermutation = normalPiece.permutations3.get(normalizedPermutations3Index);
            normalizedPermutations3Index++;
        } else {
            pieceIndex = nextPieceIndex;
            normalPiece = normalPieces[pieceIndex];
            resetIteratorIndexes();
            iterate();
        }
    }

    private void resetIteratorIndexes() {
        normalizedPermutations0Index = 0;
        normalizedPermutations1Index = 0;
        normalizedPermutations2Index = 0;
        normalizedPermutations3Index = 0;

        normalizedPermutations0Count = normalPiece.permutations0.size();
        if (checkPattern1) normalizedPermutations1Count = normalPiece.permutations1.size();
        if (checkPattern2) normalizedPermutations2Count = normalPiece.permutations2.size();
        if (checkPattern3) normalizedPermutations3Count = normalPiece.permutations3.size();
    }

    @Override
    public NormalPiece getPiece() {
        return normalPiece;
    }
}
