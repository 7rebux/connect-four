package de.nosswald;

/**
 * @author Nils Osswald
 */
public final class Board {
    /**
     * Y: Yellow
     * R: Red
     * ' ': Empty
     */
    private final char[][] board = new char[][] {
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', 'Y', ' ', ' ', ' ', ' ', ' ' },
            { ' ', 'R', ' ', ' ', ' ', 'Y', ' ' },
            { 'Y', 'R', 'R', ' ', 'Y', 'Y', ' ' }
    };

    public char[][] getBoard() {
        return board;
    }
}
