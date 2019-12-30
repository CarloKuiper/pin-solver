package main.normal;

import main.AbstractPiece;
import main.Permutation;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class NormalPiece extends AbstractPiece {

    NormalPermutation normalPermutation;
    List<NormalPermutation> permutations0 = new ArrayList<>();
    List<NormalPermutation> permutations1 = new ArrayList<>();
    List<NormalPermutation> permutations2 = new ArrayList<>();
    List<NormalPermutation> permutations3 = new ArrayList<>();

    public NormalPiece(String gridEncoding) {
        super(gridEncoding);

        if (!gridEncoding.isEmpty()) determinePermutations();
    }

    protected int decode(Character c) {
        return 'e' == c ? EMPTY : NONEMPTY;
    }

    @Override
    protected void addPermutation() {
        int pattern = determinePattern();

        switch (pattern) {
            case 0:
                permutations0.add(new NormalPermutation(getGrid(), 0));
                break;
            case 1:
                permutations1.add(new NormalPermutation(getGrid(), 1));
                break;
            case 2:
                permutations2.add(new NormalPermutation(getGrid(), 2));
                break;
            default:
                permutations3.add(new NormalPermutation(getGrid(), 3));
                break;
        }
    }

    private int determinePattern() {
        // The patterns 0 < 1 < 2 < 3
        // X  0  1  2  3
        // x  -- -- -- --
        // ox 10 1? 1? ??
        // x  10 ?0 ?? ??

        // Het is nog uit te breiden met etc etc 4 5
        // 3  4  5
        // -- -- --
        // 0? 0? 0?
        // 1? 0? 0?
        // ?? 1? 0?
        // ?? ?? 1?

        // Ensure that the height and length are at least 2.
        if (getGrid()[0].length == 1) return 0;
        if (getGrid().length == 1) return 1;

        if (getGrid()[0][0] == 1 && getGrid()[0][1] == 0 && getGrid()[1][1] == 0) return 0;
        if (getGrid()[0][0] == 1 && getGrid()[1][1] == 0) return 1;
        if (getGrid()[0][0] == 1) return 2;
        return 3;
    }

    void determineY(boolean topToBottom, int nextY, int boardHeight) {
        if (normalPermutation.pattern > 2) {
            int shiftY = normalPermutation.pattern - 2;
            setY(topToBottom ? max(0, nextY - shiftY) : min(boardHeight, nextY + shiftY));
        } else {
            setY(nextY);
        }
    }

    @Override
    public Permutation getPermutation() {
        return normalPermutation;
    }
}
