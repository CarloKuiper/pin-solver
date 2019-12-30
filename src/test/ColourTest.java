package test;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static main.pin.Colour.determineColour;
import static main.AbstractGrid.*;
import static org.junit.jupiter.api.Assertions.assertNull;


class ColourTest {

    @Test
    void testDetermineColour() {
        IntStream.of(BLUE_PIN, BLUE_OPEN, BLUE_CLOSED, GREEN_PIN, GREEN_OPEN, GREEN_CLOSED,
                RED_PIN, RED_OPEN, RED_CLOSED,YELLOW_PIN, YELLOW_OPEN, YELLOW_CLOSED
                ).forEach(i -> System.out.println(determineColour(i)));

        assertNull(determineColour(EMPTY));
        assertNull(determineColour(NONEMPTY));
    }
}

