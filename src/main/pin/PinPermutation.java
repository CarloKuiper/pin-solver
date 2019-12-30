package main.pin;

import main.Permutation;

import java.util.ArrayList;
import java.util.List;

public class PinPermutation implements Permutation {
    private int[][] grid;
    int[][] normalizedGrid;
    List<int[]> openSpaces = new ArrayList<>();
    int[] openSpace;

    PinPermutation(int[][] grid, Colour colour) {
        this.grid = grid;
        normalize();
        determineOpenSpaces(colour.factor);
    }

    private void normalize() {
        int height = grid.length;
        int length = grid[0].length;
        normalizedGrid = new int[height][length];
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                normalizedGrid[y][x] = grid[y][x] != 0 ? 1 : 0;
            }
        }
    }

    private void determineOpenSpaces(int factor) {
        int length = grid[0].length;
        int height = grid.length;
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[y][x] == 2 * factor) {
                    openSpaces.add(new int[]{x, y});
                }
            }
        }
    }

    @Override
    public int[][] getGrid() {
        return grid;
    }
}
