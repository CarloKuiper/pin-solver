package main;

public interface Piece {

    static int[][] diagonalFlip(int[][] grid) {
        int height = grid.length;
        int length = grid[0].length;
        int[][] flippedGrid = new int[length][height];
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                flippedGrid[x][y] = grid[y][x];
            }
        }
        return flippedGrid;
    }

    static int[][] rotate(int[][] grid) {
        int length = grid[0].length;
        int height = grid.length;
        int[][] rotatedGrid = new int[length][height];
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                // rotate counter clockwise.
                rotatedGrid[length - x - 1][y] = grid[y][x];
                // rotate clockwise.
                // rotatedGrid[length - x - 1][height - y - 1] = grid[y][x];
            }
        }
        return rotatedGrid;
    }

    static boolean isEqual(int[][] grid1, int[][] grid2) {
        int length = grid1[0].length;
        int height = grid1.length;
        if (length != grid2[0].length) return false;
        if (height != grid2.length) return false;
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                if (grid1[y][x] != grid2[y][x]) return false;
            }
        }
        return true;
    }

    static boolean isMirrorSymmetric(int[][] grid) {
        return isEqual(grid, diagonalFlip(grid)) || isEqual(grid, rotate(diagonalFlip(grid)))
                || isEqual(grid, rotate(rotate(diagonalFlip(grid))))
                || isEqual(grid, rotate(rotate(rotate(diagonalFlip(grid)))));
    }

    static boolean isC4(int[][] grid) {
        return isEqual(grid, rotate(grid));
    }

    static boolean isC2(int[][] grid) {
        return isEqual(grid, rotate(rotate(grid)));
    }

    int getX();

    int getY();

    Permutation getPermutation();
}
