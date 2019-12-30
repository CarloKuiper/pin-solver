package main.normal;

import main.Permutation;

public class NormalPermutation implements Permutation {
    private int[][] grid;
    int pattern;

    NormalPermutation(int[][] grid, int pattern) {
        this.grid = grid;
        this.pattern = pattern;
    }

    @Override
    public int[][] getGrid() {
        return grid;
    }
}
