package test;

import java.util.Arrays;

public interface TestUtils {
    static void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            System.out.println();
            Arrays.stream(grid[i]).forEach(c -> System.out.print(String.format("%5d", c)));
        }
        System.out.println();
    }
}
