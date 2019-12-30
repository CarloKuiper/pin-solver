package main;

/**
 * (A lazy/high cohesion implementations of the) Strategy pattern.
 */
public interface BoardAction {
    static void boardAction(BoardAction strategy, int[][] grid, Piece piece){
        boardAction(strategy, grid, piece.getPermutation().getGrid(), piece.getX(), piece.getY());
    }

    static void boardAction(BoardAction strategy, int[][] grid, int[][] pieceGrid, int pieceX, int pieceY) {
        for (int x = 0; x < pieceGrid[0].length; x++) {
            for (int y = 0; y < pieceGrid.length; y++) {
                strategy.performAction(grid, pieceGrid, pieceX, pieceY, x, y);
            }
        }
    }

    void performAction(int[][] grid, int[][] pieceGrid, int pieceX, int pieceY, int x, int y);

    class PlacePiece implements BoardAction {
        public static boolean canPerformAction(int[][] grid, Piece piece) {
            int[][] pieceGrid = piece.getPermutation().getGrid();
            if (piece.getX() + pieceGrid[0].length > grid[0].length) return false;
            if (piece.getY() + pieceGrid.length > grid.length) return false;
            for (int x = 0; x < pieceGrid[0].length; x++) {
                for (int y = 0; y < pieceGrid.length; y++) {
                    if (grid[piece.getY() + y][piece.getX() + x] + pieceGrid[y][x] == 2) return false;
                }
            }
            return true;
        }

        @Override
        public void performAction(int[][] grid, int[][] pieceGrid, int pieceX, int pieceY, int x, int y) {
            grid[pieceY + y][pieceX + x] = grid[pieceY + y][pieceX + x] + pieceGrid[y][x];
        }
    }

    class PlacePieceMirrored implements BoardAction {
        public static boolean canPerformAction(int[][] grid, Piece piece) {
            int[][] pieceGrid = piece.getPermutation().getGrid();
            if (piece.getX() + pieceGrid[0].length > grid[0].length) return false;
            if (piece.getY() - pieceGrid.length < 0) return false;
            for (int x = 0; x < pieceGrid[0].length; x++) {
                for (int y = 0; y < pieceGrid.length; y++) {
                    if (grid[piece.getY() - y][piece.getX() + x] + pieceGrid[y][x] == 2) return false;
                }
            }
            return true;
        }

        @Override
        public void performAction(int[][] grid, int[][] pieceGrid, int pieceX, int pieceY, int x, int y) {
            grid[pieceY - y][pieceX + x] = grid[pieceY - y][pieceX + x] + pieceGrid[y][x];
        }
    }

    class RemovePiece implements BoardAction {
        @Override
        public void performAction(int[][] grid, int[][] pieceGrid, int pieceX, int pieceY, int x, int y) {
            grid[pieceY + y][pieceX + x] = grid[pieceY + y][pieceX + x] - pieceGrid[y][x];
        }
    }

    class RemovePieceMirrored implements BoardAction {
        @Override
        public void performAction(int[][] grid, int[][] pieceGrid, int pieceX, int pieceY, int x, int y) {
            grid[pieceY - y][pieceX + x] = grid[pieceY - y][pieceX + x] - pieceGrid[y][x];
        }
    }
}
