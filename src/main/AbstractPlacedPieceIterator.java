package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractPlacedPieceIterator implements Iterator<Piece> {
    // The index is started at -1, so the iteration starts with the first piece.
    protected int pieceIndex = -1;
    private ArrayList<Integer> placedPieces;

    protected AbstractPlacedPieceIterator(ArrayList<Integer> placedPieces) {
        this.placedPieces = placedPieces;
    }

    @Override
    public boolean hasNext() {
        return hasPieceIteration() || hasNextPiece();
    }

    protected abstract boolean hasPieceIteration();

    protected abstract boolean hasNextPiece();

    @Override
    public Piece next() {
        try  {
            iterate();
        } catch (Exception e) {
            throw new NoSuchElementException();
        }
        return getPiece();
    }

    protected abstract void iterate();

    protected boolean isPieceUsed(int pieceIndex) {
        for (Integer placedPiece : placedPieces) {
            if (placedPiece.equals(pieceIndex)) return true;
        }
        return false;
    }

    public int getPieceIndex() {
        return this.pieceIndex;
    }

    public abstract Piece getPiece();
}
