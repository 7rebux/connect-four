package de.nosswald;

/**
 * @author Nils Osswald
 */
public class Match {
    private Player[] players;
    private Board board;

    public Match(Player a, Player b) {
        players = new Player[2];
        players[0] = a;
        players[1] = b;

        board = new Board(a);
    }

    public void start() {
        players[0].start();
        players[1].start();
    }
}
