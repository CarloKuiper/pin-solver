package main.normal;

import main.AbstractGrid;

public class NormalBoard extends AbstractGrid {

    public NormalBoard(String grid) {
        super(grid);
    }

    protected int decode(Character c) {
        return Character.isLowerCase(c) ? EMPTY : NONEMPTY;
    }
}
