package de.nosswald;

/**
 * @author Nils Osswald
 */
public class Match {
    private Player[] players;

    public Match(Player a, Player b) {
        players = new Player[2];
        players[0] = a;
        players[1] = b;
    }
}
