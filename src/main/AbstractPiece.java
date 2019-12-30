package main;

public abstract class AbstractPiece extends AbstractGrid implements Piece {

    private int x;
    private int y;

    public AbstractPiece(String gridEncoding) {
        super(gridEncoding);
    }

    protected void determinePermutations() {
        // Determine the rotational symmetry.
        int rotations = Piece.isC2(grid) ? Piece.isC4(grid) ? 1 : 2 : 4;

        int i = 0;
        while (i < rotations) {
            grid = Piece.rotate(grid);
            addPermutation();
            i++;
        }

        if (Piece.isMirrorSymmetric(grid)) return;

        grid = Piece.diagonalFlip(grid);
        i = 0;
        while (i < rotations) {
            grid = Piece.rotate(grid);
            addPermutation();
            i++;
        }
    }

    protected abstract void addPermutation();

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
