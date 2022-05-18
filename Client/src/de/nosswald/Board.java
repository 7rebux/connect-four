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

    public static final char Yellow = 'Y';
    public static final char Red = 'R';
    public static final char Empty = ' ';

    private final char[][] board = new char[][] {
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', 'Y', ' ', ' ', ' ', ' ', ' ' },
            { ' ', 'R', ' ', ' ', ' ', 'Y', ' ' },
            { 'Y', 'R', 'R', ' ', 'Y', 'Y', ' ' }
    };
}
