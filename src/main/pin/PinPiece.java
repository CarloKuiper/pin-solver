package main.pin;

import main.AbstractPiece;

import java.util.ArrayList;
import java.util.List;

import static main.pin.Colour.determineColour;

public class PinPiece extends AbstractPiece {

    PinPermutation pinPermutation;
    Colour colour;
    List<PinPermutation> permutations = new ArrayList<>();

    public PinPiece(String gridEncoding) {
        super(gridEncoding);
        colour = determineColour(getGrid()[0][0]);

        determinePermutations();
    }

    @Override
    protected int decode(Character c) {
        return PinBoard.decode(c, false);
    }

    @Override
    protected void addPermutation() {
        permutations.add(new PinPermutation(getGrid(), colour));
    }

    @Override
    public PinPermutation getPermutation() {
        return pinPermutation;
    }
}
