package main.pin;

import static main.AbstractGrid.EMPTY;
import static main.AbstractGrid.NONEMPTY;

public enum Colour {
    BLUE(10),
    GREEN(100),
    RED(1000),
    YELLOW(10000);

    int factor;

    Colour(int factor) {
        this.factor = factor;
    }

    public static Colour determineColour(int value) {
        if (value == EMPTY || value == NONEMPTY) return null;
        if (value % YELLOW.factor == 0) return YELLOW;
        if (value % RED.factor == 0) return RED;
        if (value % GREEN.factor == 0) return GREEN;
        return BLUE;
    }
}
