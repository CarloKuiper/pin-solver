package main;

public interface Solver {

    boolean solve();

    long getRestCount();

    String getGridEncoding();

    String[] getPiecesEncoding();

    AbstractPlacedPieceIterator[] getPlacedPieceIterators();
}
