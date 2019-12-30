package main;

public abstract class AbstractGrid {
    public static final int EMPTY = 0;
    public static final int NONEMPTY = 1;
    public static final int BLUE_PIN = 10;
    public static final int BLUE_OPEN = 20;
    public static final int BLUE_CLOSED = 30;
    public static final int GREEN_PIN = 100;
    public static final int GREEN_OPEN = 200;
    public static final int GREEN_CLOSED = 300;
    public static final int RED_PIN = 1000;
    public static final int RED_OPEN = 2000;
    public static final int RED_CLOSED = 3000;
    public static final int YELLOW_PIN = 10000;
    public static final int YELLOW_OPEN = 20000;
    public static final int YELLOW_CLOSED = 30000;

    public static final String DEFAULT_BOARD_ENCODING = "eebeereeleeeeeeeelbeeeeyeeleegeeeey";
    public static final String EMPTY_BOARD_ENCODING = "eeeeeeeeleeeeeeeeleeeeeeeeleeeeeeee";

    public static final String[] DEFAULT_PIECES_ENCODING = new String[]
            {"BBBlbb", "BBbB", "rRrlR", "RRelerR", "YYy", "yeelyYYley", "gGlg", "GGgleg"};

    int[][] grid;

    public AbstractGrid(String gridEncoding) {
        // Create an empty grid, where the length of each line is
        // either the first occurrence of the next line, or the length.
        int length = gridEncoding.indexOf('l') != -1 ? gridEncoding.indexOf('l') : gridEncoding.length();
        int height = (int) gridEncoding.chars().filter(c -> c == 'l').count() + 1;
        grid = new int[height][length];

        // Parse for example geebeeeyleeeeeyeelegeeeeeeleebeeeer to an array.
        int y = 0;
        for (int i = 0; i < gridEncoding.length(); i++) {
            char c = gridEncoding.charAt(i);
            int x = i % (length + 1);
            if (c != 'l') {
                grid[y][x] = decode(c);
            } else {
                y++;
            }
        }
    }

    protected abstract int decode(Character c);

    public int[][] getGrid() {
        return grid;
    }
}
